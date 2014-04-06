package rest;

import java.io.IOException;

import models.Vote;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("test")
public class TestResource {
  public static final String TEST_JSON = "{name:\"fake json\"}";
    public static  Vote vote;


  @GET
  @Produces("text/plain")
  public String getTestText(){
    return TEST_JSON;
  }


  @GET
  @Path("/vote")
  @Produces("text/plain")
  public String getVoteJSON(){return vote.JSONRep();}

  @POST
  @Path("/vote")
  @Consumes(MediaType.APPLICATION_JSON)
    public Response testPost(String vote){
	  Vote v = null;
	  
	  ObjectMapper om = new ObjectMapper();
	  try {
		v = om.readValue(vote, Vote.class);
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        this.vote = v;
    return Response.status(200).build();
  }
}
