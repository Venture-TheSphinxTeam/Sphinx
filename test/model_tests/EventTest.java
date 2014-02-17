package model_tests;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.List;

import models.ChangeEvent;
import models.Entity;
import models.Initiative;
import models.User;
import models.Event;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.WithApplication;

public class EventTest extends WithApplication{
	
	protected static User user;
	private static final String _init_id= "100";
	
	
	@BeforeClass
	public static void beforeAll(){
		running(fakeApplication(), new Runnable(){
		   public void run(){
			   Initiative.removeAll();
			   ChangeEvent.removeAll();
			   User.removeAll();
			   
				Initiative i = new Initiative();
				i.setAllowedAccessUsers(new ArrayList<String>());
				i.setEntityType("INITIATIVE");
				i.setEntityId(_init_id);
				i.upsert();
				
				ChangeEvent e = new ChangeEvent();
				e.setEventType("CREATE");
				e.setEntity(i);
				e.insert();
				
				ArrayList<String> id = new ArrayList<String>();
				id.add(i.getEntityId());
				
				User u = new User();
				u.setInitiativeSubscriptions(id);
				u.setUsername("jay-z");
				user = u.insert();
		
			   }});
		
		
	}
	
	
	@Test
	public void testGetSubscriptionsForUser(){
		
		running(fakeApplication(), new Runnable(){
			public void run(){
		
				List<Event> events = Event.getSubscribedEventsForUser(user.getUsername());
				
				assertNotNull(events);
				assertTrue(events.size()>0);
				
				Event event = events.get(0);
				
				assertEquals(event.getEventType(), "CREATE");
				
				Entity entity = event.getEntity();
				
				assertNotNull(entity);
				
				assertEquals(entity.getEntityType(), "INITIATIVE");
				assertEquals(entity.getEntityId(), _init_id);
			}});
	}

}
