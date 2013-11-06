package helpers;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoControlCenter {

	private MongoClient mongoClient;
	private DB db;

	public MongoControlCenter(String address, int port)
			throws UnknownHostException {

		mongoClient = new MongoClient(address, port);

	}

	public void setDatabase(String dbName) {
		db = mongoClient.getDB(dbName);
	}
	
	public void closeConnection(){
		mongoClient.close();
	}

	public void getInitiativesByUser(String collection, String user) {

	}

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
