package rest;

import com.sun.net.httpserver.HttpServer;
import controllers.Ingester;
import models.Vote;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Stephen Yingling on 3/2/14.
 */
public class VoteTest {

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
    public void testVotePost(){
        Vote v = new Vote("INITIATIVE", 132,"jay-z");
        v.sendVote(TESTURL);

        Ingester i = new Ingester();
        i.setTarget(TESTURL);
        String r = i.getResponse();

        System.out.println(r);


    }
}
