package models;
import com.fasterxml.jackson.annotation.*;
import java.util.List;


/**
 * POJO object for JSON conversions
 * Created by Stephen Yingling on 1/28/14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Initiative extends Entity{

    public Initiative(){}

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
