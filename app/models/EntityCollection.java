package models;

import java.util.List;

public class EntityCollection {
  private int currentPage;
  private int maxPages;
  private List<String> initiatives;
  private List<String> milestones;
  private List<String> risks;
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
public List<String> getInitiatives() {
	return initiatives;
}
public void setInitiatives(List<String> initiatives) {
	this.initiatives = initiatives;
}
public List<String> getMilestones() {
	return milestones;
}
public void setMilestones(List<String> milestones) {
	this.milestones = milestones;
}
public List<String> getRisks() {
	return risks;
}
public void setRisks(List<String> risks) {
	this.risks = risks;
}
  
  
}
