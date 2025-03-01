package com.trustrace.storyApp.dao;

import com.trustrace.storyApp.model.Reads;
import com.trustrace.storyApp.model.StoryReadCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReadsDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Check if user already read the story
    public boolean hasUserReadStory(String userId, String storyId) {
        Query query = new Query(Criteria.where("userId").is(userId).and("storyId").is(storyId));
        return mongoTemplate.exists(query, Reads.class);
    }

    public long countReadsByStoryId(String storyId) {
        Query query = new Query(Criteria.where("storyId").is(storyId));
        long count = mongoTemplate.count(query, "reads");
        return count;


    }

    // Save user read only if it's unique
    public void saveRead(Reads read) {
        if (!hasUserReadStory(read.getUserId(), read.getStoryId())) {
            mongoTemplate.save(read);
        }
    }

    // Increment unique read count for a story
    public void updateStoryReadCount(String storyId, String userId) {
        Query query = new Query(Criteria.where("storyId").is(storyId));
        Update update = new Update().addToSet("userIds", userId); // Prevent duplicate user reads
        mongoTemplate.upsert(query, update, StoryReadCount.class);
    }

    public Map<String, Long> getMonthlyReadsByAuthor(String authorId, int year, int month) {
        MatchOperation match = Aggregation.match(
                Criteria.where("authorId").is(authorId).and("year").is(year).and("month").is(month)
        );

        GroupOperation group = Aggregation.group("isPaid").count().as("count");

        Aggregation aggregation = Aggregation.newAggregation(match, group);

        List<Document> results = mongoTemplate.aggregate(aggregation, "reads", Document.class).getMappedResults();

        Map<String, Long> readCounts = new HashMap<>();
        readCounts.put("PAID", 0L);
        readCounts.put("UNPAID", 0L);

        for (Document doc : results) {
            if (doc.containsKey("_id") && doc.containsKey("count")) {
                boolean isPaid = Boolean.TRUE.equals(doc.get("_id")); // Handle null values safely
                Number countValue = doc.get("count", Number.class); // Get as Number to support both Integer & Long
                readCounts.put(isPaid ? "PAID" : "UNPAID", countValue != null ? countValue.longValue() : 0L);
            }
        }

        return readCounts;
    }





}
