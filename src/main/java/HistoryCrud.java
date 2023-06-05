/*
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

// CreateChat class
public class CreateChat {
    public static void insertChat(MongoCollection<Document> chatCollection, String question, String response) {
        Document chat = new Document("_id", new ObjectId())
                .append("question", question)
                .append("response", response);
        chatCollection.insertOne(chat);
    }
}

// ReadChat class
public class ReadChat {
    public static Document findChat(MongoCollection<Document> chatCollection, String question) {
        return chatCollection.find(eq("question", question)).first();
    }
}

// UpdateChat class
public class UpdateChat {
    public static void changeResponse(MongoCollection<Document> chatCollection, String question, String newResponse) {
        chatCollection.updateOne(eq("question", question), new Document("$set", new Document("response", newResponse)));
    }
}

// DeleteChat class
public class DeleteChat {
    public static void removeChat(MongoCollection<Document> chatCollection, String question) {
        chatCollection.deleteOne(eq("question", question));
    }
}
*/