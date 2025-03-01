package com.trustrace.storyApp.repository;


import com.trustrace.storyApp.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FollowRepository extends MongoRepository<Follow, String> {
    List<Follow> findByFollowerId(String followerId);
    List<Follow> findByAuthorId(String authorId);
    boolean existsByFollowerIdAndAuthorId(String followerId, String authorId);
    void deleteByFollowerIdAndAuthorId(String followerId, String authorId);
    int countByFollowerId(String followerId);
    int countByAuthorId(String authorId);
}
