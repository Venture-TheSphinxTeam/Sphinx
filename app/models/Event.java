package models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by Stephen Yingling on 2/4/14.
 */
public class Event {

    public Event(){}

    @JsonProperty("_id")
    protected ObjectId id;
    protected String eventType;
    protected Entity entity;
    

    
    protected static MongoCollection _events(){
		return PlayJongo.getCollection("events");
	}
    
    public static Iterable<Event> findBy(String query){
    	return _events().find("{" + query+"}").as(Event.class);
    }
    
    public static Iterable<Event> findByIDListAndEntityType(List<String> ids, String type){
    	return findBy("\"entity.entityId\": {$in:"+listToMongoString(ids)+"},"
    			+ "\"entity.entityType\": \""+type+"\"");
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public static List<Event>getSubscribedEventsForUser(String username){
    	List<Event> result = new ArrayList<Event>();
    	
    	User user = User.findByName(username); 
    	List<String> initIdList = user.getInitiativeSubscriptions();
    	List<String> mileIdList = user.getMilestoneSubscriptions();
    	List<String> riskIdList = user.getRiskSubscriptions();
    	
    	Iterable<Event> ce = Event.findByIDListAndEntityType(initIdList, "INITIATIVE");
    	for(Event c: ce){
    		result.add(c);
    	}
    	ce = Event.findByIDListAndEntityType(mileIdList, "MILESTONE");
    	
    	for(Event c: ce){
    		result.add(c);
    	}
    	ce = Event.findByIDListAndEntityType(riskIdList, "RISK");
    	
    	for(Event c: ce){
    		result.add(c);
    	}
    	
    	return result;
    }
    
    public static String listToMongoString(List<String> list){
    	String result="[";
    	if(list == null){
    		return result +"]";
    	}
    	
    	for(String s : list){
    		result += "\""+s+"\"" + ",";
    	}
    	result = result.substring(0, result.lastIndexOf(','))+ "]";
    	
    	return result;
    }
}
