package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Entity;
import models.Event;
import models.Initiative;
import models.Milestone;
import models.Risk;
import models.User;
import models.facets.SavedQuery;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	public static final String USERNAME = "jay-z";
	public static final String DATABASE = "dev";
	public static final String MONGO_URL = "venture.se.rit.edu";
	public static final int MONGO_PORT = 27017;

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
						userRate = control.getUserRefreshRate(USERNAME);

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

	public static Result index() throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		// String username = "RickyWinterborn"; // TODO : Make this pull current
		// user name

		// Object[] userEntities = control.getEventsForUser("jay-z");
		ArrayList<Event> userEvents = control.getSingleEventsForUser(USERNAME);
		ArrayList<Event> teamEntities = control.getTeamEventsForUser(USERNAME);
		ArrayList<Event> orgEntities = control
				.getOrganizationEventsForUser(USERNAME);
		Iterator<? extends models.Event> subscribedEvents = models.Event
				.getSubscribedEventsForUser(USERNAME);

		ArrayList<Event> queryEvents = new ArrayList<Event>();

		List<SavedQuery> querySubs = User.findByName(USERNAME)
				.getQuerySubscriptions();

		for (SavedQuery s : querySubs) {
			System.out.println(s.toQueryString());
			queryEvents.addAll(control.getEventsForQueriedEntities(s
					.toQueryString()
					+ ","
					+ control.createAllowedAccessUsersQuery(USERNAME)));
		}
		System.out.println(queryEvents);

		// Object[] userSubscriptions = //TODO

		control.closeConnection();

		return ok(index.render(userEvents, teamEntities, orgEntities, USERNAME,
				subscribedEvents, queryEvents));
	}

	public static Result search(String keyword, String field, String priority,
			String status, String reporter, String assignee, String label)
			throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);
		ArrayList<Entity> result = new ArrayList<Entity>();

		if (keyword.equals("") && field.equals("")) {

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
			System.out.println(facetQuery);
			if (!facetQuery.equals("")) {
				result = control.getEntitiesByQuery(facetQuery
						+ control.createAllowedAccessUsersQuery(USERNAME));
			} else {
				result = control.getEntitiesByQuery(control
						.createAllowedAccessUsersQuery(USERNAME));
			}

		}

		else if (keyword.equals("")) {
			result = control.getEntitiesByQuery(control
					.createAllowedAccessUsersQuery(USERNAME));
		}

		else if (field.equals("undefined")) {
			result = control.getEntitiesByQuery("$or:[{"
					+ control.createRegexQuery("summary", keyword) + "},{"
					+ control.createRegexQuery("description", keyword) + "}]");

			Iterator<Entity> enIter = result.iterator();

			while (enIter.hasNext()) {
				Entity e = enIter.next();
				if (!e.getAllowedAccessUsers().contains(USERNAME)
						&& e.getAllowedAccessUsers().size() != 0) {
					enIter.remove();
				}
			}

		}

		else {
			result = control.getEntitiesByQuery(control.createRegexQuery(field,
					keyword)
					+ ","
					+ control.createAllowedAccessUsersQuery(USERNAME));
		}

		ArrayList<Object> facets = control.getIndexedValues();

		return ok(search.render(result, facets));

	}

	public static Result subscriptions() throws UnknownHostException {

		// Open connection to database
		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		ArrayList<Entity> result = new ArrayList<Entity>();

		ArrayList<String> initSubIds = control.getUserSubscriptionIds(USERNAME,
				"initiativeSubscriptions");
		ArrayList<String> mileSubIds = control.getUserSubscriptionIds(USERNAME,
				"milestoneSubscriptions");
		ArrayList<String> riskSubIds = control.getUserSubscriptionIds(USERNAME,
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

		control.closeConnection();

		List<SavedQuery> querySubs = User.findByName(USERNAME)
				.getQuerySubscriptions();

		return ok(subscriptions.render(initSubs, mileSubs, riskSubs, querySubs,
				USERNAME));
	}

	public static Result adminTools() {
		return ok(adminTools.render("", AdminController.entitForm));
	}

	public static Result userSettings() {
		return ok(settings.render());
	}

	public static Result entityView(String arg, String type)
			throws UnknownHostException {

		MongoControlCenter control = new MongoControlCenter(MONGO_URL,
				MONGO_PORT);
		control.setDatabase(DATABASE);

		if (type.equals("INITIATIVE")) {
			Initiative entity_Initiative = control.getInitiativeById(arg);

			if (((entity_Initiative.getAllowedAccessUsers().contains(USERNAME) || ((entity_Initiative
					.getAllowedAccessUsers().isEmpty()))))) {

				return ok(initiative.render(entity_Initiative, USERNAME));
			} else {
				return ok(accessError.render());
			}

		}

		else if (type.equals("MILESTONE")) {
			Milestone entity_Milestone = control.getMilestoneById(arg);

			if (((entity_Milestone.getAllowedAccessUsers()).contains(USERNAME) || ((entity_Milestone
					.getAllowedAccessUsers().isEmpty())))) {

				return ok(milestone.render(entity_Milestone, USERNAME));
			}

			else {
				return ok(accessError.render());
			}
		}

		else {
			Risk entity_Risk = control.getRiskById(arg);
			if (((entity_Risk.getAllowedAccessUsers()).contains(USERNAME) || ((entity_Risk
					.getAllowedAccessUsers())).isEmpty())) {

				return ok(risk.render(entity_Risk, USERNAME));
			}

			else {
				return ok(accessError.render());

			}
		}

	}

}
