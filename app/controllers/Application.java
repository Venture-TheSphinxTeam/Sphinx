package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import models.Event;
import models.Initiative;
import models.Milestone;
import akka.actor.FSM.Timer;

import com.mongodb.BasicDBObject;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.*;
import views.html.*;
import views.html.defaultpages.error;

public class Application extends Controller {
	public static final String USERNAME = "RickyWinterborn";

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
								"venture.se.rit.edu", 27017);
						control.setDatabase("dev");

						// find current user
						userRate = control
								.getUserRefreshRate("RickyWinterborn");

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
		MongoControlCenter control = new MongoControlCenter(
				"venture.se.rit.edu", 27017);
		control.setDatabase("dev");

		// String username = "RickyWinterborn"; // TODO : Make this pull current
		// user name

		// Object[] userEntities = control.getEventsForUser("jay-z");
		ArrayList<Event> userEvents = control.getSingleEventsForUser("jay-z");
		ArrayList<Event> teamEntities = control.getTeamEventsForUser("jay-z");
		ArrayList<Event> orgEntities = control
				.getOrganizationEventsForUser("jay-z");
		Iterator<? extends models.Event> subscribedEvents = models.Event
				.getSubscribedEventsForUser("jay-z");
		// Object[] userSubscriptions = //TODO

		control.closeConnection();

		return ok(index.render(userEvents, teamEntities, orgEntities, USERNAME,
				subscribedEvents));
	}

	public static Result search() {
		return ok(search.render());
	}

	public static Result subscriptions() {
		return ok(subscriptions.render());
	}

	public static Result adminTools() {
		return ok(adminTools.render("", AdminController.entitForm));
	}

	public static Result userSettings() {
		return ok(settings.render());
	}

	public static Result entityView(String arg, String type)
			throws UnknownHostException {

		MongoControlCenter control = new MongoControlCenter(
				"venture.se.rit.edu", 27017);
		control.setDatabase("dev");

		if (type.equals("INITIATIVE")) {
			Initiative entity_Initiative = control.getInitiativeById(arg);

			if (((entity_Initiative.getAllowedAccessUsers().contains("jay-z") || ((entity_Initiative
					.getAllowedAccessUsers().isEmpty()))))) {
				System.out.println(entity_Initiative + USERNAME);
				return ok(initiative.render(entity_Initiative, USERNAME));
			} else {
				return ok(accessError.render());
			}

		}

		else if (type.equals("MILESTONE")) {
			Milestone entity_Milestone = control.getMilestoneById(arg);

			if (((entity_Milestone.getAllowedAccessUsers()).contains("jay-z") || ((entity_Milestone
					.getAllowedAccessUsers().isEmpty())))) {

				return ok(milestone.render(entity_Milestone, USERNAME));
			}

			else {
				return ok(accessError.render());
			}
		}

		else {
			BasicDBObject entity_Risk = (BasicDBObject) control
					.getRiskById(arg);

			if (((com.mongodb.BasicDBList) (entity_Risk
					.get("allowedAccessUsers"))).contains("jay-z")
					|| ((com.mongodb.BasicDBList) (entity_Risk
							.get("allowedAccessUsers"))).isEmpty()) {

				return ok(risk.render(entity_Risk, USERNAME));
			}

			else {
				return ok(accessError.render());

			}
		}

	}
}
