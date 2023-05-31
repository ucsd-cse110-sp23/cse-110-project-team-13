import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

    
    

public class DatabaseTest {
    @Test
    public void testBasicCRUD() throws Exception {
        //setup 
        String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase testDB = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> testCollection = testDB.getCollection("test");
            Document testDocument = new Document("_id", new ObjectId());
        
            // create
            testDocument.append("test", "This is a test123123lala");
            testCollection.insertOne(testDocument);
            // read
            Document readDocument = testCollection.find(Filters.eq("test", "This is a test123123lala")).first();
            assertTrue(readDocument != null);
            assertTrue(readDocument.get("test").equals("This is a test123123lala")); // ensure value is correct
            // update
            Bson filter = eq("test", "This is a test123123lala");
            Bson updateOperation = set("test", "This has been updated 12345ye");
            UpdateResult updateResult = testCollection.updateOne(filter, updateOperation);
            assertTrue(updateResult.wasAcknowledged());
            // read again
            Document readDocumentAgain = testCollection.find(Filters.eq("test", "This has been updated 12345ye")).first();
            assertTrue(readDocumentAgain != null);
            assertTrue(readDocumentAgain.get("test").equals("This has been updated 12345ye")); // ensure updated value is correct
            // delete
            DeleteResult deleteResult = testCollection.deleteOne(Filters.eq("test", "This has been updated 12345ye"));
            assertTrue(deleteResult.wasAcknowledged()); // make sure it was deleted
            Document readDeleteDocument = testCollection.find(Filters.eq("test", "This has been updated 12345ye")).first();
            assertEquals(readDeleteDocument, null); // make sure item can no longer be found
        }
    }
}
