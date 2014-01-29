package models;
import java.util.List;
import com.fasterxml.jackson.annotation.*;


/**
 * POJO object for java to/from JSON
 * Created by Striker on 1/28/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Milestone extends Entity{

    public Milestone() {}

    private String health;
    private String verificationSteps;
    private long desiredStartDate;
    private long startDate;
    private long desiredDueDate;


    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getVerificationSteps() {
        return verificationSteps;
    }

    public void setVerificationSteps(String verificationSteps) {
        this.verificationSteps = verificationSteps;
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
