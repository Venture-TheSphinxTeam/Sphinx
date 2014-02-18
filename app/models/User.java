package models;


import java.util.*;
import com.mongodb.*;
import play.data.validation.Constraints.*;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.*;
import org.jongo.*;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import scala.math.Ordering;
import uk.co.panaxiom.playjongo.PlayJongo;

@JsonIgnoreProperties(ignoreUnknown = true)
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
        return users().findOne("{username: #}", name).as(User.class);
    }

    public User(){}

    private String username;
    private String emailAddress;
    private String pictureURL;
    private List<String> groups;
    private List<Entity> watches;
    private List<Entity> votes;
    private String password;
    private boolean admin;
    private List<String> querySubscriptions;
    private List<String> initiativeSubscriptions;
    private List<String> milestoneSubscriptions;
    private List<String> riskSubscriptions;
    private int updateFrequency;

    /**
     * Returns whether the user is already subscribed to the entity or not.
     */
    public static boolean doesUserSubscribeToEntity(User user, String entityId, String entityType){
    	boolean retVal = false;

        if( entityType.toLowerCase().equals("initiative") ){
            retVal = user.initiativeSubscriptions.contains(entityId) ;
        } else if( entityType.toLowerCase().equals("milestone") ){
        	retVal = user.milestoneSubscriptions.contains(entityId) ;
        } else {
        	retVal = user.riskSubscriptions.contains(entityId) ;
        }
		
    	return retVal;
    }
    
    /**
     * Subscribe to or unsubscribe from an entity. 
     *
     * @return				the new status of the subscription
     */
    public static void setUserEntitySubscriptionStatus(boolean status, User user, String entityId, String entityType){

        // Add subscription
		if( status == true ){
	        if( entityType.toLowerCase().equals("initiative") ){
	        	user.initiativeSubscriptions.add(entityId) ;
	        } else if( entityType.toLowerCase().equals("milestone") ){
	        	user.milestoneSubscriptions.add(entityId) ;
	        } else {
	        	user.riskSubscriptions.add(entityId) ;
	        }
		}
        // Remove subscription
		else {
	        if( entityType.toLowerCase().equals("initiative") ){
            	user.initiativeSubscriptions.remove(entityId) ;
	        } else if( entityType.toLowerCase().equals("milestone") ){
	        	user.milestoneSubscriptions.remove(entityId) ;
	        } else {
	        	user.riskSubscriptions.remove(entityId) ;
	        }
		}

        users().save(user);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<Entity> getWatches() {
        return watches;
    }

    public void setWatches(List<Entity> watches) {
        this.watches = watches;
    }

    public List<Entity> getVotes() {
        return votes;
    }

    public void setVotes(List<Entity> votes) {
        this.votes = votes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<String> getQuerySubscriptions() {
        return querySubscriptions;
    }

    public void setQuerySubscriptions(List<String> querySubscriptions) {
        this.querySubscriptions = querySubscriptions;
    }

    public List<String> getInitiativeSubscriptions() {
        return initiativeSubscriptions;
    }

    public void setInitiativeSubscriptions(List<String> initiativeSubscriptions) {
        this.initiativeSubscriptions = initiativeSubscriptions;
    }

    public List<String> getMilestoneSubscriptions() {
        return milestoneSubscriptions;
    }

    public void setMilestoneSubscriptions(List<String> milestoneSubscriptions) {
        this.milestoneSubscriptions = milestoneSubscriptions;
    }

    public List<String> getRiskSubscriptions() {
        return riskSubscriptions;
    }

    public void setRiskSubscriptions(List<String> riskSubscriptions) {
        this.riskSubscriptions = riskSubscriptions;
    }

    public int getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }
}

