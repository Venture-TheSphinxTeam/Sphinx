package controllers;

import helpers.MongoControlCenter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import play.*;
import play.mvc.*;

import views.html.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class Application extends Controller {

	public static Result index() throws UnknownHostException {
		MongoControlCenter control = new MongoControlCenter("venture.se.rit.edu", 27017);
		control.setDatabase("dev");

		Object[] array = control.getAllDocuments("entities");
		
		control.closeConnection();

		return ok(index.render(array));
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
