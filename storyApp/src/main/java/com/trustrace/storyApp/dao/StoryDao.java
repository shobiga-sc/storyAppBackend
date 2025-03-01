package com.trustrace.storyApp.dao;

import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Story> getStoriesByUserIdAndStatus(String userId, StoryStatus status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorId").is(userId)
                .and("status").is(status));
        return mongoTemplate.find(query, Story.class);
    }
}
