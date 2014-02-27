package helpers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import views.html.initiative;

import models.Entity;
import models.Event;
import models.Initiative;
import models.Milestone;
import models.Risk;
import models.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.BasicDBList;

public class MongoControlCenter {

	private MongoClient mongoClient;

	private DB db;
	private DBCollection initiatives;
	private DBCollection risks;
	private DBCollection milestones;
	private DBCollection events;
	private DBCollection users;

	private BasicDBList ids;

	public MongoControlCenter(String address, int port)
			throws UnknownHostException {

		mongoClient = new MongoClient(address, port);

	}

	/**
	 * 
	 * Sets the database of the Mongo DB controller.
	 * 
	 * @param dbName
	 *            - The name of the Database to access
	 * 
	 */
	public void setDatabase(String dbName) {
		db = mongoClient.getDB(dbName);

		initiatives = db.getCollection("initiatives");
		risks = db.getCollection("risks");
		milestones = db.getCollection("milestones");
		events = db.getCollection("events");
		users = db.getCollection("users");

		ids = new BasicDBList();
	}

	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		mongoClient.close();
	}

	public Initiative getInitiativeById(String entityId) {

		return Initiative.getFirstInitiativeById(entityId);
	}

	/**
	 * Get a specific milestone by its ID.
	 * 
	 * @param entityId
	 * @return entity object
	 */
	public Milestone getMilestoneById(String entityId) {
		return Milestone.getFirstWithId(entityId);
	}

	/**
	 * Get a specific risk by its ID.
	 * 
	 * @param entityId
	 * @return an entity object
	 */
	public Risk getRiskById(String entityId) {

		return Risk.getFirstWithId(entityId);
	}

	public ArrayList<Event> getSingleEventsForUser(String user) {
		ArrayList<Event> result;

		String userQuery = "$or: [{assignee: \"" + user + "\"}, {watchers: \""
				+ user + "\"},{reporter: \"" + user + "\"},{ businessOwner: \""
				+ user + "\"}]";

		result = getEventsForQueriedEntities(userQuery);

		return result;
	}

	public ArrayList<Event> getOrganizationEventsForUser(String username) {
		ArrayList<Event> result;

		String query = "$or:[{allowedAccessUsers:\"" + username
				+ "\"},{allowedAccessUsers:{$size: 0}}]";
		result = getEventsForQueriedEntities(query);

		return result;
	}

	public ArrayList<Event> getEventsForQueriedEntities(String query) {
		ArrayList<Event> result = new ArrayList<Event>();

		Iterator<? extends Entity> entit = Initiative.findBy(query).iterator();
		ArrayList<String> ids = entityIteratorToIdList(entit);
		result.addAll(Event.findByIDListAndEntityType(ids,
				Initiative.TYPE_STRING));

		entit = Milestone.findBy(query).iterator();
		ids = entityIteratorToIdList(entit);
		result.addAll(Event.findByIDListAndEntityType(ids,
				Milestone.TYPE_STRING));

		entit = Risk.findBy(query).iterator();
		ids = entityIteratorToIdList(entit);
		result.addAll(Event.findByIDListAndEntityType(ids, Risk.TYPE_STRING));

		long unixTime = System.currentTimeMillis() / 1000L;
		long threeMonths = 7776000L;
		Iterator<Event> iEvent = result.iterator();

		/**
		 * Code for filtering out non-relevant events. Will uncomment when can
		 * test. while (iEvent.hasNext()) { if (iEvent.next().getDateAsLong() <=
		 * (unixTime - threeMonths)) { iEvent.remove();
		 * 
		 * } }
		 **/

		Collections.sort(result);
		return result;
	}

	private ArrayList<String> entityIteratorToIdList(
			Iterator<? extends Entity> it) {
		ArrayList<String> result = new ArrayList<String>();

		while (it.hasNext()) {
			result.add(it.next().getEntityId());
		}

		return result;
	}

	public ArrayList<Event> getTeamEventsForUser(String username) {
		ArrayList<Event> result;

		User user = User.findByName(username);

		if (user == null) {
			return new ArrayList<Event>();
		}

		List<String> groupList = user.getGroups();
		if (groupList == null) {
			return new ArrayList<Event>();
		}
		String groups = Event.listToMongoString(groupList);

		String query = "$or: [{businessGroups: {$in: " + groups
				+ "}}, {providerGroups: {$in: " + groups + "}}]";

		result = getEventsForQueriedEntities(query);

		return result;

	}

	/**
	 * Gets events that the user is part of aka ones that they reported, are
	 * assigned to, are watchers of or is the buisiness owner of.
	 * 
	 * @param user
	 * @return
	 */
	public Object[] getEventsForUser(String user) {
		ArrayList<DBObject> results = new ArrayList<DBObject>();

		// Query that returns results that the user is part of
		DBObject baseQuery = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user))
				// does this work because watchers is a list?
				.or(new BasicDBObject("reporter", user))
				.or(new BasicDBObject("businessOwner", user)).get();

		/* ------------- Get Results --------------- */

		// Get all events associated with initiatives user is part of

		DBCursor queryCursor = initiatives.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		BasicDBObject eventQuery = new BasicDBObject("entity.entityId",
				new BasicDBObject("$in", ids));

		queryCursor = events.find(eventQuery.append("entity.entityType",
				"INITIATIVE"));
		results.addAll(getResults(queryCursor));

		ids.clear();

		// Get all events associated with risks user is part of

		queryCursor = risks.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));

		queryCursor = events.find(eventQuery
				.append("entity.entityType", "RISK"));
		results.addAll(getResults(queryCursor));

		ids.clear();

		// Get all milestones associated with results user is part of

		queryCursor = milestones.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));

		queryCursor = events.find(eventQuery.append("entity.entityType",
				"MILESTONE"));
		results.addAll(getResults(queryCursor));

		return results.toArray();

	}

	@SuppressWarnings("unchecked")
	public Object[] getTeamEventsForAUser(String user) {
		ArrayList<DBObject> results = new ArrayList<DBObject>();

		DBObject query = new BasicDBObject("username", user);
		DBCursor cursor = users.find(query);

		ArrayList<String> groups = new ArrayList<String>();
		try {
			while (cursor.hasNext()) {
				groups = (ArrayList<String>) cursor.next().get("groups");
			}
		} finally {
			cursor.close();
		}

		BasicDBObject eventQuery = new BasicDBObject("entity.entityId",
				new BasicDBObject("$in", ids));

		// for each group that the user is part of
		for (String group : groups) {

			// find all team initiative related events

			DBObject teamQuery = new QueryBuilder()
					.or(new BasicDBObject("businessGroups", group))
					.or(new BasicDBObject("providerGroups", group)).get();

			DBCursor teamCursor = initiatives.find(teamQuery);
			setIdsFromQueryResults(teamCursor);

			teamCursor = events.find(eventQuery.append("entity.entityType",
					"INITIATIVE"));

			results.addAll(getResults(teamCursor));
			ids.clear();

			// find all team risk related events

			teamCursor = risks.find(teamQuery);
			setIdsFromQueryResults(teamCursor);

			eventQuery = new BasicDBObject("entity.entityId",
					new BasicDBObject("$in", ids));
			teamCursor = events.find(eventQuery.append("entity.entityType",
					"RISK"));

			results.addAll(getResults(teamCursor));
			ids.clear();

			// find all team milestone related events

			teamCursor = milestones.find(teamQuery);
			setIdsFromQueryResults(teamCursor);

			eventQuery = new BasicDBObject("entity.entityId",
					new BasicDBObject("$in", ids));
			teamCursor = events.find(eventQuery.append("entity.entityType",
					"MILESTONE"));

			results.addAll(getResults(teamCursor));
			ids.clear();
		}

		return results.toArray();
	}

	/**
	 * Gets all events of that user's organization .
	 * 
	 * @param user
	 * @return
	 */
	public Object[] getOrgEventsForUser(String user) {
		ArrayList<DBObject> results = new ArrayList<DBObject>();

		DBObject baseQuery = new QueryBuilder()
				.or(new BasicDBObject("allowedAccessUsers", user))
				.or(new BasicDBObject("allowedAccessUsers", new BasicDBObject(
						"$size", 0))).get();

		/* ------------- Get Results --------------- */

		// Get all events associated with initiatives user is part of

		DBCursor queryCursor = initiatives.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		BasicDBObject eventQuery = new BasicDBObject("entity.entityId",
				new BasicDBObject("$in", ids));

		queryCursor = events.find(eventQuery.append("entity.entityType",
				"INITIATIVE"));
		results.addAll(getResults(queryCursor));

		ids.clear();

		// Get all events associated with risks user is part of

		queryCursor = risks.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));

		queryCursor = events.find(eventQuery
				.append("entity.entityType", "RISK"));
		results.addAll(getResults(queryCursor));

		ids.clear();

		// Get all milestones associated with results user is part of

		queryCursor = milestones.find(baseQuery);
		setIdsFromQueryResults(queryCursor);

		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));

		queryCursor = events.find(eventQuery.append("entity.entityType",
				"MILESTONE"));
		results.addAll(getResults(queryCursor));

		return results.toArray();
	}

	@SuppressWarnings("unchecked")
	public Integer getUserRefreshRate(String user) {

		BasicDBObject refreshRateQuery = new BasicDBObject("username", user);
		DBCursor cursor = users.find(refreshRateQuery);

		ArrayList<Integer> temp = new ArrayList<Integer>();

		try {
			while (cursor.hasNext()) {
				temp.add((Integer) cursor.next().get("updateFrequency"));

			}
		} finally {
			cursor.close();
		}

		if (temp.size() != 1) {
			temp.add(0, null);
		}
		return temp.get(0);

	}

	/*
	 * -----------Helper Functions--------------
	 */

	private void setIdsFromQueryResults(DBCursor resultsCursor) {

		DBObject temp;
		try {
			while (resultsCursor.hasNext()) {
				temp = resultsCursor.next();
				try {
					ids.add(((BasicDBObject) temp).getString("entityId"));
				} catch (NullPointerException e) {
					// This occurs when there is no element entityId on the
					// object.
					// TODO: Setup logging so we can find these rogue entities
					// and destroy them
				}
			}
		} finally {
			resultsCursor.close();
		}
	}

	private ArrayList<DBObject> getResults(DBCursor resultsCursor) {
		ArrayList<DBObject> temp = new ArrayList<DBObject>();

		try {
			while (resultsCursor.hasNext()) {
				temp.add(resultsCursor.next());
			}
		} finally {
			resultsCursor.close();
		}

		return temp;
	}
}
