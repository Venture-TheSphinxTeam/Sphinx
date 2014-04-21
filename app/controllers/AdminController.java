package controllers;

import java.util.List;

import javax.ws.rs.ProcessingException;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminTools;

import play.mvc.Security;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class AdminController extends Controller{
	public static Form<GetEntitiesForm> entitForm = Form.form(GetEntitiesForm.class);
	
	public static Result forceGetEntities(){
		String message = "";
		Form<GetEntitiesForm> ff = entitForm.bindFromRequest();
		Ingester ingester = new Ingester();
		EntityCollection ec = null;
		try {
			ec = ingester.getEntitiesSince(ff.get().date);
		} catch (ProcessingException e) {
			message += "\n"+e.getMessage();
		}
		if(ec != null){
			List<Initiative> li = ec.getInitiatives();
			for(Initiative i : li){
				i.upsert();
			}
		
			List<Milestone> lm = ec.getMilestones();
		
			for(Milestone m : lm){
				m.upsert();
			}
		
			List<Risk> lr = ec.getRisks();
		
			for(Risk r : lr){
				r.upsert();
			}
			ec.createIndices();
			message += "\nEntities pulled";
		}
        EventCollection events =null;
        try{
        	events =ingester.getEventsSince(ff.get().date);
        }catch(ProcessingException e){
        	message += "\n"+e.getMessage();
        }
        if(events != null){
	        List<ChangeEvent> ce = events.getChangeEvents();
	        for(ChangeEvent c : ce){
	        	if(c.getEntity() != null){
	        		c.insert();
	        	}
	        }
	        
	        List<ReportEvent> re = events.getReportEvents();
	        for(ReportEvent rep: re){
	        	if(rep.getEntity() != null){
	        		rep.insert();
	        	}
	        }
	        
	        List<TimeSpentEvent> tse = events.getTimeSpentEvents();
	        for(TimeSpentEvent ts : tse){
	        	if(ts.getEntity() != null){
	        		ts.insert();
	        	}
	        }
	        message+= "\nEvents pulled";
        }

		return ok(adminTools.render(message, entitForm, new ArrayList<User>()));
	}

	/*
	 * Returns usernames and statuses of users.
	 *
	 *  NOTE : You must split by ',' to get because arrays cannot be returned 
	 *    in a result
	 */
	@Security.Authenticated(Secured.class)
	public static Result getAdminStatusOfUsers(){

		// Check the user is an admin before returning admin statuses
		if (User.findByName(request().username()).getAdmin()) {
			// create return object
			ObjectNode result = Json.newObject();

			String usernames = "";
			String adminStatuses = "";

			for (User user : User.getAllUsers()){
				usernames = usernames + user.getUsername() + ",";
				adminStatuses = adminStatuses + user.getAdmin() + ",";
			}

			// remove trailing comma
		  	if (usernames.length() > 0) {
		    	usernames = usernames.substring(0, usernames.length()-1);
		    	adminStatuses = adminStatuses.substring(0, adminStatuses.length()-1);
		  	}

			result.put("usernames",usernames);
			result.put("adminStatuses",adminStatuses);

			return ok(result);
		} 
		else {
			return badRequest("User is not admin and does not have access to this function");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result toggleAdminStatusOfUser(){

		// Check the user is an admin before returning admin statuses
		if (User.findByName(request().username()).getAdmin()) {

			// Get json information sent in
			JsonNode json = request().body().asJson();		
			String username = json.get("username").asText();
			User userToChangeUsername = User.findByName(username);

			if( userToChangeUsername.getAdmin() ){
				userToChangeUsername.setUserAdminStatus(false,userToChangeUsername);
			}else{
				userToChangeUsername.setUserAdminStatus(true,userToChangeUsername);
			}


			return ok( Json.newObject() );			
		} 
		else {
			return badRequest("User is not admin and does not have access to this function");
		}

	}

}
