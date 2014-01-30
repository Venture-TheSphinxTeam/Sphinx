package model_tests;

import static org.junit.Assert.*;

import java.util.Date;

import models.Initiative;
import models.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static play.test.Helpers.*;
import play.test.WithApplication;

public class InitiativeTest extends WithApplication {
    
	protected Initiative i;
	protected final String TEST_STRING = "SUPERCOOL";
	@Before
    public void setUp() {
		Initiative.removeAll();
		Initiative.removeAll();
		Initiative.removeAll();
		i = new Initiative();
    	i.setKey(TEST_STRING);
    	
    }

    @After
    public void tearDown(){
    	Initiative.removeAll();
    }
    
    @Test
    public void cannotRetrieveNonExistantInitiative(){
    	running(fakeApplication(), new Runnable(){
            public void run(){
            	assertNull(Initiative.getFirstInitiativeByKey(TEST_STRING));
            }
    	});
    }
    
    @Test
    public void createAndRetrieveInitiative(){
    	
    	running(fakeApplication(),new Runnable(){
    		public void run(){
		    	i.insert();
		    	Initiative retreived = Initiative.getFirstInitiativeByKey(TEST_STRING);
		    	assertNotNull(retreived);
		    	assertEquals(i.id, retreived.id);
    		}
    	});
    }

    @Test
    public void insertIfNew() {
    	running(fakeApplication(), new Runnable(){
    		public void run(){
		    	i.setDescription("I'm the first!");
		    	i.insert();
		    	Initiative j = new Initiative();
		    	j.setKey(TEST_STRING);
		    	j.setDescription("Second Place");
		    	
		    	j.insertIfNew();
		    	
		    	Initiative r = Initiative.getFirstInitiativeByKey(TEST_STRING);
		    	
		    	assertNotNull(r);
		    	assertEquals(r.getDescription(),"I'm the first!");
		    	assertNotEquals(r.getDescription(),"Second Place");
    		}
    	});
    }

    @Test
    public void upsert(){
    	running(fakeApplication(),new Runnable(){
    		public void run(){
    			assertNull(Initiative.getFirstInitiativeByKey(TEST_STRING));
    			i.setDescription("So Cool");
    			i.upsert();
    			Initiative r = Initiative.getFirstInitiativeByKey(TEST_STRING);
    			assertNotNull("Upsert should insert record",r);
    			assertEquals(r.getDescription(),"So Cool");
    			i.setDescription("Even Cooler!");
    			i.upsert();
    			r = Initiative.getFirstInitiativeByKey(TEST_STRING);
    			assertEquals(r.getDescription(),"Even Cooler!");
    			Iterable<Initiative> it = Initiative.getAllByKey(TEST_STRING);
    			assertNotNull(it);
    			assertNotNull(it.iterator());
    			assertFalse("Should only return one thing",it.iterator().hasNext());
    		}
    	});
    }
    
@AfterClass
public static void insertCrap(){
	running(fakeApplication(),new Runnable(){
		public void run(){
			Initiative k = new Initiative();
			k.setKey("SO COOL");
			k.upsert();
		}
	});
}

}
