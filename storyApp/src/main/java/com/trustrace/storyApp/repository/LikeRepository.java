package com.trustrace.storyApp.repository;


import com.trustrace.storyApp.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    void deleteByUserIdAndStoryId(String userId, String storyId);
    boolean existsByUserIdAndStoryId(String userId, String storyId);
}
