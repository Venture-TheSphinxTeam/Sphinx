package models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
    protected List<UpdateChanges> updateChanges;

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
    
    @JsonAnySetter
    public void setUCsFromOther(String key, Object value){
    	UpdateChanges uc = null;
    	ObjectMapper om = new ObjectMapper();
    	if(value instanceof UpdateChanges){
    		uc = (UpdateChanges) value;
    	}
    	if (uc !=null){
    		uc.setFieldName(key);
    		if(updateChanges == null){
    			updateChanges = new ArrayList<UpdateChanges>(); 
    		}
    		updateChanges.add(uc);
    	}
    }

    @Override
    public String getAssociatedUser(){
        return "default-user";
        //return changedBy; because none of these users actually exist
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    
    
    
    public List<UpdateChanges> getUpdateChanges() {
		return updateChanges;
	}

	public void setUpdateChanges(List<UpdateChanges> updateChanges) {
		this.updateChanges = updateChanges;
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
