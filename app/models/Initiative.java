package models;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;


/**
 * POJO object for JSON conversions
 * Created by Stephen Yingling on 1/28/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Initiative extends Entity{
	
	private static MongoCollection initiatives(){
		return PlayJongo.getCollection("initiatives");
	}
	
	public static Iterable<? extends Entity> findBy(String query){
		return initiatives().find("{ entityType: \""+Initiative.TYPE_STRING + 
				"\","+query+"}").as(Initiative.class);
	}
	
	public void remove(){
		initiatives().remove(this.id);
	}
	
	public static void removeAll(){
		initiatives().remove();
	}
	
	public static Iterable<Initiative> getAllWithKey(String key){
		return initiatives().find("{key: #}",key).as(Initiative.class);
	}
	
	public Initiative upsert(){
		
		initiatives().update("{key: #}",this.getKey()).upsert().merge(this);
		
		return this;
	}
	
	public Initiative insert(){
		initiatives().save(this);
		return this;
	}
	
	public Initiative insertIfNew(){
		
		if(getFirstInitiativeByKey(this.key) == null){
			this.insert();
		}
		return this;
	}
	
	public static Initiative getFirstInitiativeByKey(String key){
		return initiatives().findOne("{key:#}",key).as(Initiative.class);
	}
	
	public static Initiative getFirstInitiativeById(String id){
		return initiatives().findOne("{entityId:#}",id).as(Initiative.class);
	}

    public Initiative(){}
    
    @JsonProperty("_id")
    public ObjectId id;
    
    private String projectType;
    private String complexity;
    private String health;
    private String businessGoals;
    private String verificationSteps;
    private String businessUnit;
    private List<String> businessGroups;
    private List<String> providerGroups;
    private String businessOwner;
    private long desiredStartDate;
    private long startDate;
    private long desiredDueDate;
    public static final String TYPE_STRING = "INITIATIVE"; 

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getBusinessGoals() {
        return businessGoals;
    }

    public void setBusinessGoals(String businessGoals) {
        this.businessGoals = businessGoals;
    }

    public String getVerificationSteps() {
        return verificationSteps;
    }

    public void setVerificationSteps(String verificationSteps) {
        this.verificationSteps = verificationSteps;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public List<String> getBusinessGroups() {
        return businessGroups;
    }

    public void setBusinessGroups(List<String> businessGroups) {
        this.businessGroups = businessGroups;
    }

    public List<String> getProviderGroups() {
        return providerGroups;
    }

    public void setProviderGroups(List<String> providerGroups) {
        this.providerGroups = providerGroups;
    }

    public String getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(String businessOwner) {
        this.businessOwner = businessOwner;
    }

    public long getDesiredStartDate() {
        return desiredStartDate;
    }

    public void setDesiredStartDate(long desiredStartDate) {
        this.desiredStartDate = desiredStartDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getDesiredDueDate() {
        return desiredDueDate;
    }

    public void setDesiredDueDate(long desiredDueDate) {
        this.desiredDueDate = desiredDueDate;
    }
}
