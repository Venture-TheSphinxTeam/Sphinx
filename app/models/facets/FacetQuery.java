package models.facets;

public class FacetQuery {
	
	protected String facetName;
	protected FacetValue value;
	
	public FacetQuery(){
		
	}

	public String getFacetName() {
		return facetName;
	}

	public void setFacetName(String facetName) {
		this.facetName = facetName;
	}

	public FacetValue getValue() {
		return value;
	}

	public void setValue(FacetValue value) {
		this.value = value;
	}
	
	public String toQueryString(){
		String s = "";
		s += facetName +": " + value.toQueryString();
		return s;
	}

}
