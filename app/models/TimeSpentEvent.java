package models;

import java.text.ParseException;
import java.util.Date;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Stephen Yingling on 2/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSpentEvent extends Event {

	private static final String TYPE_SELECTOR = "eventType: \"TIMESPENT\"";

	public static Iterable<TimeSpentEvent> findTSEBy(String query) {
		String q = "{" + TimeSpentEvent.TYPE_SELECTOR + "," + query + "}";
		return _events().find(q).as(TimeSpentEvent.class);

	}

	public void remove() {
		_events().remove(this.id);
	}

	public void removeAll() {
		_events().remove("{" + TYPE_SELECTOR + "}");
	}

	public TimeSpentEvent insert() {
		_events().save(this);
		return this;
	}

	public TimeSpentEvent() {
	}

	protected long periodStartDate;
	protected long periodEndDate;

	public long getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(long periodStartDate) {
		this.periodStartDate = periodStartDate;
	}

	public long getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(long periodEndDate) {
		this.com_date = periodEndDate;
		this.periodEndDate = periodEndDate;
	}

	@Override
	public String getEventDetails() {
		String result = "";

		result += "Work was performed from " + new Date(periodStartDate)
				+ " to " + new Date(periodEndDate);

		return result;
	}

	@Override
	public String getDate(){
		return super.getFormattedDate(new Date(periodEndDate));
	}

}
