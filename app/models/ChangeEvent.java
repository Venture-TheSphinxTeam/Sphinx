package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Striker on 2/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeEvent extends Event{
	
	public static final String TYPE_SELECTOR = "eventType: {$in: [\"CREATE\", \"UPDATE\",\"DELETE\"]}";
	
	
	public static Iterable<ChangeEvent> findCEBy(String query){
		String q = "{"+ChangeEvent.TYPE_SELECTOR+","+query+"}";
		return _events().find(q).as(ChangeEvent.class);
		
	}
	
	public void remove(){
        _events().remove(this.id);
    }

    public static void removeAll(){
        _events().remove("{"+ TYPE_SELECTOR + "}");
    }

    public ChangeEvent insert(){
        _events().save(this);
        return this;
    }
    
    
    public static Iterable<ChangeEvent> findCEby(String query){
    	return _events().find("{"+TYPE_SELECTOR +","+query+"}").as(ChangeEvent.class);
    }


    public ChangeEvent(){    }


    protected long eventDate;
    protected String changedBy;

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
        this.com_date = eventDate;
    }

    public String getChangedBy() {

        
        return changedBy; 
    }

    @Override
    public String getAssociatedUser(){
        return "default-user";
        //return changedBy; because none of these users actually exist
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    
    @Override
    public String getEventDetails(){
    	String result ="";
    	
    	result += changedBy;
    	
    	if(eventType.equals("CREATE")){
    		result += " created ";
    	}
    	else if(eventType.equals("UPDATE")){
    		result += " updated ";
    	}
    	else{
    		result += " deleted ";
    	}
    	
    	result += entity.getEntityType().toLowerCase();
    	result += (" " + entity.getSummary());
    	
		return result;
    }
    
    @Override
    public String getDate() {
    	return super.getFormattedDate(new Date(eventDate));
    }
    
}
