package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.BasicDBObject;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.*;
import views.html.*;
import views.html.defaultpages.error;

public class Application extends Controller {
	
	public static WebSocket<String> webbysockets(){
	    return new WebSocket<String>() {

	        // Called when the Websocket Handshake is done.
	        public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

	            // For each event received on the socket,
	            in.onMessage(new Callback<String>() {
	                public void invoke(String event) {

	                    // Log events to the console
	                    //System.out.println(event.toString());
	                	
	                	try {
							index();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

	                }
	            });

	            // When the socket is closed.
	            in.onClose(new Callback0() {
	                public void invoke() {

	                    System.out.println("Disconnected");

	                }
	            });

	            // Send a single 'Hello!' message
	            out.write("Haz pulled data for");

	        }

	    };
	    
	}

	public static Result index() throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter(
				"venture.se.rit.edu", 27017);
		control.setDatabase("dev");

		String username = "RickyWinterborn"; // TODO : Make this pull current
												// user name

		Object[] userEntities = control.getEventsForUser("jay-z");
		Object[] teamEntities = control.getTeamEventsForUser("RickyWinterborn");
		Object[] orgEntities = control.getOrgEventsForUser("jay-z");
		// Object[] userSubscriptions = //TODO

		control.closeConnection();

		return ok(index.render(userEntities, teamEntities, orgEntities,
				username));
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
			
			if(testInitiative.get("assignee").equals("jay-z")){
				
				return ok(initiative.render(testInitiative));
			}
			else{
				return ok(accessError.render());
			}
			
			
		}

		else if (type.equals("MILESTONE")) {
			Object testMilestone = control.getMilestoneById(arg);
			return ok(milestone.render(testMilestone));

		}

		else {
			Object testRisk = control.getRiskById(arg);
			return ok(risk.render(testRisk));
		}

	}
}
