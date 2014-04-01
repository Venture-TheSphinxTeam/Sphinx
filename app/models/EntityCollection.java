package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityCollection {
  private int currentPage;
  private int maxPages;
  private List<Initiative> initiatives;
  private List<Milestone> milestones;
  private List<Risk> risks;
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
public List<Initiative> getInitiatives() {
	return initiatives;
}
public void setInitiatives(List<Initiative> initiatives) {
	this.initiatives = initiatives;
}
public List<Milestone> getMilestones() {
	return milestones;
}
public void setMilestones(List<Milestone> milestones) {
	this.milestones = milestones;
}
public List<Risk> getRisks() {
	return risks;
}
public void setRisks(List<Risk> risks) {
	this.risks = risks;
}

public void createIndices(){
	HashSet<String> priorities = new HashSet<String>(),
			        status = new HashSet<String>(),
			        reporter = new HashSet<String>(),
			        assignee = new HashSet<String>(),
			        labels = new HashSet<String>();
	
	
	for(Initiative i : initiatives){
		if(i.getPriority() !=null){
			priorities.add(i.getPriority());
		}
		if(i.getStatus() != null){
			status.add(i.getStatus());
		}
		reporter.add(i.getReporter());
		assignee.add(i.getAssignee());
		if(i.getLabels() != null){
			labels.addAll(i.getLabels());
		}
		
	}
	
	for(Milestone m : milestones){
		priorities.add(m.getPriority());
		status.add(m.getStatus());
		reporter.add(m.getReporter());
		assignee.add(m.getAssignee());
		if(m.getLabels() != null){
			labels.addAll(m.getLabels());
		}
	}
	
	for(Risk r : risks){
		priorities.add(r.getPriority());
		status.add(r.getStatus());
		reporter.add(r.getReporter());
		assignee.add(r.getAssignee());
		if(r.getLabels() != null){
			labels.addAll(r.getLabels());
		}
	}
	
	FieldIndex f = new FieldIndex();
	f.setFieldName("priority");
	f.setFieldValues(new ArrayList<String>(priorities));
    f.upsert();
    
    f = new FieldIndex();
    f.setFieldName("status");
    f.setFieldValues(new ArrayList<String>(status));
    f.upsert();
    
    f = new FieldIndex();
    f.setFieldName("reporter");
    f.setFieldValues(new ArrayList<String>(reporter));
    f.upsert();
    
    f = new FieldIndex();
    f.setFieldName("assignee");
    f.setFieldValues(new ArrayList<String>(assignee));
    f.upsert();
    
    f=new FieldIndex();
    f.setFieldName("labels");
    f.setFieldValues(new ArrayList<String>(labels));
    f.upsert();
    
	
}
	
	
  
  
}
