package controllers;

import java.util.ArrayList;

import helpers.JSONParser;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

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


	public void getStuff(){
		WebTarget target = client.target("http://localhost:9998/entity/2001-01-01");
		
		Invocation.Builder ib = target.request();
		
		Response r= ib.get();
		
		System.out.println(r.getStatus());
		//System.out.println(r.readEntity(String.class));
		
		JSONParser j = new JSONParser();
		ArrayList<String> k = j.extractEntities(r.readEntity(String.class));
		
		System.out.println(k.get(0));
		
	}
}
