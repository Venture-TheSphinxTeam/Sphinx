package model_tests;

import java.util.ArrayList;
import java.util.List;

import models.*;

import org.junit.*;

import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;

public class UserTest extends WithApplication {
    
	@BeforeClass
	public static void before(){
		running(fakeApplication(), new Runnable(){
			   public void run(){
				   User user = new User();
				   user.setUsername("coolio");
				   ArrayList<String> inits = new ArrayList<String>();
				   inits.add("100");
				   user.setInitiativeSubscriptions(inits);
				   user.insert();
			   }
		}
		);
	}
	
	
	@Before
    public void setUp() {
    }

    @After
    public void tearDown(){
      User.users().remove();
    }
    
    @Test
    public void testLoadInitiativeList(){
    	running(fakeApplication(), new Runnable(){
 		   public void run(){
 			   User u = User.findByName("coolio");
 			   List<String> initIds = u.getInitiativeSubscriptions();
 			   assertNotNull(initIds);
 			   assertEquals(initIds.size(),1);
 			   assertEquals("100",initIds.get(0));
 			   
 			   List<String> mileIds = u.getMilestoneSubscriptions();
 			   assertNull(mileIds);
 			   
 		   }
    	});
    }


    public void createAndRetrieveUser() {
	   running(fakeApplication(), new Runnable(){
		   public void run(){
			   User u = new User();
		        u.setUsername("Bob");
		        u.insert();
		        User bob = User.findByName("Bob");
		        assertNotNull(bob);
		        assertEquals("Bob", bob.getUsername());
		   }
	   });
        
    }
    
    @AfterClass
    public static void After(){
    	running(fakeApplication(), new Runnable(){
			   public void run(){
				   User user = User.findByName("coolio");
				   if(user != null){
					   //user.remove();
				   }
			   }
		}
		);
    }


}


