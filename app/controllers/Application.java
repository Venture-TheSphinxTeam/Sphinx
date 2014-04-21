package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import models.Entity;
import models.Event;
import models.Initiative;
import models.Milestone;
import models.Risk;
import models.Comment;
import akka.actor.FSM.Timer;

import com.mongodb.BasicDBObject;

import models.User;
import models.facets.SavedQuery;
import play.Play;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	public static String DATABASE = Play.application().configuration().getString("sphinx.db.db");
	public static String MONGO_URL = Play.application().configuration().getString("sphinx.db.url");
	public static int MONGO_PORT = Play.application().configuration().getInt("sphinx.db.port");

	public static WebSocket<String> webbysockets() {
		return new WebSocket<String>() {
			final int MINUTES_TO_MILLISECONDS = 60 * 1000;
			Integer userRate;
			Integer update;

			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<String> in,
					final WebSocket.Out<String> out) {

				// For each event received on the socket,
				in.onMessage(new Callback<String>() {
					public void invoke(String event)
							throws UnknownHostException {

						MongoControlCenter control = new MongoControlCenter(
								MONGO_URL, MONGO_PORT);
						control.setDatabase(DATABASE);

						// find current user
						userRate = control.getUserRefreshRate(request()
								.username());

						control.closeConnection();

						// set min value if something goes wrong
						if (userRate == null || userRate < 5) {
							update = 5;
						} else {
							update = userRate;
						}

						update *= MINUTES_TO_MILLISECONDS;
						out.write(update.toString());
					}

				});

				// When the socket is closed.
				in.onClose(new Callback0() {
					public void invoke() {

					}
				});

			}

		};

	}

	@Security.Authenticated(Secured.class)
	public static Result index() throws UnknownHostException {

		String username = request().username();
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		ArrayList<Event> userEvents = control.getSingleEventsForUser(username);
		ArrayList<Event> teamEntities = control.getTeamEventsForUser(username);
		ArrayList<Event> orgEntities = control
				.getOrganizationEventsForUser(username);
		Iterator<? extends models.Event> subscribedEvents = models.Event
				.getSubscribedEventsForUser(username);

		HashSet<Event> queryEvents = new HashSet<Event>();

		List<SavedQuery> querySubs = User.findByName(username)
				.getQuerySubscriptions();

		for (SavedQuery s : querySubs) {
			if (s.getFacets().size() != 0) {
				ArrayList<Event> allEvents = control
						.getEventsForQueriedEntities(s.toQueryString()
								+ ","
								+ control
										.createAllowedAccessUsersQuery(username));
				for (Event ev : allEvents) {
					queryEvents.add(ev);
				}
			}
		}

		ArrayList<Event> queryEvList = new ArrayList<Event>();
		queryEvList.addAll(queryEvents);

		// Object[] userSubscriptions = //TODO

		control.closeConnection();

		return ok(index.render(userEvents, teamEntities, orgEntities, username,
				subscribedEvents, queryEvList));
	}

	@Security.Authenticated(Secured.class)
	public static Result search(String keyword, String priority, String status,
			String reporter, String assignee, String label)
			throws UnknownHostException {
		String username = request().username();
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);
		ArrayList<Entity> result = new ArrayList<Entity>();

		if (keyword.equals("")) {

			String priorityQuery = control.createSimpleFindQuery("priority",
					priority);
			String statusQuery = control
					.createSimpleFindQuery("status", status);
			String reporterQuery = control.createSimpleFindQuery("reporter",
					reporter);
			String assigneeQuery = control.createSimpleFindQuery("assignee",
					assignee);
			String labelQuery = control.createSimpleFindQuery("labels", label);
			String facetQuery = "";

			if (!priority.equals("")) {
				facetQuery += priorityQuery + ",";
			}

			if (!status.equals("")) {
				facetQuery += statusQuery + ",";
			}

			if (!reporter.equals("")) {
				facetQuery += reporterQuery + ",";
			}

			if (!assignee.equals("")) {
				facetQuery += assigneeQuery + ",";
			}

			if (!label.equals("")) {
				facetQuery += labelQuery + ",";
			}

			if (!facetQuery.equals("")) {
				result = control.getEntitiesByQuery(facetQuery
						+ control.createAllowedAccessUsersQuery(username));
			} else {
				result = control.getEntitiesByQuery(control
						.createAllowedAccessUsersQuery(username));
			}

		}

		else if (keyword.equals("")) {
			result = control.getEntitiesByQuery(control
					.createAllowedAccessUsersQuery(username));
		}

		else {
			result = control.getEntitiesByQuery("$or:[{"
					+ control.createRegexQuery("summary", keyword) + "},{"
					+ control.createRegexQuery("description", keyword) + "}]");

			Iterator<Entity> enIter = result.iterator();

			while (enIter.hasNext()) {
				Entity e = enIter.next();
				if (!e.getAllowedAccessUsers().contains(username)
						&& e.getAllowedAccessUsers().size() != 0) {
					enIter.remove();
				}
			}

		}

		ArrayList<Object> facets = control.getIndexedValues();

		return ok(search.render(result, facets));

	}

	@Security.Authenticated(Secured.class)
	public static Result subscriptions() throws UnknownHostException {

		String username = request().username();
		// Open connection to database
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		ArrayList<Entity> result = new ArrayList<Entity>();

		ArrayList<String> initSubIds = control.getUserSubscriptionIds(username,
				"initiativeSubscriptions");
		ArrayList<String> mileSubIds = control.getUserSubscriptionIds(username,
				"milestoneSubscriptions");
		ArrayList<String> riskSubIds = control.getUserSubscriptionIds(username,
				"riskSubscriptions");

		// Collect initiative objects
		ArrayList<Initiative> initSubs = new ArrayList<Initiative>();
		for (String id : initSubIds) {
			initSubs.add(control.getInitiativeById(id));
		}

		// Collect milestone objects
		ArrayList<Milestone> mileSubs = new ArrayList<Milestone>();
		for (String id : mileSubIds) {
			mileSubs.add(control.getMilestoneById(id));
		}

		// Collect risk objects
		ArrayList<Risk> riskSubs = new ArrayList<Risk>();
		for (String id : riskSubIds) {
			riskSubs.add(control.getRiskById(id));
		}

		ArrayList<Object> facets = control.getIndexedValues();

		control.closeConnection();

		List<SavedQuery> querySubs = User.findByName(username)
				.getQuerySubscriptions();

		return ok(subscriptions.render(initSubs, mileSubs, riskSubs, querySubs,
				facets));
	}

	@Security.Authenticated(Secured.class)
	public static Result adminTools() {

		List<User> users = User.getAllUsers();

		if (User.findByName(request().username()).getAdmin()) {
			return ok(adminTools.render("", AdminController.entitForm,
					users));
		} else {
			return ok(accessError.render());
		}

	}

	public static Result userSettings() {
		return ok(settings.render("", UserSettingsController.intervalForm));
	}

	@Security.Authenticated(Secured.class)
	public static Result entityView(String arg, String type)
			throws UnknownHostException {
		String username = request().username();

		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		if (type.equals("INITIATIVE")) {
			Initiative entity_Initiative = control.getInitiativeById(arg);
			ArrayList<Comment> entityComments = control
					.getComments(entity_Initiative.getEntityId());

			if (((entity_Initiative.getAllowedAccessUsers().contains(username) || ((entity_Initiative
					.getAllowedAccessUsers().isEmpty()))))) {

				return ok(initiative
						.render(entity_Initiative,
								username,
								control.getEntitiesByQuery("\"workBreakdownParent.entityId\":"
										+ "\""
										+ entity_Initiative.getEntityId()
										+ "\","
										+ control
												.createAllowedAccessUsersQuery(username)),
								entityComments));
			} else {
				return ok(accessError.render());
			}

		}

		else if (type.equals("MILESTONE")) {
			Milestone entity_Milestone = control.getMilestoneById(arg);
			ArrayList<Comment> entityComments = control
					.getComments(entity_Milestone.getEntityId());

			if (((entity_Milestone.getAllowedAccessUsers()).contains(username) || ((entity_Milestone
					.getAllowedAccessUsers().isEmpty())))) {

				return ok(milestone
						.render(entity_Milestone,
								username,
								control.getEntitiesByQuery("\"workBreakdownParent.entityId\":"
										+ "\""
										+ entity_Milestone.getEntityId()
										+ "\","
										+ control
												.createAllowedAccessUsersQuery(username)),
								entityComments));
			}

			else {
				return ok(accessError.render());
			}
		}

		else {
			Risk entity_Risk = control.getRiskById(arg);
			ArrayList<Comment> entityComments = control.getComments(entity_Risk
					.getEntityId());

			if (((entity_Risk.getAllowedAccessUsers()).contains(username) || ((entity_Risk
					.getAllowedAccessUsers())).isEmpty())) {

				return ok(risk.render(entity_Risk, username, entityComments));
			}

			else {
				return ok(accessError.render());

			}
		}

	}
}
