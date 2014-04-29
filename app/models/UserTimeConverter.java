package models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class UserTimeConverter {
	
	protected List<UserTime> UserTimes;
	
	public UserTimeConverter(){
		UserTimes = new ArrayList<UserTime>();
	}
	
	@JsonAnySetter
	protected void setAny(String key, String value){
		UserTime ut = new UserTime();
		ut.setUserName(key);
		double timeSpent = 0;
		try{
			timeSpent = Double.parseDouble(value);
		}catch (Exception e){
			
		}
		ut.setTimeSpent(timeSpent);
		
		UserTimes.add(ut);
	}

	public List<UserTime> getUserTimes() {
		return UserTimes;
	}

	public void setUserTimes(List<UserTime> userTimes) {
		UserTimes = userTimes;
	}
	
	
}
