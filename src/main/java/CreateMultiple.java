import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static java.util.Arrays.asList;


public class CreateMultiple {

    public static void main(String[] args) {
        String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> usersCollection = sampleTrainingDB.getCollection("users");

            insertOneUser(usersCollection);
            insertManyUsers(usersCollection);
        }
    }

    private static void insertOneUser(MongoCollection<Document> usersCollection) {
        usersCollection.insertOne(generateNewUser("testUser1", "testPassword1"));
        System.out.println("One user inserted with username testUser1.");
    }

    private static void insertManyUsers(MongoCollection<Document> usersCollection) {
        List<Document> users = new ArrayList<>();
        for (int i = 2; i <= 11; i++) {
            users.add(generateNewUser("testUser" + i, "testPassword" + i));
        }

        usersCollection.insertMany(users, new InsertManyOptions().ordered(false));
        System.out.println("Ten users inserted.");
    }

    private static Document generateNewUser(String username, String password) {
        return new Document("_id", new ObjectId())
                .append("username", username)
                .append("password", password);
    }
}

