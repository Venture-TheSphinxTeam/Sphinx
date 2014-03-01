package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
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
		String username = json.get("username").asText();
		String buttonType = json.get("buttonType").asText();
		
		User user = User.findByName(username);

		// create return object
		ObjectNode result = Json.newObject();

		if( buttonType.equals("subscription") ) {
			result.put("status",UpdateSubscriptionStatus(user,entityId,entityType));
		}  // TODO: Test else if and else
		else if( buttonType.equals("watch") ){
			result.put("status",UpdateWatchStatus(user,entityId,entityType));
		}else{
			result.put("status",UpdateVoteStatus(user,entityId,entityType));
		}
		
		return ok(result);
	}

	/**
	 *   Returns current status of if entityType-entityName is already subscribed 
	 * to by inputted user.
	 */
	public static Result GetButtonStatus(){

		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = json.get("username").asText();
		String buttonType = json.get("buttonType").asText();
		
		User user = User.findByName(username);

		// create return object
		ObjectNode result = Json.newObject();

		System.out.println("This is the buttonType I got: " + buttonType);

		if( buttonType.equals("subscription") ){
			result.put("status",User.doesUserSubscribeToEntity(user, entityId, entityType ));
		}  // TODO: Test else if and else
		else if( buttonType.equals("watch") ){
			result.put("status",User.doesUserWatchEntity(user, entityId, entityType ));
		}else{
			result.put("status",User.doesUserVoteForEntity(user, entityId, entityType ));
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

		// swap vote status
		if( User.doesUserVoteForEntity(user, entityId, entityType )){
			User.setUserEntityVoteStatus(false, user, entityId, entityType);
			retVal = false;
		}
		else{
			User.setUserEntityVoteStatus(true, user, entityId, entityType);
			retVal = true;
		}

		return retVal;
	}

	private static boolean UpdateWatchStatus(User user, String entityId, String entityType){
		boolean retVal;

		// swap watch status
		if( User.doesUserWatchEntity(user, entityId, entityType )){
			User.setUserEntityWatchStatus(false, user, entityId, entityType);
			retVal = false;
		}
		else{
			User.setUserEntityWatchStatus(true, user, entityId, entityType);
			retVal = true;
		}

		return retVal;
	}
}
