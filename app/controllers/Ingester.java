package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.JSONParser;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import models.EventCollection;
import play.Play;
import models.EntityCollection;

public class Ingester {
        protected Client client;
	protected WebTarget webTarget;
	protected Invocation.Builder invBuild;


        /**
        ***Creates an ingester with a default rest client
	**/
        public Ingester(){
	  client = ClientBuilder.newClient();
	}

        /**
	 **Creates an ingester pointing at the given url
	 **/
	public Ingester(String target){
          client = ClientBuilder.newClient();
	  webTarget = client.target(target);
	  invBuild = webTarget.request();
	}


        public boolean setTarget(String url){
          webTarget= client.target(url);
	  invBuild = webTarget.request();

	  return true;
	}
        /**
	 **Gets the response from the set target and returns the message as a string
	 **/
	public String getResponse(){
          Response r;
	  
	  if(invBuild != null){
            r = invBuild.get();
	    if(r.getStatus() == 200){
	      return r.readEntity(String.class);
	    }
	  }

	  return null;
	}

        public String getMessageFromTarget(String target){
          WebTarget t = client.target(target);

	  Invocation.Builder ib = t.request();

	  Response r = ib.get();

	  return r.readEntity(String.class);
	}
        
    public EntityCollection getEntitiesSince(Date date) throws ProcessingException{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	WebTarget target = client.target(Play.application().configuration().getString("external.json.source")+
    			"/entity/"+format.format(date));
    	Invocation.Builder ib = target.request();
    	Response r  = null;
    	try{
    	r = ib.get();
    	}catch(ProcessingException e){
    		//TODO: Logging
    		throw e;
    	}
    	
    	JSONParser j = new JSONParser();
    	
    	return j.extractEntities(r.readEntity(String.class));
    }

    public EventCollection getEventsSince(Date date) throws ProcessingException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        WebTarget target = client.target(Play.application().configuration().getString("external.json.source") +
                "/event/"+ format.format(date));
        Invocation.Builder ib = target.request();
        Response r = null;
        try{
        r = ib.get();
        }catch (ProcessingException e){
        	//TODO: Logging
        	throw e;
        }

        JSONParser j = new JSONParser();

        return j.extractEvents(r.readEntity(String.class));
    }


	public void getStuff(){
		WebTarget target = client.target("http://localhost:9998/entity/2001-01-01");
		
		Invocation.Builder ib = target.request();
		
		Response r= ib.get();
		
		System.out.println(r.getStatus());
		//System.out.println(r.readEntity(String.class));
		
		JSONParser j = new JSONParser();
		EntityCollection entities= j.extractEntities(r.readEntity(String.class));
		
		
		
	}
}
