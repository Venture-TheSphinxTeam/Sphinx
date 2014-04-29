package models;

import java.util.List;

public class UpdateChanges {
	public String fieldName;
	public String fieldId;
	public List<String> oldValues;
	public List<String> newValues;
	public List<Entity> oldEntities;
	public List<Entity> newEntities;
	
	public UpdateChanges(){
		
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public List<String> getOldValues() {
		return oldValues;
	}

	public void setOldValues(List<String> oldValues) {
		this.oldValues = oldValues;
	}

	public List<String> getNewValues() {
		return newValues;
	}

	public void setNewValues(List<String> newValues) {
		this.newValues = newValues;
	}

	public List<Entity> getOldEntities() {
		return oldEntities;
	}

	public void setOldEntities(List<Entity> oldEntities) {
		this.oldEntities = oldEntities;
	}

	public List<Entity> getNewEntities() {
		return newEntities;
	}

	public void setNewEntities(List<Entity> newEntities) {
		this.newEntities = newEntities;
	}
	
}
