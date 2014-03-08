package models;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;


/**
 * POJO object for java to/from JSON
 * Created by Striker on 1/28/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Milestone extends Entity{

    private static MongoCollection milestones(){
        return PlayJongo.getCollection("milestones");
    }
    
    public static Iterable<Milestone> findBy(String query){
    	return milestones().find("{ $and: [{entityType: \""+Milestone.TYPE_STRING+ 
				"\"},"+query+"]}").as(Milestone.class);
    }

    public void remove(){
        milestones().remove(this.id);
    }

    public void removeAll(){
        milestones().remove();
    }

    public static Iterable<Milestone> getAllWithKey(String key){
        return milestones().find("{key: #}",key).as(Milestone.class);
    }

    public Milestone upsert(){
        milestones().update("{key: #}", this.getKey()).upsert().merge(this);
        return this;
    }

    public Milestone getFirstWithKey(String key){
        return milestones().findOne("{key: #",key).as(Milestone.class);
    }
    
    public static Milestone getFirstWithId(String id){
        return milestones().findOne("{entityId: #}",id).as(Milestone.class);
    }


    public Milestone() {}

    @JsonProperty("_id")
    public ObjectId id;

    private String health;
    private String verificationSteps;
    private long desiredStartDate;
    private long startDate;
    private long desiredDueDate;
    public static final String TYPE_STRING = "MILESTONE";


    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getVerificationSteps() {
        return verificationSteps;
    }

    public void setVerificationSteps(String verificationSteps) {
        this.verificationSteps = verificationSteps;
    }

    public long getDesiredStartDate() {
        return desiredStartDate;
    }

    public void setDesiredStartDate(long desiredStartDate) {
        this.desiredStartDate = desiredStartDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getDesiredDueDate() {
        return desiredDueDate;
    }

    public void setDesiredDueDate(long desiredDueDate) {
        this.desiredDueDate = desiredDueDate;
    }
}
