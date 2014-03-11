package models;

import java.util.ArrayList;
import java.util.List;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

public class FieldIndex {
	
	protected static MongoCollection fieldIndices(){
		return PlayJongo.getCollection("fieldIncices");
	}
	
	protected String fieldName;
	protected List<String> fieldValues;
	
	public void upsert(){
		fieldIndices().update("{fieldName: #}",this.getFieldName()).upsert().merge(this);
	}
	
	public FieldIndex findIndex(String fieldName){
		return fieldIndices().findOne("{fieldName: }",this.fieldName).as(FieldIndex.class);
	}
	
	public FieldIndex(){
		fieldValues= new ArrayList<String>();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
		if(fieldValues == null){
			fieldValues = new ArrayList<String>();
		}
		
		
	}
	
	

}
