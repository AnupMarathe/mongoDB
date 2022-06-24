package com.learning.mongo;

import com.mongodb.client.MongoCollection; 
import com.mongodb.client.MongoDatabase; 
import org.bson.Document; 
import com.mongodb.MongoClient; 
public class SelectCollection { 
   
   public static void main( String args[] ) {  
      
      // Creating a Mongo client 
      try(MongoClient mongo = new MongoClient( "localhost" , 27017 ))
      {
     
   
	      // Accessing the database 
	      MongoDatabase database = mongo.getDatabase("Library");  
	      
	      // Creating a collection 
	      System.out.println("Collection created successfully"); 
	      // Retrieving a collection
	      MongoCollection<Document> collection = database.getCollection("myCollection"); 
	      System.out.println("Collection myCollection selected successfully");
      }
      finally
      {
    	  System.out.println("Mongo client closed successfully");
      }
   }
}