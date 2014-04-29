package models;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class UserTime {
	protected String userName;
	protected double timeSpent;
	
	public UserTime(){}

	public String getUserName() {
		return userName;
	}
	
	@JsonAnySetter
	public void setOther(String key, String value){
		userName = key;
		timeSpent = Double.parseDouble(value);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(double timeSpent) {
		this.timeSpent = timeSpent;
	}
	
	
}
