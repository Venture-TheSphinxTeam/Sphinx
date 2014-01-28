package models;

import java.util.List;

/**
 * Created by Striker on 1/28/14.
 */
public class Risk extends Entity{

    public Risk(){}

    private List<String> riskTypes;
    private String riskImpact;
    private String riskLikelihood;
    private String riskResponseStrategy;
    private String riskResponsePlan;

    public List<String> getRiskTypes() {
        return riskTypes;
    }

    public void setRiskTypes(List<String> riskTypes) {
        this.riskTypes = riskTypes;
    }

    public String getRiskImpact() {
        return riskImpact;
    }

    public void setRiskImpact(String riskImpact) {
        this.riskImpact = riskImpact;
    }

    public String getRiskLikelihood() {
        return riskLikelihood;
    }

    public void setRiskLikelihood(String riskLikelihood) {
        this.riskLikelihood = riskLikelihood;
    }

    public String getRiskResponseStrategy() {
        return riskResponseStrategy;
    }

    public void setRiskResponseStrategy(String riskResponseStrategy) {
        this.riskResponseStrategy = riskResponseStrategy;
    }

    public String getRiskResponsePlan() {
        return riskResponsePlan;
    }

    public void setRiskResponsePlan(String riskResponsePlan) {
        this.riskResponsePlan = riskResponsePlan;
    }
}
