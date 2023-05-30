package mongodb;

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


    private static final Random rand = new Random();


    public static void main(String[] args) {
        String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {


            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sample_training");
            MongoCollection<Document> gradesCollection = sampleTrainingDB.getCollection("grades");


            insertOneDocument(gradesCollection);
            insertManyDocuments(gradesCollection);
        }
    }


    private static void insertOneDocument(MongoCollection<Document> gradesCollection) {
        gradesCollection.insertOne(generateNewGrade(10000d, 1d));
        System.out.println("One grade inserted for studentId 10000.");
    }


    private static void insertManyDocuments(MongoCollection<Document> gradesCollection) {
        List<Document> grades = new ArrayList<>();
        for (double classId = 1d; classId <= 10d; classId++) {
            grades.add(generateNewGrade(10001d, classId));
        }


        gradesCollection.insertMany(grades, new InsertManyOptions().ordered(false));
        System.out.println("Ten grades inserted for studentId 10001.");
    }


    private static Document generateNewGrade(double studentId, double classId) {
        List<Document> scores = asList(new Document("type", "exam").append("score", rand.nextDouble() * 100),
                                       new Document("type", "quiz").append("score", rand.nextDouble() * 100),
                                       new Document("type", "homework").append("score", rand.nextDouble() * 100),
                                       new Document("type", "homework").append("score", rand.nextDouble() * 100));
        return new Document("_id", new ObjectId()).append("student_id", studentId)
                                                  .append("class_id", classId)
                                                  .append("scores", scores);
    }
}

