package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SubscriptionController extends Controller{

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
}