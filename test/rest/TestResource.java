package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("test")
public class TestResource {
  public static final String TEST_JSON = "{name:\"fake json\"}";


  @GET
  @Produces("text/plain")
  public String getTestText(){
    return TEST_JSON;
  }
}
