package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Soon to come: Sphinx"));
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
