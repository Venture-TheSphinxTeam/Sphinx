package controllers;

import helpers.OutgoingJSON;

import javax.ws.rs.ProcessingException;

import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;

import java.net.UnknownHostException;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;
import play.Logger;
import play.Play;
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
		Date timeStamp = new Date();

		// create result object
		Comment result = new Comment(entityType, entityId, createdBy, commentHeader, comment, timeStamp);
		result.insert();
		
		OutgoingJSON oj = new OutgoingJSON();
		
		String tempString = Play.application().configuration().getString("comment.post_target");
		tempString.replace(":entityType", entityType);
		tempString.replace(":entityId", entityId);
		tempString.replace(":userName", createdBy);
		
		oj.setJson(comment);
		oj.setUrl(tempString);
		
		try{
			oj.sendJson();
		}catch(ProcessingException e){
			Logger.error("Could not post comment",e);
		}
		 
		//return 
		return Application.entityView(entityId, entityType);
	}
	
	
	@Security.Authenticated(Secured.class)
	public static Result changeComment() throws UnknownHostException{
		String message = "";
		
		JsonNode json = request().body().asJson();

		String objID = json.get("_id").asText();
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String createdBy = request().username();
		String commentHeader = json.get("commentHeader").asText();
		String comment = json.get("comment").asText();
		Date timeStamp = new Date();

		// create result object
		Comment result = new Comment(objID, entityType, entityId, createdBy, commentHeader, comment, timeStamp);
		result.upsert();
		 
		//return 
		return Application.entityView(entityId, entityType);
	}

	@Security.Authenticated(Secured.class)
	public static Result removeComment() throws UnknownHostException{
		String message = "";
		
		JsonNode json = request().body().asJson();

		String entityId = null;
		String entityType = null;
		String _id = json.get("_id").asText();

		//create result object
		Comment result = new Comment(_id, null, null, null, null, null);
		result.delete();

		return ok();

	}

}
