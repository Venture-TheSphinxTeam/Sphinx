package rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jdkhttp.*;

import com.sun.net.httpserver.HttpServer;

import controllers.Ingester;
import org.junit.*;




public class IngesterTest{
  protected static HttpServer server;
  protected final static String TESTURL="http://localhost:9997/test";

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
  public void testIngester() throws URISyntaxException{
    Ingester i = new Ingester();
    i.setTarget(TESTURL);
    String r = i.getResponse();
    Assert.assertTrue(r.equals(TestResource.TEST_JSON));
    Assert.assertNotEquals(r, "NOT AT ALL WHAT IS COMING IN");
  }
  
  @Test
  public void testIngesterOtherConstructor() {
    Ingester i = new Ingester(TESTURL);
    String r = i.getResponse();
    Assert.assertEquals(r,TestResource.TEST_JSON);
  }

  @Test
  public void testGetMessageFromTarget(){
    Ingester i = new Ingester();
    String r = i.getMessageFromTarget(TESTURL);
    Assert.assertEquals(r,TestResource.TEST_JSON);
  }


}
