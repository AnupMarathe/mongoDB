package com.learning.mongo;

import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;  
public class CreateCollection { 
   
   public static void main( String args[] ) {  
      
      // Creating a Mongo client 
      try(MongoClient mongo = new MongoClient( "localhost" , 27017 ))
      {
     
      // Creating Credentials 
      MongoCredential credential; 
      credential = MongoCredential.createCredential("sampleUser", "Library", 
         "password".toCharArray()); 
      System.out.println("Connected to the database successfully");  
      
      //Accessing the database 
      MongoDatabase database = mongo.getDatabase("Library");  
      
      //Creating a collection 
      database.createCollection("sampleCollection"); 
      System.out.println("Collection created successfully");
      }
      finally {
    	  System.out.println("Mongo client closed");
      }
   } 
} 