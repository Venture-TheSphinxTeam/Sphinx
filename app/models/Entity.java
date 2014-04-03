package models;


import java.text.SimpleDateFormat;
import java.util.*;

import com.mongodb.*;

import play.data.validation.Constraints.*;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.*;

import org.jongo.*;

import uk.co.panaxiom.playjongo.PlayJongo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {

    public Entity(){
    	this.allowedAccessUsers = new ArrayList<String>();
    }

    protected String entityId;
    protected String entityType;//TODO: Change from String to Enum
    protected Entity workBreakdownParent;
    protected List<Entity> otherParents;
    protected String key;
    protected String summary;
    protected String description;
    protected String priority;
    protected String status;
    protected long dueDate;
    protected String reporter;
    protected String assignee;
    protected List<String> watchers;
    protected List<String> labels;
    protected long created;
    protected long updated;
    protected List<String> allowedAccessUsers;


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Entity getWorkBreakdownParent() {
        return workBreakdownParent;
    }

    public void setWorkBreakdownParent(Entity workBreakdownParent) {
        this.workBreakdownParent = workBreakdownParent;
    }

    public List<Entity> getOtherParents() {
        return otherParents;
    }

    public void setOtherParents(List<Entity> otherParents) {
        this.otherParents = otherParents;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<String> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<String> watchers) {
        this.watchers = watchers;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public List<String> getAllowedAccessUsers() {
        return allowedAccessUsers;
    }

    public void setAllowedAccessUsers(List<String> allowedAccessUsers) {
    	if(allowedAccessUsers == null){
    		this.allowedAccessUsers = new ArrayList<String>();
    	}
    	else{
    		this.allowedAccessUsers = allowedAccessUsers;
    	}
    }
    
    public String getFormattedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
		String formattedDate = sdf.format(date);

		return formattedDate;
	}
    
}

