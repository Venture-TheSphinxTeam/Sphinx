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

public class Entity {

    public static MongoCollection entities() {
        return PlayJongo.getCollection("entities");
    }


    @JsonProperty("_id")
    public ObjectId id;
    public String key;
    public String summary;
    public String description;
    public String status;
    public String priority;
    public String projectType;
    public String complexity;
    public String health;
    public String businessGoals;
    public String verificationSteps;
    public String businessUnit;
    public String businessGroup;
    public String providerGroup;
    public ArrayList<User> businessOwner;
    public User assigne;
    public User reporter;
    public Date desiredStartDate;
    public Date startDate;
    public Date desiredDueDate;
    public Date dueDate;
    public int originalEstimate;
    public int remainingEstimate;
    public int workLogged;
    public ArrayList<String> labels;
    public Entity parent;
    public Entity altParent;
    public ArrayList<String> riskType;
    public String riskImpact;
    public String riskLiklihood;
    public String riskResponseStrat;
    public String riskPlan;
    public ArrayList<User> allowedUsers;
    public Date updated;
    public Date created; 

    public String name;

    public Entity insert() {
        entities().save(this);
        return this;
    }

    public void remove() {
        entities().remove(this.id);
    }

    public static Entity findByName(String name) {
        return entities().findOne("{name: #}", name).as(Entity.class);
    }

}

