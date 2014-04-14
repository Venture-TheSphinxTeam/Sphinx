package models;
import helpers.OutgoingJSON;
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

    public Response sendVote(){
        String voteStrTemp = Play.application().configuration().getString("vote.post_target");
        voteStrTemp = voteStrTemp.replace(":entityType", entityType);
        voteStrTemp = voteStrTemp.replace(":entityId", Long.toString(entityId));
        voteStrTemp = voteStrTemp.replace(":userName", userName);
        voteStrTemp = voteStrTemp.replace(":voteStatus","vote");
        
        Response r = sendVote(voteStrTemp);
        return r;
    }


    public Response sendVote(String url){
        
        OutgoingJSON out = new OutgoingJSON(url, this.JSONRep());
        Response response = out.sendJson();
        
        return response;
    }

    public Response sendUnVote(){
        String voteStrTemp = Play.application().configuration().getString("vote.post_target");
        voteStrTemp = voteStrTemp.replace(":entityType", entityType);
        voteStrTemp = voteStrTemp.replace(":entityId", Long.toString(entityId));
        voteStrTemp = voteStrTemp.replace(":userName", userName);
        voteStrTemp = voteStrTemp.replace(":voteStatus","unvote");

        return sendUnVote(voteStrTemp);
    }

    public Response sendUnVote(String url){
        OutgoingJSON out = new OutgoingJSON(url, this.JSONRep());
        Response response = out.sendJson();

        return response;
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
