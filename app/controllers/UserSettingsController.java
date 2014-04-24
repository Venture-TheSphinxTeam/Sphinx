package controllers;

import javax.ws.rs.ProcessingException;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.settings;

public class UserSettingsController extends Controller{
	public static Form<ChangeInterval> intervalForm = Form.form(ChangeInterval.class);

	public static Form<ChangeIcon> iconForm = Form.form(ChangeIcon.class);
	
	
	@Security.Authenticated(Secured.class)
	public static Result updateInterval(){
		String message = "";
		
		Form<ChangeInterval> inv = intervalForm.bindFromRequest();
		
		User user = User.findByName(request().username());
		
		try {
			
			Integer newRate = inv.get().interval;
			user.setUpdateFrequency(newRate);
			message = "Update interval set to " + newRate + " minutes.";
		} catch (Exception e) {
			message += "\n"+"Invalid input. Please submit an integer value.";
		}
		 
		return ok(settings.render(message, intervalForm, iconForm));
	}



	@Security.Authenticated(Secured.class)
	public static Result updateIcon(){
		String message = "";
		
		Form<ChangeIcon> ico = iconForm.bindFromRequest();
		
		User user = User.findByName(request().username());
		

		try {
			String newURL = ico.get().userImageURL;

			if(newURL == ""){
				throw new Exception();
			}

			user.setPictureURL(newURL);

			message = "User icon has been updated.";
		} catch (Exception e) {
			message += "\n"+"Invalid input.";
		}
		 
		return ok(settings.render(message, intervalForm, iconForm));
	}

}
