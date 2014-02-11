package models;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Stephen Yingling on 2/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSpentEvent extends Event{
	
	private static MongoCollection _events(){
		return PlayJongo.getCollection("events");
	}
	
	public void remove(){
        _events().remove(this.id);
    }

    public void removeAll(){
        _events().remove();
    }

    public TimeSpentEvent insert(){
        _events().save(this);
        return this;
    }

    public TimeSpentEvent(){}

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
        this.periodEndDate = periodEndDate;
    }


}
