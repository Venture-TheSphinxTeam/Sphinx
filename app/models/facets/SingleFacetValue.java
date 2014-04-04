package models.facets;

public class SingleFacetValue extends FacetValue{
	
	protected String value;
	protected boolean isString;
	
	public SingleFacetValue(){
		
	}
	
	public SingleFacetValue(String s){
		value = s;
		isString = true;
	}
	
	public SingleFacetValue(int i){
		value = Integer.toString(i);
		isString = false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toQueryString() {
		String v;
		
		if(isString){
			v = "\""+value +"\"";
		}
		else{
			v = value;
		}
		return v;
	}
	
	public void setIsString(boolean isS){
		isString = isS;
	}
	
	public boolean getIsString(){
		return isString;
	}
	
	
}
