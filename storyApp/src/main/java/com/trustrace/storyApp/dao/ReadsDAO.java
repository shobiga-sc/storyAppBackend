package com.trustrace.storyApp.dao;

import com.trustrace.storyApp.model.Reads;
import com.trustrace.storyApp.model.StoryReadCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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
import java.util.Objects;

@Repository
public class ReadsDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    public boolean hasUserReadStory(String userId, String storyId) {
        Query query = new Query(Criteria.where("userId").is(userId).and("storyId").is(storyId));
        return mongoTemplate.exists(query, Reads.class);
    }

    public long countReadsByStoryId(String storyId) {
        Query query = new Query(Criteria.where("storyId").is(storyId));
        long count = mongoTemplate.count(query, "reads");
        return count;


    }

    public void saveRead(Reads read) {
        if (!hasUserReadStory(read.getUserId(), read.getStoryId())) {
            mongoTemplate.save(read);
        }
    }


    public void updateStoryReadCount(String storyId, String userId) {
        Query query = new Query(Criteria.where("storyId").is(storyId));
        Update update = new Update().addToSet("userIds", userId);
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
                boolean isPaid = Boolean.TRUE.equals(doc.get("_id"));
                Number countValue = doc.get("count", Number.class);
                readCounts.put(isPaid ? "PAID" : "UNPAID", countValue != null ? countValue.longValue() : 0L);
            }
        }

        return readCounts;
    }

    public long countByMonthAndYear(int month, int year) {
        Query query = new Query(Criteria.where("month").is(month).and("year").is(year));
        return mongoTemplate.count(query, Reads.class);
    }

    public long countByMonthAndYearAndIsPaid(int month, int year, boolean isPaid) {
        Query query = new Query(Criteria.where("month").is(month).and("year").is(year).and("isPaid").is(isPaid));
        return mongoTemplate.count(query, Reads.class);
    }

    public List<String> getAllAuthors() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("authorId").first("authorId").as("authorId")
        );

        AggregationResults<AuthorResult> result = mongoTemplate.aggregate(aggregation, "reads", AuthorResult.class);
        return result.getMappedResults().stream().map(AuthorResult::getAuthorId).toList();
    }



    public long getPaidReadsByAuthor(String authorId, int month, int year) {
        Query query = new Query(Criteria.where("authorId").is(authorId)
                .and("month").is(month)
                .and("year").is(year)
                .and("isPaid").is(true));
        return mongoTemplate.count(query, Reads.class);
    }

    public long getUnpaidReadsByAuthor(String authorId, int month, int year) {
        Query query = new Query(Criteria.where("authorId").is(authorId)
                .and("month").is(month)
                .and("year").is(year)
                .and("isPaid").is(false));
        return mongoTemplate.count(query, Reads.class);
    }

    static class AuthorResult {
        private String authorId;

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }
    }








}
