package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter("venture.se.rit.edu", 27017);
		control.setDatabase("dev");
                String username = "jay-z";
		Object[] array = control.getEventsForUser(username);
		
		control.closeConnection();

		return ok(index.render(array,username));
	}

	public static Result search() {

		return ok(search.render());

	}

	public static Result subscriptions() {
		return ok(subscriptions.render());

	}

	public static Result adminTools() {
		return ok(adminTools.render());
	}
}
