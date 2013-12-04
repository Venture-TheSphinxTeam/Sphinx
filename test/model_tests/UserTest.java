package model_tests;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;

public class UserTest extends WithApplication {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown(){
      User.users().remove();
    }


   @Test
    public void createAndRetrieveUser() {
	   
	   running(fakeApplication(), new Runnable(){
		   public void run(){
			   User u = new User();
		        u.name = "Bob";
		        u.insert();
		        User bob = User.findByName("Bob");
		        assertNotNull(bob);
		        assertEquals("Bob", bob.name);
		   }
	   });
        
    }


}


