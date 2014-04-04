package models.facets;

import java.util.ArrayList;

public class MultiFacetValue extends FacetValue{
	
	protected ArrayList<SingleFacetValue> values;
	
	public MultiFacetValue(){
		values = new ArrayList<SingleFacetValue>();
	}

	public ArrayList<SingleFacetValue> getValues() {
		return values;
	}

	public void setValues(ArrayList<SingleFacetValue> values) {
		this.values = values;
	}
	
	public void addValue(SingleFacetValue s){
		values.add(s);
	}

	public String toQueryString() {
		String valString = "$in{[";
		
		for(int i=0; i< values.size()-1; i++){
			valString += values.get(i).toQueryString() + ",";
		}
		
		valString += values.get(values.size()-1).toQueryString() + "]}";
		
		return valString;
	}
	

}
