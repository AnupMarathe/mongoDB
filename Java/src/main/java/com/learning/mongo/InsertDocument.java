package com.learning.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.MongoClient;
public class InsertDocument {
	public static void main( String args[] ) {
	
	// Creating a Mongo client
	try(MongoClient mongo = new MongoClient( "localhost" , 27017 ))
	{
	
	// Accessing the database
	MongoDatabase database = mongo.getDatabase("Library");
	
	// Creating a collection
	database.createCollection("sampleCollection");
	System.out.println("Collection created successfully");
	
	// Retrieving a collection
	MongoCollection<Document> collection = database.getCollection("sampleCollection");
	System.out.println("Collection sampleCollection selected successfully");
	Document document = new Document("title", "Learning MongoDB")
	.append("description", "Learning a Mongo DB  - NoSQL database")
	.append("pages", 100);
	
	//Inserting document into the collection
	collection.insertOne(document);
	System.out.println("Document inserted successfully");
	}
	finally
	{
		System.out.println("Mongo client closed");
	}
}
}