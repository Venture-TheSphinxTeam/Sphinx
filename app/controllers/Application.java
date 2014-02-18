package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.Arrays;

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
			final int MINUTES_TO_MILLISECONDS = 60*1000;
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

						//find current user
						userRate = control
								.getUserRefreshRate("RickyWinterborn");

						control.closeConnection();

						//set min value if something goes wrong
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

						System.out.println("Disconnected");

					}
				});

			}

		};

	}

	public static Result index() throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter(
				"venture.se.rit.edu", 27017);
		control.setDatabase("dev");

		//String username = "RickyWinterborn"; // TODO : Make this pull current
												// user name

		Object[] userEntities = control.getEventsForUser("jay-z");
		Object[] teamEntities = control.getTeamEventsForUser("RickyWinterborn");
		Object[] orgEntities = control.getOrgEventsForUser("jay-z");
		// Object[] userSubscriptions = //TODO

		control.closeConnection();

		return ok(index.render(userEntities, teamEntities, orgEntities,
				USERNAME));
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
			BasicDBObject testInitiative = (BasicDBObject) control.getInitiativeById(arg);
			
			
			if(((com.mongodb.BasicDBList)(testInitiative.get("allowedAccessUsers"))).contains("jay-z")){
			
				return ok(initiative.render(testInitiative, USERNAME));
			}
			else{
				return ok(accessError.render());
			}

		}

		else if (type.equals("MILESTONE")) {
			Object testMilestone = control.getMilestoneById(arg);
			return ok(milestone.render(testMilestone, USERNAME));

		}

		else {
			Object testRisk = control.getRiskById(arg);
			return ok(risk.render(testRisk, USERNAME));
		}

	}
}
