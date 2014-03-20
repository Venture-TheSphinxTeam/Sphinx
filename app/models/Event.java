package models;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

//import sun.util.resources.CalendarData;
import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by Stephen Yingling on 2/4/14.
 */
public class Event implements Comparable<Event> {

	public Event() {
	}

	@JsonProperty("_id")
	protected ObjectId id;
	protected String eventType;
	protected Entity entity;
	protected long com_date;

	protected static MongoCollection _events() {
		return PlayJongo.getCollection("events");
	}

	public static Iterable<Event> findBy(String query) {
		return _events().find("{" + query + "}").as(Event.class);
	}

	public static List<? extends Event> findByIDListAndEntityType(
			List<EntitySubscription> subs, String type) {

        //Change Report
        ArrayList<String> ids = EntitySubscription.getIdsForEventType(subs,"REPORT");

        String idString = listToMongoString(ids);

		String s = "\"entity.entityId\": {$in:" + idString + "},"
				+ "\"entity.entityType\": \"" + type + "\"";

		ArrayList<Event> result;
		Iterable<? extends Event> events = ReportEvent.findREBy(s);
		result = Event.eventIterToList(events.iterator());

        //Change events
        ids = EntitySubscription.getIdsForEventType(subs,"CREATE");
        ids.addAll(EntitySubscription.getIdsForEventType(subs,"UPDATE"));
        ids.addAll(EntitySubscription.getIdsForEventType(subs,"DELETE"));
        idString = listToMongoString(ids);
        s = "\"entity.entityId\": {$in:" + idString + "},"
                + "\"entity.entityType\": \"" + type + "\"";

		events = ChangeEvent.findCEby(s);
		result.addAll(eventIterToList(events.iterator()));

        ids = EntitySubscription.getIdsForEventType(subs,"TIMESPENT");
        idString = listToMongoString(ids);
        s = "\"entity.entityId\": {$in:" + idString + "},"
                + "\"entity.entityType\": \"" + type + "\"";

		events = TimeSpentEvent.findTSEBy(s);
		result.addAll(eventIterToList(events.iterator()));

		return result;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
        Entity id = entity;
        
        if(id != null){
            if(id.getEntityType().equals(Initiative.TYPE_STRING)){
                this.entity = Initiative.getFirstInitiativeById(id.getEntityId());
            }
            else if(id.getEntityType().equals( Milestone.TYPE_STRING)){
                this.entity = Milestone.getFirstWithId(id.getEntityId());
            }
            else{
                this.entity = Risk.getFirstWithId(id.getEntityId());
            }
        }else{
            this.entity = entity;
        }
    }
    

	public long getComDate() {
		return com_date;
	}

	public static Iterator<? extends Event> getSubscribedEventsForUser(
			String username) {
		List<Event> result = new ArrayList<Event>();

		User user = User.findByName(username);
		if (user == null) {
			return result.iterator();
		}
		List<EntitySubscription> initIdList = user.getInitiativeSubscriptions();
		List<EntitySubscription> mileIdList = user.getMilestoneSubscriptions();
		List<EntitySubscription> riskIdList = user.getRiskSubscriptions();

		Iterator<? extends Event> initIter, mileIter, riskIter, resultIter;
		List<? extends Event> i, m, r;

		i = Event.findByIDListAndEntityType(initIdList, Initiative.TYPE_STRING);

		m = Event.findByIDListAndEntityType(mileIdList, "MILESTONE");

		r = Event.findByIDListAndEntityType(riskIdList, "RISK");

		result.addAll(i);
		result.addAll(m);
		result.addAll(r);

		return result.iterator();
	}

	public static String listToMongoString(List<String> list) {
		String result = "[";
		if (list == null || list.size() == 0) {
			return result + "]";
		}

		for (String s : list) {
			result += "\"" + s + "\"" + ",";
		}
		result = result.substring(0, result.lastIndexOf(',')) + "]";

		return result;
	}

	public String getEventDetails() {
		String result = "";

		result = "An event of type " + eventType + "has been performed on"
				+ entity.getSummary();

		return result;
	}

	public String getDate() throws ParseException {
		long date = 0;

		if (entity.getUpdated() > 0) {
			date = entity.getUpdated();
		} else {
			date = entity.getCreated();
		}

		return new Date(date).toString();
	}

	public static ArrayList<Event> eventIterToList(
			Iterator<? extends Event> iter) {
		ArrayList<Event> result = new ArrayList<Event>();

		while (iter.hasNext()) {
			result.add(iter.next());
		}

		return result;
	}

	public static Iterator<? extends Event> mergeIterators(
			Iterator<? extends Event> i1, Iterator<? extends Event> i2) {

		ArrayList<Event> result = new ArrayList<Event>();

		Event e1 = null;
		Event e2 = null;
		while (i1.hasNext() || i2.hasNext()) {
			if (i1.hasNext() && e1 == null) {
				e1 = i1.next();
			}
			if (i2.hasNext() && e2 == null) {
				e2 = i2.next();
			}
			if (e1 != null && e2 != null) {
				if (e1.getDateAsLong() > e2.getDateAsLong()) {
					result.add(e1);
					e1 = null;
				} else {
					result.add(e2);
					e2 = null;
				}
			} else if (e1 != null) {
				result.add(e1);
				e1 = null;
			} else {
				result.add(e2);
				e2 = null;
			}

		}

		return result.iterator();

	}

	public long getDateAsLong() {

		if (entity == null) {
			return 0;
		} else {
			return entity.getUpdated();
		}
	}

	@Override
	public int compareTo(Event e) {

		if (this.getComDate() > e.getComDate()) {
			return 1;
		} else if (this.getComDate() == e.getComDate()) {
			return 0;
		} else {
			return -1;
		}
	}

	protected String getFormattedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy"); // the format of your date
		String formattedDate = sdf.format(date);

		return formattedDate;
	}
}
