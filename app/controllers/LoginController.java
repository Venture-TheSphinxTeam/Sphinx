package controllers;

import helpers.auth.AuthValidation;
import models.User;
import views.*;
import views.html.login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import static play.data.Form.form;

/**
 * Created by Stephen Yingling on 4/1/14.
 */
public class LoginController extends Controller {
	

    public static Result login(){
        return ok(login.render(form(Login.class)));
    }



    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()){
            return badRequest(login.render(loginForm));
        }
        else {
            session().clear();
            session("username", loginForm.get().username);
            return redirect(
                    controllers.routes.Application.index()
            );
        }
    }

    public static class Login {
    	public static AuthValidation validator;
        public String username;
        public String password;

        public String validate(){
        	return validator.validateUser(username, password);
        }
    }
    
    public static Result logout(){
		session().clear();
		flash("You have successfully logged out.");
		return redirect(routes.LoginController.login());
	}
}
