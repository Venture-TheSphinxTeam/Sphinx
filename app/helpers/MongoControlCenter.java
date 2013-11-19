package helpers;

import java.net.UnknownHostException;
import java.util.ArrayList;

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
	

	public MongoControlCenter(String address, int port)
			throws UnknownHostException {

		mongoClient = new MongoClient(address, port);

	}

	/**
	 * 
	 * Sets the database of the Mongo DB controller.
	 * 
	 * @param dbName
	 *            The name of the Database to access
	 */
	public void setDatabase(String dbName) {
		db = mongoClient.getDB(dbName);
		
		initiatives = db.getCollection("initiatives");
		risks = db.getCollection("risks");
		milestones = db.getCollection("milestones");
		events = db.getCollection("events");
	}

	/**
	 * 
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		mongoClient.close();
	}

	public Object[] getEventsForUser(String user) {

		DBObject query = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user))
				.or(new BasicDBObject("reporter", user))
				.or(new BasicDBObject("businessOwner", user)).get();

		BasicDBList ids = new BasicDBList();

		BasicDBObject eventQuery = new BasicDBObject("entity.entityId",
				new BasicDBObject("$in", ids));

		ArrayList<DBObject> result = new ArrayList<DBObject>();

		DBCursor c = initiatives.find(query);
		DBObject t;
		try {
			while (c.hasNext()) {
				t = c.next();
				ids.add(((BasicDBObject) t).getInt("entityId"));
			}
		} finally {
			c.close();
		}

		c = events.find(eventQuery.append("entity.entityType", "INITIATIVE"));

		try {
			while (c.hasNext()) {
				result.add(c.next());
			}
		} finally {
			c.close();
		}

		ids = new BasicDBList();

		c = risks.find(query);
		try {
			while (c.hasNext()) {
				t = c.next();
				// System.out.println(((BasicDBObject)t).getInt("entityId"));
				ids.add(((BasicDBObject) t).getInt("entityId"));
			}
		} finally {
			c.close();
		}
		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));
		c = events.find(eventQuery.append("entity.entityType", "RISK"));

		try {
			while (c.hasNext()) {
				result.add(c.next());
			}
		} finally {
			c.close();
		}

		ids = new BasicDBList();
		c = milestones.find(query);
		try {
			while (c.hasNext()) {
				ids.add(((BasicDBObject) c.next()).getInt("entityId"));
			}
		} finally {
			c.close();
		}
		eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject(
				"$in", ids));
		c = events.find(eventQuery.append("entity.entityType", "MILESTONE"));

		try {
			while (c.hasNext()) {
				result.add(c.next());
			}
		} finally {
			c.close();
		}

		return result.toArray();

	}

	@SuppressWarnings("unchecked")
	public Object[] getTeamEventsForUser(String user) {

		DBObject query = new BasicDBObject("username", user);

		DBCollection users = db.getCollection("users");

		ArrayList<String> groups = new ArrayList<String>();

		DBCursor cursor = users.find(query);

		try {
			while (cursor.hasNext()) {
				groups = (ArrayList<String>) cursor.next().get("groups");
			}
		}

		finally {
			cursor.close();
		}

		ArrayList<DBObject> teamEntities = new ArrayList<DBObject>();
		BasicDBList ids = new BasicDBList();

		BasicDBObject eventQuery = new BasicDBObject("entity.entityId",
				new BasicDBObject("$in", ids));

		for (String group : groups) {

			DBObject teamQuery = new QueryBuilder()
					.or(new BasicDBObject("businessGroups", group))
					.or(new BasicDBObject("providerGroups", group)).get();

			DBCursor teamCursor = initiatives.find(teamQuery);
			DBObject t;
			try {
				while (teamCursor.hasNext()) {
					t = teamCursor.next();
					ids.add(((BasicDBObject) t).getInt("entityId"));
				}
			} finally {
				teamCursor.close();
			}

			teamCursor = events.find(eventQuery.append("entity.entityType",
					"INITIATIVE"));

			try {
				while (teamCursor.hasNext()) {
					teamEntities.add(teamCursor.next());
				}
			} finally {
				teamCursor.close();
			}

			ids.clear();

			teamCursor = risks.find(teamQuery);

			try {
				while (teamCursor.hasNext()) {
					t = teamCursor.next();
					ids.add(((BasicDBObject) t).getInt("entityId"));
				}
			} finally {
				teamCursor.close();
			}

			eventQuery = new BasicDBObject("entity.entityId",
					new BasicDBObject("$in", ids));
			teamCursor = events.find(eventQuery.append("entity.entityType",
					"RISK"));

			try {
				while (teamCursor.hasNext()) {
					teamEntities.add(teamCursor.next());
				}
			} finally {
				teamCursor.close();
			}

			ids.clear();

			DBCollection milestones = db.getCollection("milestones");

			teamCursor = milestones.find(teamQuery);

			try {
				while (teamCursor.hasNext()) {
					t = teamCursor.next();
					ids.add(((BasicDBObject) t).getInt("entityId"));
				}
			} finally {
				teamCursor.close();
			}

			eventQuery = new BasicDBObject("entity.entityId",
					new BasicDBObject("$in", ids));
			teamCursor = events.find(eventQuery.append("entity.entityType",
					"MILESTONE"));

			try {
				while (teamCursor.hasNext()) {
					teamEntities.add(teamCursor.next());
				}
			} finally {
				teamCursor.close();
			}

		}

		return teamEntities.toArray();
	}

	public Object[] getOrgEventsForUser(String user){
		return null;
	}
	/**
	 * 
	 * Gets all the entities the supplied user is a part of.
	 * 
	 * 
	 * @param user
	 *            The user supplied.
	 * @return An array of entity documents the user belongs to.
	 */
/*	public Object[] getInitiativesByUser(String user) {

		DBObject query = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user))
				.or(new BasicDBObject("reporter", user))
				.or(new BasicDBObject("businessOwner", user)).get();

		DBCursor cursor = initiatives.find(query);

		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();

	}*/

	/**
	 * 
	 * Gets all of the documents in a specific collection
	 * 
	 * @param collection
	 *            The collection being queried.
	 * @return An array of all documents in that collection.
	 */
	public Object[] getAllDocuments(String collection) {
		DBCollection coll = db.getCollection(collection);

		DBCursor cursor = coll.find();
		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();
	}

}
