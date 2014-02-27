package model_tests;

import static play.test.Helpers.fakeApplication;

import java.util.Iterator;

import models.Initiative;
import models.ReportEvent;
import static play.test.Helpers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.WithApplication;
import static org.junit.Assert.*;

public class ReportEventTest extends WithApplication{

	@BeforeClass
	public static void before(){
		running(fakeApplication(), new Runnable(){
			
		public void run(){
		ReportEvent.removeAll();
		
		ReportEvent re = new ReportEvent();
		re.setEventType("REPORT");
		re.setReportDate(100);
		re.setReportedBy("jay-z");
		re.setReportText("So cool");
		re.setReportType("WEEKLY_STATUS");
		
		Initiative i = new Initiative();
		i.setAssignee("jay-z");
		i.setEntityId("13013");
		i.setEntityType("INITIATIVE");
		i.setDescription("This is a description");
		
		i.upsert();
		
		re.setEntity(i);
		re.insert();
		}});
	}
	
	@Test
	public void testFindREBy(){
		running(fakeApplication(), new Runnable(){
			public void run(){
				Iterable<ReportEvent> r= ReportEvent.findREBy("");
				assertNotNull("Should retrieve one",r);
				Iterator<ReportEvent> a = r.iterator();
				ReportEvent re = a.next();
				assertEquals(re.getReportType(),"WEEKLY_STATUS");
				assertFalse(a.hasNext());
				
			}
		});
	}
}
