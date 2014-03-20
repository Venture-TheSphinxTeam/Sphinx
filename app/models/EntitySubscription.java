package models;

import java.util.ArrayList;
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

    public static ArrayList<String> getIdsForEventType(List<EntitySubscription> subs, String eventType){
        ArrayList<String> result = new ArrayList<String>();

        for(EntitySubscription es : subs){
            if(es.getEventTypes().contains(eventType)){
                result.add(es.getEntityId());
            }
        }

        return result;
    }
}
