import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class QuickStart {
   public static void main( String[] args ) {
       // Replace the placeholder with your MongoDB deployment's connection string
       String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";


       try (MongoClient mongoClient = MongoClients.create(uri)) {
           MongoDatabase database = mongoClient.getDatabase("sample_mflix");
           MongoCollection<Document> collection = database.getCollection("movies");


           Document doc = collection.find(eq("title", "Back to the Future")).first();
           if (doc != null) {
               System.out.println(doc.toJson());
           } else {
               System.out.println("No matching documents found.");
           }
       }
   }
}

