package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SubscriptionController extends Controller{
	private static final ArrayList<String> ALL_EVENT_TYPES = new ArrayList(Arrays.asList("REPORT", "TIMESPENT", "CREATE", "UPDATE", "DELETE"));

	public static Result DeleteEntitySubscription(){

		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = json.get("username").asText();
		
		ObjectNode result = Json.newObject();

		User user = User.findByName(username);
		user.setUserEntitySubscriptionStatus(false, user, entityId, entityType);

		return ok(result);
	}

	public static Result UpdateEntitySubscription(){

		ObjectNode result = Json.newObject();

		return ok(result);
	}

	public static Result GetEntityEventSubscriptions(){

		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = json.get("username").asText();
		
		ObjectNode result = Json.newObject();

		User user = User.findByName(username);
		List<String> eventSubscriptions;

		eventSubscriptions = user.getEventsTiedToEntitySubscription(user, entityId, entityType);

		System.out.println("\n\n"+eventSubscriptions+"\n\n");

		// Set output booleans for whether subscription is for x or y type of event
		for(int i=0; i<ALL_EVENT_TYPES.size(); i++){
			String currEventType = ALL_EVENT_TYPES.get(i);
			if( eventSubscriptions.contains(currEventType) ){
				result.put(currEventType,true);
			}else{
				result.put(currEventType,false);
			}
		}

		return ok(result);
	}
}