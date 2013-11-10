package helpers;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.BasicDBList;

public class MongoControlCenter {

	private MongoClient mongoClient;
	private DB db;

	public MongoControlCenter(String address, int port)
			throws UnknownHostException {

		mongoClient = new MongoClient(address, port);

	}

	/**
	 * 
	 * Sets the database of the Mongo DB controller.
	 * 
	 * @param dbName  The name of the Database to access
	 */
	public void setDatabase(String dbName) {
		db = mongoClient.getDB(dbName);
	}

	/**
	 * 
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		mongoClient.close();
	}



        public Object[] getEventsForUser(String user) {

          DBObject query = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user))
				.or(new BasicDBObject("reporter", user))
				.or(new BasicDBObject("businessOwner", user))
				.get();



          //ArrayList<Integer> ids = new ArrayList<Integer>();
          
          BasicDBList ids = new BasicDBList();

          BasicDBObject eventQuery = new BasicDBObject("entity.entityId", new BasicDBObject("$in", ids));

          DBCollection init = db.getCollection("initiatives");
          DBCollection risks = db.getCollection("risks");
          DBCollection mile = db.getCollection("milestones");
          DBCollection event = db.getCollection("events");          

          ArrayList<DBObject> result = new ArrayList<DBObject>();

          DBCursor c = init.find(query); 
	  DBObject t;
          try {
                while (c.hasNext()){
		  t = c.next();
		  System.out.println(((BasicDBObject)t).getInt("entityId"));
                  ids.add(((BasicDBObject)t).getInt("entityId"));
                }
              }
          finally{
             c.close();
          }

          c = event.find(eventQuery.append("entity.entityType", "INITIATIVE"));

         try{
           while(c.hasNext()){
             result.add(c.next());
           }
         }
         finally{
           c.close();
         }

         


        return result.toArray();

        }




	/**
	 * 
	 * Gets all the entities the supplied user is a part of. 
	 * 
	 * 
	 * @param user	The user supplied.
	 * @return	An array of entity documents the user belongs to.
	 */
	public Object[] getEntitiesByUser(String user) {

		DBObject query = new QueryBuilder()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user))
				.or(new BasicDBObject("reporter", user))
				.or(new BasicDBObject("businessOwner", user))
				.get();
		DBCollection coll = db.getCollection("initiatives");

		DBCursor cursor = coll.find(query);

		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();

	}

	/**
	 * 
	 * Gets all of the documents in a specific collection
	 * 
	 * @param collection	The collection being queried.
	 * @return	An array of all documents in that collection.
	 */
	public Object[] getAllDocuments(String collection) {
		DBCollection coll = db.getCollection(collection);

		DBCursor cursor = coll.find();
		ArrayList<DBObject> list = new ArrayList<DBObject>();

		try {
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list.toArray();
	}

}
