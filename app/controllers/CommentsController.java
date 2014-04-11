package controllers;

import javax.ws.rs.ProcessingException;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import play.mvc.Controller;
import play.libs.Json;
import play.mvc.Result;

public class CommentsController extends Controller {
	
	public static Result newComment() throws UnknownHostException{
		String message = "";
		
		JsonNode json = request().body().asJson();

		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String createdBy = json.get("createdBy").asText();
		String commentHeader = json.get("commentHeader").asText();
		String comment = json.get("comment").asText();

		// create return object
		Comment result = new Comment(entityType, entityId, createdBy, commentHeader, comment);
		result.insert();

		//Comment.
		ObjectNode resultx = Json.newObject();
		 
		//return 
		return Application.entityView(entityId, entityType);
	}
	
	

}
