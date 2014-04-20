package controllers;

import javax.ws.rs.ProcessingException;

import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import play.mvc.Controller;
import play.mvc.Security;
import play.libs.Json;
import play.mvc.Result;

public class CommentsController extends Controller {
	
	@Security.Authenticated(Secured.class)
	public static Result newComment() throws UnknownHostException{
		String message = "";
		
		JsonNode json = request().body().asJson();

		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String createdBy = request().username();
		String commentHeader = json.get("commentHeader").asText();
		String comment = json.get("comment").asText();

		// create result object
		Comment result = new Comment(entityType, entityId, createdBy, commentHeader, comment);
		result.insert();
		 
		//return 
		return Application.entityView(entityId, entityType);
	}
	
	

	public static void editComment(){
		
	}

	public static Result deleteComment() throws UnknownHostException{
		
		JsonNode json = request().body().asJson();

		String entityId = null;
		String entityType = null;
		String _id = json.get("_id").asText();

		//create result object
		Comment result = new Comment(_id, null, null, null, null, null);
		result.delete();

		return Application.entityView(entityId, entityType);

	}

}
