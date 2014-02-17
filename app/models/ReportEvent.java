package models;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by Striker on 2/4/14.
 */
public class ReportEvent extends Event  {
	
	
	public void remove(){
        _events().remove(this.id);
    }

    public void removeAll(){
        _events().remove();
    }

    public ReportEvent insert(){
        _events().save(this);
        return this;
    }

    public ReportEvent(){}

    protected long reportDate;
    protected String reportedBy;
    protected String reportType;
    protected String reportText;

    public long getReportDate() {
        return reportDate;
    }

    public void setReportDate(long reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
}
