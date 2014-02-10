package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Stephen Yingling on 2/9/14.
 */
public class EventCollection {

    public EventCollection(){}

    @JsonProperty("_id")
    public ObjectId _id;

    protected int currentPage;
    protected int maxPages;
    protected List<ChangeEvent> changeEvents;
    protected List<ReportEvent> reportEvents;
    protected List<TimeSpentEvent> timeSpentEvents;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public List<ChangeEvent> getChangeEvents() {
        return changeEvents;
    }

    public void setChangeEvents(List<ChangeEvent> changeEvents) {
        this.changeEvents = changeEvents;
    }

    public List<ReportEvent> getReportEvents() {
        return reportEvents;
    }

    public void setReportEvents(List<ReportEvent> reportEvents) {
        this.reportEvents = reportEvents;
    }

    public List<TimeSpentEvent> getTimeSpentEvents() {
        return timeSpentEvents;
    }

    public void setTimeSpentEvents(List<TimeSpentEvent> timeSpentEvents) {
        this.timeSpentEvents = timeSpentEvents;
    }
}
