package models;

import java.util.List;

/**
 * Created by Stephen Yingling on 3/17/14.
 */
public class EntitySubscription {

    protected String entityId;
    protected List<String> eventTypes;

    public EntitySubscription(String entityId, List<String>eventTypes){
        this.entityId = entityId;
        this.eventTypes = eventTypes;
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
