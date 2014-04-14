package models;


import java.util.*;
import com.mongodb.*;
import play.data.validation.Constraints.*;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.annotation.*;
import org.jongo.*;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import uk.co.panaxiom.playjongo.PlayJongo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    private static MongoCollection comments(){
        return PlayJongo.getCollection("comments");
    }

    public Comment(String new_entityType, String new_entityId, String new_createdBy, String new_commentHeader, String new_comment){
        this.entityId = new_entityId;
        this.entityType = new_entityType;
        this.createdBy = new_createdBy; 
        this.commentHeader = new_commentHeader;
        this.comment = new_comment;
    }

    public Comment(){}

    public void insert() {
        comments().save(this);
        //return this;
    }

    public void remove() {
        //comments().remove(this);
    }

    public static Iterable<? extends Comment> findBy(String query){
        return comments().find("{entityId: #}", query).as(Comment.class);
    }

    protected String entityId;
    protected String entityType;//TODO: Change from String to Enum
    protected String createdBy; //TODO: Change from string to User
    protected String commentHeader;
    protected String comment;
    //Will we need a lastUpdateDate or dateCreated
   


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getCreatedBy(){
        return createdBy;
    }
    
    public String getCommentHeader(){
        return commentHeader;
    }

    public void setCommentHeader(String newCommentHeader){
        //TODO: Actually test this
        this.commentHeader = newCommentHeader;
        //then save
        comments().save(this);
    }

    public String getCommentBody(){
        return comment;
    }

    public void setCommentBody(String newCommentBody){
        //TODo: Actually test this
        this.comment = newCommentBody;
        //then save
        comments().save(this);
    }
}

