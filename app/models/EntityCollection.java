package models;

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
  
  
}
