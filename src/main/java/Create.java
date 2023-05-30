import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;


import java.util.Random;


import static java.util.Arrays.asList;


public class Create {


    public static void main(String[] args) {
        String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {


            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("grades");


            Random rand = new Random();
            Document student = new Document("_id", new ObjectId());
            student.append("student_id", 10000d)
                   .append("class_id", 1d)
                   .append("scores", asList(new Document("type", "exam").append("score", rand.nextDouble() * 100),
                                            new Document("type", "quiz").append("score", rand.nextDouble() * 100),
                                            new Document("type", "homework").append("score", rand.nextDouble() * 100),
                                            new Document("type", "homework").append("score", rand.nextDouble() * 100)));


            gradesCollection.insertOne(student);
        }
    }
}

