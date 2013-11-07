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

	public void closeConnection() {
		mongoClient.close();
	}

	public Object[] getInitiativesByUser(String collection, String user) {

		DBObject query = new QueryBuilder().start()
				.or(new BasicDBObject("assignee", user))
				.or(new BasicDBObject("watchers", user)).get();
		DBCollection coll = db.getCollection(collection);

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
