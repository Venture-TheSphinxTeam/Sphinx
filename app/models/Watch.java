package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.OutgoingJSON;
import play.Play;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

/**
 * Created by Stephen Yingling on 3/4/14.
 */
public class Watch {

    protected String entityType;
    protected long entityId;
    protected String userName;

    public Watch(String entityType, long entityId, String userName){
        this.entityId = entityId;
        this.entityType = entityType;
        this.userName = userName;
    }

    public Watch(){}

    public Response sendWatch() throws JsonProcessingException, ProcessingException{
        String voteStrTemp = Play.application().configuration().getString("watch.post_target");
        voteStrTemp = voteStrTemp.replace(":entityType", entityType);
        voteStrTemp = voteStrTemp.replace(":entityId", Long.toString(entityId));
        voteStrTemp = voteStrTemp.replace(":userName", userName);
        voteStrTemp = voteStrTemp.replace(":watchStatus","watch");


        return sendWatch(voteStrTemp);
    }


    public Response sendWatch(String url) throws JsonProcessingException, ProcessingException{

        OutgoingJSON out = new OutgoingJSON(url, this.JSONRep());
        Response response = out.sendJson();

        return response;
    }

    public Response sendUnWatch() throws JsonProcessingException, ProcessingException {
        String voteStrTemp = Play.application().configuration().getString("watch.post_target");
        voteStrTemp = voteStrTemp.replace(":entityType", entityType);
        voteStrTemp = voteStrTemp.replace(":entityId", Long.toString(entityId));
        voteStrTemp = voteStrTemp.replace(":userName", userName);
        voteStrTemp = voteStrTemp.replace(":voteStatus","unwatch");

        return sendUnWatch(voteStrTemp);
    }

    public Response sendUnWatch(String url){
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
