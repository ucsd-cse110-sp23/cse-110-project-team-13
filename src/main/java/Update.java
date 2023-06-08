import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class Update {

    private static final String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";

    public static void updateSetupEmailInfo(String applicationEmail, String firstName, String lastName, String displayName, String Email, String SMTPHost, String TLSPort, String EmailPassword) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");

            //find document using application email and password in database
            Bson filter = eq("appEmail", applicationEmail);
            Document document = loginDataCollection.find(filter).first();
            
            //upsert firstname
            document.put("firstName", firstName);

            //upsert lastname
            document.put("lastName", lastName);

            //upsert displayname
            document.put("displayName", displayName);

            //upsert email
            document.put("sendEmail", Email);

            //upsert smtphost
            document.put("SMTPHost", SMTPHost);

            //upsert tlsport
            document.put("TLSPort", TLSPort);

            //upsert emailpassword
            document.put("emailPassword", EmailPassword);
        }
    }
    
    public static void automaticallyLog(String email){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");
        Bson filter = eq("appEmail", email);
        Document document = loginDataCollection.find(filter).first();
        document.put("previouslyLogged", true);
      }
    }

    public static void manuallyLog(String email){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> loginDataCollection = database.getCollection("user_login_data");
        Bson filter = eq("appEmail", email);
        Document document = loginDataCollection.find(filter).first();
        document.put("previouslyLogged", false);
      }
    }
}

