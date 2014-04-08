package model_tests;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.Iterator;

import models.ChangeEvent;
import models.Entity;
import models.Initiative;
import models.ReportEvent;
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
			   ReportEvent.removeAll();
			   User.removeAll();
			   
				Initiative i = new Initiative();
				i.setAllowedAccessUsers(new ArrayList<String>());
				i.setEntityType(Initiative.TYPE_STRING);
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
				user = u.save();

			   }});
		
		
	}
	
	
	@Test
	public void testGetSubscriptionsForUser(){
		
		running(fakeApplication(), new Runnable(){
			public void run(){
		
				Iterator<? extends Event> events = Event.getSubscribedEventsForUser(user.getUsername());
				
				assertNotNull(events);
				
				assertTrue(events.hasNext());
				
				Event event = events.next();
				
				assertEquals(event.getEventType(), "CREATE");
				
				Entity entity = event.getEntity();
				
				assertNotNull(entity);
				
				assertEquals(entity.getEntityType(), Initiative.TYPE_STRING);
				assertEquals(entity.getEntityId(), _init_id);
				
				assertFalse(events.hasNext());
				
				
				
			}});
	}
	
	
	

}
