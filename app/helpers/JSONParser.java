package helpers;
import java.io.IOException;
import java.util.ArrayList;

import models.EntityCollection;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Event;
import models.EventCollection;

public class JSONParser {
  public JSONParser(){}

  public EntityCollection extractEntities(String message){
    EntityCollection entities = null;
    
    ObjectMapper om = new ObjectMapper();
    try{
    	entities= om.readValue(message, EntityCollection.class);
    }catch(JsonGenerationException e){
    	e.printStackTrace();
    }catch(JsonMappingException e){
    	e.printStackTrace();
    }catch (JsonParseException e1) {
			e1.printStackTrace();
	} catch (IOException e1) {
		e1.printStackTrace();
	}

    return entities;
  }

    public EventCollection extractEvents(String message){
        EventCollection events = null;

        ObjectMapper om = new ObjectMapper();
        try{
            events = om.readValue(message, EventCollection.class);
        }catch(JsonGenerationException e){
            e.printStackTrace();
        }catch(JsonMappingException e){
            e.printStackTrace();
        }catch (JsonParseException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return events;
    }
}
