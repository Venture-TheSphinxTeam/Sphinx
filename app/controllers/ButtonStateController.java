package controllers;

import ch.qos.logback.core.Context;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import models.Vote;
import models.Watch;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Http.Request;
import play.mvc.Result;

public class ButtonStateController extends Controller{
	
	/**
	 * If the user is subscribed to an entity, unsubscribe or vice versa.
	 *
	 * @return	New state of button
	 */
	public static Result UpdateButtonStatus(){
		
		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String buttonType = json.get("buttonType").asText();
		String username = request().username();
		
		User user = User.findByName(username);

		// create return object
		ObjectNode result = Json.newObject();

		if( buttonType.equals("subscription") ) {
			result.put("status",UpdateSubscriptionStatus(user,entityId,entityType));
		}  
		else if( buttonType.equals("watch") ){
			result.put("status",UpdateWatchStatus(user,entityId,entityType));
		}
		else if( buttonType.equals("vote") ){
			result.put("status",UpdateVoteStatus(user,entityId,entityType));
		}
		else{
			result.put("status","");
		}
		
		return ok(result);
	}

	/**
	 *   Returns current status of if entityType-entityName is already subscribed 
	 * to by inputted user.
	 */
	@Security.Authenticated(Secured.class)
	public static Result GetButtonStatus(){

		// Get json information sent in
		JsonNode json = request().body().asJson();

		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String buttonType = json.get("buttonType").asText();
		String username = request().username();
		
		User user = User.findByName(username);

		ObjectNode result = Json.newObject();

		if( buttonType.equals("subscription") ){
			result.put("status",User.doesUserSubscribeToEntity(user, entityId, entityType ));
		}  
		else if( buttonType.equals("watch") ){
			result.put("status",User.doesUserWatchEntity(user, entityId, entityType ));
		}
		else if( buttonType.equals("vote") ){
			result.put("status",User.doesUserVoteForEntity(user, entityId, entityType ));

		}
		else{
			result.put("status","");
		}

		return ok(result);
	}

	//------------------------SUBFUNCTIONS---------------------//

	private static boolean UpdateSubscriptionStatus(User user, String entityId, String entityType){
		boolean retVal;

		// swap subscription status
		if( User.doesUserSubscribeToEntity(user, entityId, entityType )){
			User.setUserEntitySubscriptionStatus(false, user, entityId, entityType);
			retVal = false;
		}
		else{
			User.setUserEntitySubscriptionStatus(true, user, entityId, entityType);
			retVal = true;
		}

		return retVal;
	}

	private static boolean UpdateVoteStatus(User user, String entityId, String entityType){
		boolean retVal;

        Vote v = new Vote();
        v.setEntityId(Long.parseLong(entityId));
        v.setEntityType(entityType);
        v.setUserName(user.getUsername());

		// swap vote status
		if( User.doesUserVoteForEntity(user, entityId, entityType )){
			User.setUserEntityVoteStatus(false, user, entityId, entityType);
			retVal = false;

            v.sendUnVote();
			
		}
		else{
			User.setUserEntityVoteStatus(true, user, entityId, entityType);
			retVal = true;

            v.sendVote();
		}

		return retVal;
	}

	private static boolean UpdateWatchStatus(User user, String entityId, String entityType){
		boolean retVal;

        Watch w = new Watch();
        w.setEntityId(Long.parseLong(entityId));
        w.setEntityType(entityType);
        w.setUserName(user.getUsername());

		// swap watch status
		if( User.doesUserWatchEntity(user, entityId, entityType)){
			User.setUserEntityWatchStatus(false, user, entityId, entityType);
			retVal = false;
            w.sendUnWatch();
		}
		else{
			User.setUserEntityWatchStatus(true, user, entityId, entityType);
			retVal = true;
            w.sendWatch();
		}

		return retVal;
	}
}
