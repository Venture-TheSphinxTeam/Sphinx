package models;
import play.Play;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Stephen Yingling on 2/27/14.
 */
public class Vote {

    protected String entityType;
    protected long entityId;
    protected String userName;

    public Vote(String entityType, long entityId, String userName){
        this.entityId = entityId;
        this.entityType = entityType;
        this.userName = userName;
    }
    
    public Vote(){}

    public boolean sendVote(){
        String baseUrl = Play.application().configuration().getString("sphinx.external_post_url");
        baseUrl += "/entity/" + entityType +"/" + entityId +"/vote/" + userName;

        return sendVote(baseUrl);
    }


    public boolean sendVote(String url){
        boolean sent = false;
        //String baseUrl = Play.application().configuration().getString("sphinx.external_post_url");
       // baseUrl += "/entity/" + entityType +"/" + entityId +"/vote/" + userName;

        Client c = ClientBuilder.newClient();
        WebTarget wt = c.target(url);

        Form voteForm = new Form();
        voteForm.param("entityType",this.entityType);
        voteForm.param("Id", Long.toString(this.entityId));
        voteForm.param("username",this.userName);
        
        

        Response response = wt.request().post(Entity.json(this.JSONRep()));
        if(response.getStatus() == 200){
            sent = true;
        }

        return sent;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String JSONRep(){
    	
    	ObjectMapper om = new ObjectMapper();
    	String result = "";
    	
    	try {
			result = om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return result;
    }
}
