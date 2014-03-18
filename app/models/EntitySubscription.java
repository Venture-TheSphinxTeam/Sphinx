package models;

import java.util.List;

/**
 * Created by Stephen Yingling on 3/17/14.
 */
public class EntitySubscription {

    protected String entityType;
    protected String entityId;
    protected List<String> eventTypes;

    public EntitySubscription(){}

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
