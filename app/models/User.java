package models;


import java.util.*;

import com.mongodb.*;

import play.data.validation.Constraints.*;
import models.facets.SavedQuery;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.*;

import org.jongo.*;

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
    
    public static void removeAll(){
    	users().remove();
    }

    public static User findByName(String name) {
        return users().findOne("{username: #}", name).as(User.class);
    }

    public User(){}

    private String username;
    private String emailAddress;
    private String pictureURL;
    private List<String> groups;
    private List<String> watches;
    private List<String> votes;
    private String password;
    private boolean admin;
    private List<SavedQuery> querySubscriptions;
    private List<EntitySubscription> initiativeSubscriptions;
    private List<EntitySubscription> milestoneSubscriptions;
    private List<EntitySubscription> riskSubscriptions;
    private int updateFrequency;


    //----------------------------SUBSCRIPTIONS/WATCHES/VOTES---------------------------------//

    public static boolean doesUserSubscribeToEntity(User user, String entityId, String entityType){     //TODO: FIX
    	boolean retVal = false;

        if( entityType.toLowerCase().equals("initiative") ){
            for(int i=0; i<user.initiativeSubscriptions.size(); i++){
                if( user.initiativeSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    retVal = true;
                    break;
                }
            }
        } else if( entityType.toLowerCase().equals("milestone") ){
            for(int i=0; i<user.milestoneSubscriptions.size(); i++){
                if( user.milestoneSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    retVal = true;
                    break;
                }
            }
        } else {
            for(int i=0; i<user.riskSubscriptions.size(); i++){
                if( user.riskSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    retVal = true;
                    break;
                }
            }
        }
		
    	return retVal;
    }
    
    public static boolean doesUserVoteForEntity(User user, String entityId, String entityType){
        return user.votes.contains(entityId) ;
    }


    public static boolean doesUserWatchEntity(User user, String entityId, String entityType){
        return user.watches.contains(entityId) ;
    }

    /**
     * Subscribe to or unsubscribe from an entity.                      // TO DO : TEST IF NEW CHANGES WORK
     * @return				the new status of the subscription
     */
    public static void setUserEntitySubscriptionStatus(boolean status, User user, String entityId, String entityType){
        List<String> defaultEventSubscriptions = Arrays.asList("REPORT", "TIMESPENT", "CREATE", "UPDATE", "DELETE");

        // Add subscription
		if( status == true ){
	        if( entityType.toLowerCase().equals("initiative") ){
	        	user.initiativeSubscriptions.add(new EntitySubscription(entityId, defaultEventSubscriptions)) ; 
	        } else if( entityType.toLowerCase().equals("milestone") ){                    
	        	user.milestoneSubscriptions.add(new EntitySubscription(entityId, defaultEventSubscriptions)) ;     
 	        } else {
	        	user.riskSubscriptions.add(new EntitySubscription(entityId, defaultEventSubscriptions)) ;
	        }
		}
        // Remove subscription
		else {
	        deleteEntitySubscription(user, entityId, entityType);
		}

        users().save(user);
    }

    /**
     *   Updates what events the user will see updates on for a specific
     * entity subscription.
     */
    public static void updateEventsTiedToEntitySubscription(List<String> eventSubscriptions, User user, String entityId, String entityType){
        // TO DO
    }

    /**
     * Watch to unwatch an initiative. 
     * @return              the new status of the watch
     */
    public static void setUserEntityWatchStatus(boolean status, User user, String entityId, String entityType){

        if( status == true ){
            user.watches.add(entityId);
        }
        else {
            user.watches.remove(entityId);
        }

        users().save(user);
    }

    /**
     * Vote or unvote for an initiative. 
     * @return              the new status of the vote
     */
    public static void setUserEntityVoteStatus(boolean status, User user, String entityId, String entityType){

        if( status == true ){
            user.votes.add(entityId);
        }
        else {
            user.votes.remove(entityId);
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

    public List<String> getWatches() {
        return watches;
    }

    public void setWatches(List<String> watches) {
        this.watches = watches;
    }

    public List<String> getVotes() {
        return votes;
    }

    public void setVotes(List<String> votes) {
        this.votes = votes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<SavedQuery> getQuerySubscriptions() {
        return querySubscriptions;
    }

    public void setQuerySubscriptions(List<SavedQuery> querySubscriptions) {
        this.querySubscriptions = querySubscriptions;
    }

    public List<EntitySubscription> getInitiativeSubscriptions() {
        return initiativeSubscriptions;
    }

    public void setInitiativeSubscriptions(List<EntitySubscription> initiativeSubscriptions) {
        this.initiativeSubscriptions = initiativeSubscriptions;
    }

    public List<EntitySubscription> getMilestoneSubscriptions() {
        return milestoneSubscriptions;
    }

    public void setMilestoneSubscriptions(List<EntitySubscription> milestoneSubscriptions) {
        this.milestoneSubscriptions = milestoneSubscriptions;
    }

    public List<EntitySubscription> getRiskSubscriptions() {
        return riskSubscriptions;
    }

    public void setRiskSubscriptions(List<EntitySubscription> riskSubscriptions) {
        this.riskSubscriptions = riskSubscriptions;
    }

    public int getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    //------------PRIVATE FUNCTIONS---------------//

    private static void deleteEntitySubscription(User user, String entityId, String entityType){

        if( entityType.toLowerCase().equals("initiative") ){
            for(int i=0; i<user.initiativeSubscriptions.size(); i++){
                if( user.initiativeSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    user.initiativeSubscriptions.remove(i);
                    break;
                }
            }
        } else if( entityType.toLowerCase().equals("milestone") ){
            for(int i=0; i<user.milestoneSubscriptions.size(); i++){
                if( user.milestoneSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    user.milestoneSubscriptions.remove(i);
                    break;
                }
            }
        } else {
            for(int i=0; i<user.riskSubscriptions.size(); i++){
                if( user.riskSubscriptions.get(i).getEntityId().equals( entityId ) ){
                    user.riskSubscriptions.remove(i);
                    break;
                }
            }
        }
    }


}

