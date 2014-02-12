package models;


import java.util.*;
import com.mongodb.*;
import play.data.validation.Constraints.*;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.*;
import org.jongo.*;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

public class User {

    public static MongoCollection users() {
        return PlayJongo.getCollection("users");
    }


    @JsonProperty("_id")
    public ObjectId id;

    public User insert() {
        users().save(this);
        return this;
    }

    public void remove() {
        users().remove(this.id);
    }

    public static User findByName(String name) {
        return users().findOne("{name: #}", name).as(User.class);
    }

    // TODO : Figure out how to actually access this data
    /**
     * Returns whether the user is already subscribed to the entity or not.
     * 
     * @param user			user object
     * @param entityId		id of entity to look up
     * @param entityType	type of entity to look up
     * @return
     */
    public static boolean doesUserSubscribeToEntity(User user, String entityId, String entityType){
		boolean retVal = false;
    	
        /*if(entityType=="initiative"){
        	if( user.initiativeSubscriptions.findOne("{entityId: #}", entityId) ){
        		retVal = true;
        	}
        } else if(entityType=="milestone"){
        	if( user.milestoneSubscriptions.findOne("{entityId: #}", entityId) ){
        		retVal = true;
        	}
        } else {
        	if( user.riskSubscriptions.findOne("{entityId: #}", entityId) ){
        		retVal = true;
        	}
        }*/
		
    	return retVal;
    }
    
    /**
     * Subscribe to or unsubscribe from an entity. 
     * 
     * @param status		whether you would like to subscribe (true) or unsubscribe (false)
     * @param user			user object
     * @param entityId		id of entity
     * @param entityType	entity's type
     * @return				the new status of the subscription
     */
    public static boolean setUserEntitySubscriptionStatus(boolean status, User user, String entityId, String entityType){
		boolean retVal = false;
    	
		// TODO : make this work
		if( status = true ){
	        /*if(entityType=="initiative"){
	        	if( user.initiativeSubscriptions.add("{entityId: #}", entityId) ){
	        		retVal = true;
	        	}
	        } else if(entityType=="milestone"){
	        	if( user.milestoneSubscriptions.add("{entityId: #}", entityId) ){
	        		retVal = true;
	        	}
	        } else {
	        	if( user.riskSubscriptions.add("{entityId: #}", entityId) ){
	        		retVal = true;
	        	}
	        }*/
		}
		else {
	        /*if(entityType=="initiative"){
        	if( user.initiativeSubscriptions.remove("{entityId: #}", entityId) ){
        		retVal = true;
        	}
	        } else if(entityType=="milestone"){
	        	if( user.milestoneSubscriptions.remove("{entityId: #}", entityId) ){
	        		retVal = true;
	        	}
	        } else {
	        	if( user.riskSubscriptions.remove("{entityId: #}", entityId) ){
	        		retVal = true;
	        	}
	        }*/			
		}
		
    	return retVal;
    }
}

