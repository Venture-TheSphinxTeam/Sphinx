package models.facets;

import java.util.ArrayList;
import java.util.List;

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

        if(eventTypes.size() >0){
            String eTypes = "[";

            for(int i=0; i<eventTypes.size()-1; i++){
                eTypes += eventTypes.get(i) +",";
            }

            eTypes += eventTypes.get(eventTypes.size()-1) +"]";

            queryString += ", eventTypes: $in: {" + eTypes +"}";

        }
		
		return queryString;
	}
    
    @Override
    public boolean equals(Object o){
    	boolean result = false;
    	
    	if(o instanceof SavedQuery){
    		
    	}
    	
    	
    	return result;
    }

}
