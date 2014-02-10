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

    public User(){}

    private String emailAddress;
    private String pictureURL;
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
}

