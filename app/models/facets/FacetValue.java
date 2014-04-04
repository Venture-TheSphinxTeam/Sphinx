package models.facets;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS,property="_class")
public abstract class FacetValue {

	public abstract String toQueryString();

}
