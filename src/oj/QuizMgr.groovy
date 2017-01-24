package oj;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuizMgr {
    @Autowired
    QuestionDB questionDB

    void addQuestion(Question question) {
        questionDB.save(question)
    }

    Question getRandom() {
        MongoDatabase database = null
        MongoCollection<Document> collection = database.getCollection("Question");
        //AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
        //        new Document("$sample", new Document("size", 1))
        //))
        return null
    }
}
