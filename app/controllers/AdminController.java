package controllers;

import java.net.ConnectException;
import java.util.List;

import javax.ws.rs.ProcessingException;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminTools;

public class AdminController extends Controller{
	public static Form<GetEntitiesForm> entitForm = Form.form(GetEntitiesForm.class);
	
	public static Result forceGetEntities(){
		String message = "";
		Form<GetEntitiesForm> ff = entitForm.bindFromRequest();
		Ingester ingester = new Ingester();
		EntityCollection ec = null;
		try {
			ec = ingester.getEntitiesSince(ff.get().date);
		} catch (ProcessingException e) {
			message += "\n"+e.getMessage();
		}
		if(ec != null){
			List<Initiative> li = ec.getInitiatives();
			for(Initiative i : li){
				i.upsert();
			}
		
			List<Milestone> lm = ec.getMilestones();
		
			for(Milestone m : lm){
				m.upsert();
			}
		
			List<Risk> lr = ec.getRisks();
		
			for(Risk r : lr){
				r.upsert();
			}
			message += "\nEntities pulled";
		}
        EventCollection events =null;
        try{
        	events =ingester.getEventsSince(ff.get().date);
        }catch(ProcessingException e){
        	message += "\n"+e.getMessage();
        }
        if(events != null){
	        List<ChangeEvent> ce = events.getChangeEvents();
	        for(ChangeEvent c : ce){
	        	c.insert();
	        }
	        
	        List<ReportEvent> re = events.getReportEvents();
	        for(ReportEvent rep: re){
	        	rep.insert();
	        }
	        
	        List<TimeSpentEvent> tse = events.getTimeSpentEvents();
	        for(TimeSpentEvent ts : tse){
	        	ts.insert();
	        }
	        message+= "\nEvents pulled";
        }
		return ok(adminTools.render(message, entitForm));
	}

}
