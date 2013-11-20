package controllers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class Ingester {
	public void getStuff(){
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target("http://localhost:9998");
		
		Invocation.Builder ib = target.request();
		
		Response r= ib.get();
		
		System.out.println(r.getStatus());
		System.out.println(r.readEntity(String.class));
		
			
		
	}
}
