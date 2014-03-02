package rest;

import models.Vote;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("test")
public class TestResource {
  public static final String TEST_JSON = "{name:\"fake json\"}";
    public Vote vote;


  @GET
  @Produces("text/plain")
  public String getTestText(){
    return TEST_JSON;
  }


  @GET
  @Path("/vote")
  @Produces("text/plain")
  public String getVoteJSON(){return vote.getJSONRep();}

  @POST
  @Path("/vote")
  @Consumes(MediaType.APPLICATION_JSON)
    public Response testPost(Vote vote){
        this.vote = vote;
    return Response.status(200).build();
  }
}
