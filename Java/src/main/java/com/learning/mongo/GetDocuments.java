package com.learning.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
public class GetDocuments {
	public static void main( String args[] ) {
	
		// Creating a Mongo client
		try(MongoClient mongo = new MongoClient( "localhost" , 27017 ))
		{
		
		// Creating Credentials
		System.out.println("Connected to the database successfully");
		
		// Accessing the database
		MongoDatabase database = mongo.getDatabase("Library");
		
		// Retrieving a collection
		MongoCollection<Document> collection = database.getCollection("sampleCollection");
		System.out.println("Collection sampleCollection selected successfully");
		Document document1 = new Document("title", "Learning NoSQL")
		.append("description", "Learn about NoSQL databases")
		.append("pages", 200);
		Document document2 = new Document("title", "Learning Cassandra")
		.append("description", "Learning about Cassandra database")
		.append("pages", 300);
		List<Document> list = new ArrayList<Document>();
		list.add(document1);
		list.add(document2);
		collection.insertMany(list);
		// Getting the iterable object
		FindIterable<Document> iterDoc = collection.find();
		// Getting the iterator
		Iterator<Document> it = iterDoc.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		}
		finally
		{
			System.out.println("Mongo client closed");
		}
	}
}