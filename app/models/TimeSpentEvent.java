package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Striker on 2/4/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSpentEvent {

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
