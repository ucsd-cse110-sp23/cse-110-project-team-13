import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class Read {

    private static final String uri = "mongodb://gll002:5XzFoHTsaDuKyO4o@ac-jp7sh2s-shard-00-00.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-01.ws94cg8.mongodb.net:27017,ac-jp7sh2s-shard-00-02.ws94cg8.mongodb.net:27017/?ssl=true&replicaSet=atlas-12j7mx-shard-0&authSource=admin&retryWrites=true&w=majority";

    public static boolean userExists(String userEmail) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> userLoginDataCollection = sampleTrainingDB.getCollection("user_login_data");
            Document user = userLoginDataCollection.find(eq("appEmail", userEmail)).first();
            if (user != null) {
                // user exists
                return true;
            } else {
                // user does not exist
                return false;
            }
        }
    }

    public static boolean successfulLogin (String email, String password){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> userLoginDataCollection = sampleTrainingDB.getCollection("user_login_data");
        Document user = userLoginDataCollection.find(eq("appEmail", email)).first();

        if (user != null) {
            // user exists
            if (email.equals(user.getString("appEmail")) && 
            password.equals(user.getString("appPassword"))) {
              return true;
            } else {
              return false;
            }
        } else {
            // user does not exist
            return false;
        }
      }
    }

    public static ArrayList<Question> readUserChatDataByEmail(String userEmail) {
        ArrayList<Question> questionList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
            MongoCollection<Document> userChatDataCollection = sampleTrainingDB.getCollection("user_chat_data");
            Bson filter = eq("appEmail", userEmail);
            List<Document> chats = userChatDataCollection.find(filter).into(new ArrayList<>());
            if (!chats.isEmpty()) {
                for (Document chat : chats) {
                    if (chat.containsKey("Question") && chat.containsKey("Response")) {
                        Question newQuestion = new Question();
                        newQuestion.qName.setText(chat.getString("Question"));
                        newQuestion.answer = chat.getString("Response");
                        newQuestion.isEmail = chat.getBoolean("isEmail");
                        questionList.add(newQuestion);
                    }
                }
            }
        }

        return questionList;
    }

    public static String checkAutomaticLogin(){
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> userLoginDataCollection = sampleTrainingDB.getCollection("user_login_data");
        
        String lastUser;
        try {
          BufferedReader reader = new BufferedReader(new FileReader("lastLogin.txt", StandardCharsets.UTF_8));
          lastUser = reader.readLine();
          reader.close();
        } catch (IOException e){
          lastUser = null;
        }

        if (lastUser != null){
          Document user = userLoginDataCollection.find(eq("appEmail", lastUser)).first();
          return user.getString("appEmail");
        }
        else {
          return null;
        }
      }
    }

    public static String[] sendEmailInfo(String email) {
      String[] info = new String[7];
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> userLoginDataCollection = sampleTrainingDB.getCollection("user_login_data");
        Document user = userLoginDataCollection.find(eq("appEmail", email)).first();

        if (user.getString("firstName") == null || user.getString("sendEmail") == null){
          return null;
        }
        else {
          info[0] = user.getString("firstName");
          info[1] = user.getString("lastName");
          info[2] = user.getString("displayName");
          info[3] = user.getString("sendEmail");
          info[4] = user.getString("SMTPHost");
          info[5] = user.getString("TLSPort");
          info[6] = user.getString("emailPassword");
          return info;
        }
      }
    }

    public static String[] getUserInfo(String email) {
      String[] info = new String[9];
      try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase sampleTrainingDB = mongoClient.getDatabase("sayit_data");
        MongoCollection<Document> userLoginDataCollection = sampleTrainingDB.getCollection("user_login_data");
        Document user = userLoginDataCollection.find(eq("appEmail", email)).first();

        if (user == null){
          System.out.println("User does not exist");
          return null;
        }
        else {
          info[0] = user.getString("appEmail");
          info[1] = user.getString("appPassword");
          info[2] = user.getString("firstName");
          info[3] = user.getString("lastName");
          info[4] = user.getString("displayName");
          info[5] = user.getString("sendEmail");
          info[6] = user.getString("SMTPHost");
          info[7] = user.getString("TLSPort");
          info[8] = user.getString("emailPassword");
        }
        return info;
      }
    }
}