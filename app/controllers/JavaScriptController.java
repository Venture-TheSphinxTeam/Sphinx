package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;


public class JavaScriptController extends Controller{

	
	public static Result jsRoutes(){
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("myJsRoutes", 
				routes.javascript.ButtonStateController.GetButtonStatus()));
	}
}
