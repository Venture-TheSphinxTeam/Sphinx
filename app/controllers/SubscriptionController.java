package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SubscriptionController extends Controller{
	
	/**
	 * 
	 * @return
	 */
	public static Result UpdateSubscriptionStatus(){
		
		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = json.get("username").asText();
		
		
		
		// create return object
		ObjectNode result = Json.newObject();
		result.put("test","do I work?");
		
		return ok(result);
	}

}
