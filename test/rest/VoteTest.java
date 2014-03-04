package rest;

import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeApplication;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import controllers.Ingester;
import models.Vote;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.WithApplication;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

/**
 * Created by Stephen Yingling on 3/2/14.
 */
public class VoteTest extends WithApplication{

    protected static HttpServer server;
    protected final static String TESTURL="http://localhost:9997/test/vote";

    @BeforeClass
    public static void setUpTestServer() throws URISyntaxException {
        ResourceConfig rc = new ResourceConfig(TestResource.class);
        URI uri = new URI("http","","localhost",9997,"/",null,null);
        server = JdkHttpServerFactory.createHttpServer(uri, rc);
    }

    @AfterClass
    public static void tearDownServer(){
        server.stop(0);
    }

    @Test
    public void testVotePost() throws JsonParseException, JsonMappingException, IOException{
        Vote v = new Vote("INITIATIVE", 132,"jay-z");
        Response status = v.sendVote(TESTURL);

        Ingester i = new Ingester();
        i.setTarget(TESTURL);
        String r = i.getResponse();
        ObjectMapper om = new ObjectMapper();
        Vote response = om.readValue(r, Vote.class);
        
        assertEquals(200, status.getStatus());
        assertNotNull(response);
        assertEquals(v.getEntityId(), response.getEntityId());
        assertEquals(v.getEntityType(), response.getEntityType());
        assertEquals(v.getUserName(), response.getUserName());
        
        


    }
    
    @Test
    public void testSendVoteNoParams() throws JsonParseException, JsonMappingException, IOException{
    	running(fakeApplication(), new Runnable(){
    		public void run() {
    			
		    	Vote v = new Vote("MILESTONE",999,"RickyWinterboard");
		    	Response r = v.sendVote();
		    	
		    	Ingester i = new Ingester();
		        i.setTarget(TESTURL);
		        String s = i.getResponse();
		        ObjectMapper om = new ObjectMapper();
		        Vote response = null;
				try {
					response = om.readValue(s, Vote.class);
				} catch (JsonParseException e) {
					fail(e.getMessage());
				} catch (JsonMappingException e) {
					fail(e.getMessage());
				} catch (IOException e) {
					fail(e.getMessage());
				}
		        
		        assertEquals(200, r.getStatus());
		        assertNotNull(response);
		        assertEquals(v.getEntityId(), response.getEntityId());
		        assertEquals(v.getEntityType(), response.getEntityType());
		        assertEquals(v.getUserName(), response.getUserName());
    		}
    	});
    }
}
