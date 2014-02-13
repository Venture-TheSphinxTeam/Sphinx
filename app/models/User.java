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
     * Add an entity to subscribe to.
     * IN PROGRESS
     *
     * @input entityType of entity to be inserted
     * @input entityId   of entity to be inserted
     * @input userName   of the user to have the subscription added to
     */ // TODO : FIX
    /*public void addEntitySubscription(String entityType, String entityId) {
        if(entityType=="initiative"){
            this.initiativeSubscriptions.add(entityId);
        } else if(entityType=="milestone"){
            this.milestoneSubscriptions.add(entityId);
        } else {
            this.riskSubscriptions.add(entityId);
        }
    }

    public boolean isEntitySubscribed(String entityType, String entityId){
        boolean retVal;
        if(entityType=="initiative"){
            if(this.initiativeSubscriptions.contains(entityId)){
                retVal = true;
            }else{
                retVal = false;
            }
        } else if(entityType=="milestone"){
            if(this.milestoneSubscriptions.contains(entityId)){
                retVal = true;
            }else{
                retVal = false;
            }
        } else {
            if(this.riskSubscriptions.contains(entityId)){
                retVal = true;
            }else{
                retVal = false;
            }
        }
        return retVal;
    }

    public List<String> getInitiativeSubscriptions(){
        return this.initiativeSubscriptions;
    }

    public void setUpdateFrequency(int updateFrequency){
        this.updateFrequency = updateFrequency;
    }

    public int getUpdateFrequency(){
        return this.updateFrequency;
    }*/

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

