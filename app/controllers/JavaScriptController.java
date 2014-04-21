package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;


public class JavaScriptController extends Controller{

	
	public static Result jsRoutes(){
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("myJsRoutes", 
				routes.javascript.ButtonStateController.GetButtonStatus(),
				routes.javascript.ButtonStateController.UpdateButtonStatus(),
				routes.javascript.SubscriptionController.UpdateSubscriptionsEvents(),
				routes.javascript.SubscriptionController.DeleteEntitySubscription(),
				routes.javascript.SubscriptionController.GetEntityEventSubscriptions(),
				routes.javascript.SavedQueryController.deleteQuerySubscription(),
				routes.javascript.SavedQueryController.saveQuery(),
				routes.javascript.SavedQueryController.updateQuerySubscription(),
				routes.javascript.CommentsController.newComment(),
				routes.javascript.CommentsController.changeComment(),
				routes.javascript.CommentsController.removeComment()));
	}
}
