package com.trustrace.storyApp.dao;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryReadTracking;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class StoryReadTrackingDAO {

    @Autowired
    private MongoTemplate mongoTemplate;



    public Map<String, Long> getMonthlyViewsByAuthor(String authorId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        Date startOfMonth = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfMonth = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        MatchOperation matchStage = Aggregation.match(
                Criteria.where("story.authorId").is(authorId)
                        .and("startTime").gte(startOfMonth).lt(endOfMonth)
                        .and("fullyRead").is(true) // Only count full reads
        );


        GroupOperation uniqueUserGroup = Aggregation.group("story.type", "userId")
                .count().as("userViews");


        GroupOperation finalCountGroup = Aggregation.group("_id.storyType")
                .count().as("count");


        Aggregation aggregation = Aggregation.newAggregation(matchStage, uniqueUserGroup, finalCountGroup);


        List<Document> results = mongoTemplate.aggregate(aggregation, "storyReadTracking", Document.class).getMappedResults();


        Map<String, Long> viewsData = new HashMap<>();
        viewsData.put("PAID", 0L);
        viewsData.put("UNPAID", 0L);

        for (Document doc : results) {
            String type = doc.get("_id", String.class);
            Number count = doc.get("count", Number.class);
            viewsData.put(type, count != null ? count.longValue() : 0L);
        }

        return viewsData;
    }

    public long getTotalReadsByStory(String storyId) {
        MatchOperation matchStage = Aggregation.match(
                Criteria.where("story.id").is(storyId)
        );

        GroupOperation groupStage = Aggregation.group().count().as("totalReads");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, groupStage);

        List<Document> results = mongoTemplate.aggregate(aggregation, "storyReadTracking", Document.class).getMappedResults();

        if (results.isEmpty()) {
            return 0;
        }

        return results.get(0).getLong("totalReads");
    }

    public long getTotalReads(String storyId) {
        return mongoTemplate.count(
                Query.query(Criteria.where("storyId").is(storyId)),
                StoryReadTracking.class
        );
    }

    public void saveStoryRead(StoryReadTracking tracking) {
        mongoTemplate.save(tracking);
    }





}
