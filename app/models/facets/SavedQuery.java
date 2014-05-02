package models.facets;

import helpers.MongoControlCenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;

import play.mvc.Http.Context;
import controllers.Secured;
import models.Entity;
import models.Event;
import models.Initiative;
import models.Milestone;
import models.Risk;
import models.User;

public class SavedQuery {
	protected List<FacetQuery> facets;
	protected List<String> eventTypes;
    protected String name;
	
	public SavedQuery(){
		facets = new ArrayList<FacetQuery>();
		eventTypes = new ArrayList<String>();
	}

	public List<FacetQuery> getFacets() {
		return facets;
	}

	public void setFacets(List<FacetQuery> facets) {
		this.facets = facets;
	}
	
	public void addFacetQuery(FacetQuery fq){
		facets.add(fq);
	}
	
	public List<String> getEventTypes(){
		return eventTypes;
	}
	
	public void setEventTypes(List<String> eventTypes){
		this.eventTypes = eventTypes;
	}

    public void addFacet(Facet f){
        FacetQuery fq = new FacetQuery();
        FacetValue fv = new SingleFacetValue(f.getValue());
        fq.setValue(fv);
        fq.setFacetName(f.getName());

        addFacetQuery(fq);
    }

    public void addEventType(String s){
        eventTypes.add(s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toQueryString(){
		String queryString = "";
		
		for(int i=0; i< facets.size()-1; i++){
			queryString += facets.get(i).toQueryString() +", ";
		}
        queryString += facets.get(facets.size()-1).toQueryString();

		
		return queryString;
	}
    
    public String eventTypesAsMongoString(){
    	String res ="";
    	if(eventTypes.size() >0){
            String eTypes = "[";

            for(int i=0; i<eventTypes.size()-1; i++){
                eTypes += ""+eventTypes.get(i) +",";
            }

            eTypes += eventTypes.get(eventTypes.size()-1) +"]";

            res += eTypes;

        }
    	return res;
    }
    

    
    public List<Event> allEventsForQuery(String username){
    	ArrayList<Event> result = new ArrayList<Event>();
    	
    	Iterator<? extends Entity> ent = Initiative.findByQueryForUser(toQueryString(), username).iterator();
    	ArrayList<String> ids = MongoControlCenter.entityIteratorToIdList(ent);
		result.addAll(Event.findByIDListAndEntityTypeA(ids,
				Initiative.TYPE_STRING, eventTypes));
		
		ent = Milestone.findByQueryForUser(toQueryString(), username).iterator();
		ids = MongoControlCenter.entityIteratorToIdList(ent);
		result.addAll(Event.findByIDListAndEntityTypeA(ids,
				Milestone.TYPE_STRING, eventTypes));

		ent = Risk.findByQueryForUser(toQueryString(), username).iterator();
		ids = MongoControlCenter.entityIteratorToIdList(ent);
		result.addAll(Event.findByIDListAndEntityTypeA(ids, Risk.TYPE_STRING, eventTypes));
		
    	return result;
    }
    
    public static List<Event> allSQEventsForUser(String username){
    	ArrayList<Event> result = new ArrayList<Event>();
    	HashSet<Event> eventSet = new HashSet<Event>();
    	
    	User u = User.findByName(username);
    	
    	if(u != null){
    		for(SavedQuery sq : u.getQuerySubscriptions()){
    			eventSet.addAll(sq.allEventsForQuery(username));
    		}
    	}
    	result.addAll(eventSet);
    	
    	return result;
    }
    
    @Override
    public boolean equals(Object o){
    	boolean result = false;
    	
    	if(o instanceof SavedQuery){
    		
    	}
    	
    	
    	return result;
    }

}
