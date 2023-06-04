/*import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

// CreateUser class
public class CreateUser {
    public static void insertUser(MongoCollection<Document> usersCollection, String username, String password) {
        Document user = new Document("_id", new ObjectId())
                .append("username", username)
                .append("password", password);
        usersCollection.insertOne(user);
    }
}

// ReadUser class
public class ReadUser {
    public static Document findUser(MongoCollection<Document> usersCollection, String username) {
        return usersCollection.find(eq("username", username)).first();
    }
}

// UpdateUser class
public class UpdateUser {
    public static void changePassword(MongoCollection<Document> usersCollection, String username, String newPassword) {
        usersCollection.updateOne(eq("username", username), new Document("$set", new Document("password", newPassword)));
    }
}

// DeleteUser class
public class DeleteUser {
    public static void removeUser(MongoCollection<Document> usersCollection, String username) {
        usersCollection.deleteOne(eq("username", username));
    }
}
*/
