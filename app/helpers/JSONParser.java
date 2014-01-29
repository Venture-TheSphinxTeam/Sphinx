package helpers;
import java.io.IOException;
import java.util.ArrayList;

import models.EntityCollection;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JSONParser {
  public JSONParser(){}

  public ArrayList<String> extractEntities(String message){
    ArrayList<String> entities = new ArrayList<String>();
    
    ObjectMapper om = new ObjectMapper();
    try{
    	EntityCollection e= om.readValue(message, EntityCollection.class);
    	//entities.addAll(e.getInitiatives());
    	//entities.addAll(e.getRisks());
    	//entities.addAll(e.getMilestones());
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
}
