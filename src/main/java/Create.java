import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Create {
    
    private static final String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";

    public static void addQuestionAndAnswer(String question, String answer) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> chatDataCollection = database.getCollection("user_chat_data");

            Document chatData = new Document("_id", new ObjectId());
            chatData.append("Question", question)
                    .append("Response", answer);

            chatDataCollection.insertOne(chatData);
        }
    }
}
