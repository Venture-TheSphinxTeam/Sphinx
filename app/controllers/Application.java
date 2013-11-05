package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import play.*;
import play.mvc.*;

import views.html.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;


public class Application extends Controller {

    public static Result index(){
    	
    	

        return ok(index.render("Sphinx"));
    }

    
    public static Result search() throws UnknownHostException {
    	
    	//TEST CODE FOR MONGO DB
    	/**
    	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    	DB db = mongoClient.getDB( "Sphinx" );
    	
    	
    	BasicDBObject query = new BasicDBObject("key", "IN-1");
    	DBCollection coll =  db.getCollection("Initiative");
    	
    	DBCursor cursor = coll.find(query);
    	Object [] dbo = {};
    	try {
    	   while(cursor.hasNext()) {
    	       Collection<Object> values = cursor.next().toMap().values();
    	       List<String> listVals = new ArrayList<String>();
    	       for(Object val: values){
    	    	   listVals.add("" + val );
    	    	   
    	       }
    	       dbo = listVals.toArray();
    	   }
    	} finally {
    	   cursor.close();
    	}
    	**/
    	return ok(search.render());
    
    }
    
    public static Result subscriptions() {
    	return ok(subscriptions.render());
    	
    }
    
    public static Result adminTools() {
    	return ok(adminTools.render());
    }
}
