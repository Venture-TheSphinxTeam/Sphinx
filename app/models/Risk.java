package models;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

import org.bson.types.ObjectId;

/**
 * Created by Striker on 1/28/14.
 */
public class Risk extends Entity{

    private static MongoCollection risks(){
        return PlayJongo.getCollection("risks");
    }
    
    public static Iterable<Risk> findBy(String query){
    	return risks().find("{$and: [{entityType: \""+Risk.TYPE_STRING+ 
				"\"},"+query+"]}").as(Risk.class);
    }

    public void remove(){
        risks().remove(this.id);
    }

    public void removeAll(){
        risks().remove();
    }

    public static Iterable<Risk> getAllWithKey(String key){
        return risks().find("{key: #", key).as(Risk.class);
    }

    public Risk upsert(){
        risks().update("{key: #}",this.getKey()).upsert().merge(this);
        return this;
    }

    public static Risk getFirstWithKey(String key){
        return risks().findOne("{key:#}",key).as(Risk.class);
    }
    
    public static Risk getFirstWithId(String id){
        return risks().findOne("{entityId:#}",id).as(Risk.class);
    }

    public Risk(){}

    @JsonProperty("_id")
    public ObjectId id;
    private List<String> riskTypes;
    private String riskImpact;
    private String riskLikelihood;
    private String riskResponseStrategy;
    private String riskResponsePlan;
    
    public static final String TYPE_STRING = "RISK";

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
