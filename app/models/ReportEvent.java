package models;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

/**
 * Created by Striker on 2/4/14.
 */
public class ReportEvent extends Event  {
	
	
	public static Iterable<ReportEvent> findREBy(String query){
		String q = "{"+ReportEvent.TYPE_SELECTOR+","+query+"}";
		return _events().find(q).as(ReportEvent.class);
		
	}
	
	
	public void remove(){
        _events().remove(this.id);
    }

	
    public static void removeAll(){
        _events().remove("{"+ReportEvent.TYPE_SELECTOR+"}");
    }

    public ReportEvent insert(){
        _events().save(this);
        return this;
    }

    public ReportEvent(){
    	eventType = "REPORT";
    }

    protected long reportDate;
    protected String reportedBy;
    protected String reportType;
    protected String reportText;
    public static final String TYPE_SELECTOR = "eventType: \"REPORT\"";

    public long getReportDate() {
        return reportDate;
    }

    public void setReportDate(long reportDate) {
        this.reportDate = reportDate;
        this.com_date = reportDate;
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
    
    public static String prettifyReportType(String rType){
    	if(rType == null){return null;}
    	
    	String result = rType.toLowerCase();
    			
    	
    	return result;
    }
    
    @Override
    public String getEventDetails(){
    	String result ="";
    	
    	result += "A " + prettifyReportType(reportType) + 
    			" report was submitted by " + reportedBy + " showing that: </br></br>"+reportText;
    	
		return result;
    	
    }
    
    @Override
    public String getDate(){
    	return super.getFormattedDate(new Date(reportDate));
    }
}
