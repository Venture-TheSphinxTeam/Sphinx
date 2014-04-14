package controllers;

import javax.ws.rs.ProcessingException;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.settings;

public class UserSettingsController extends Controller{
	public static Form<ChangeInterval> intervalForm = Form.form(ChangeInterval.class);
	
	public static Result updateInterval(){
		String message = "";
		

		Form<ChangeInterval> ff = intervalForm.bindFromRequest();
		
		String username = "jay-z";
		User user = User.findByName(username);
		Integer newRate = 0;
		
		try {
			//@TODO: sanitize and round
			user.setUpdateFrequency(newRate = ff.get().interval);
			message = "Update interval set to " + newRate + " minutes.";
		} catch (Exception e) {
			message += "\n"+"Invalid input. Please submit an integer value.";
		}
		 
		
		return ok(settings.render(message, intervalForm));
	}

}
