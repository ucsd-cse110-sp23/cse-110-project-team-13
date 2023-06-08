import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class MockUpdate {

    private static final String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";

    public void updateSetupEmailInfo(String applicationEmail, String firstName, String lastName, String displayName, String Email, String SMTPHost, String TLSPort, String emailPassword) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");

            //find document using application email and password in database
            Bson filter = eq("appEmail", applicationEmail);
            Bson updates = Updates.combine(
              Updates.set("firstName", firstName),
              Updates.set("lastName", lastName),
              Updates.set("displayName", displayName),
              Updates.set("sendEmail", Email),
              Updates.set("SMTPHost", SMTPHost),
              Updates.set("TLSPort", TLSPort),
              Updates.set("emailPassword", emailPassword)
            );

            UpdateOptions options = new UpdateOptions().upsert(true);
            loginDataCollection.updateOne(filter, updates, options);
        }
    }
    
    public void automaticallyLog(String email){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");
        Bson filter = eq("appEmail", email);
        Bson updates = Updates.set("previouslyLogged", true);
        UpdateOptions options = new UpdateOptions().upsert(true);
        loginDataCollection.updateOne(filter, updates, options);
      }
    }

    public void manuallyLog(String email){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");
        Bson filter = eq("appEmail", email);
        Bson updates = Updates.set("previouslyLogged", false);
        UpdateOptions options = new UpdateOptions().upsert(true);
        loginDataCollection.updateOne(filter, updates, options);
      }
    }
}