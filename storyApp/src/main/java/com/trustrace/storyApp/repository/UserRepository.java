package com.trustrace.storyApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.trustrace.storyApp.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findById(String id);

    default String findEmailById(String userId) {
        return findById(userId)
                .map(User::getEmail)
                .orElse(null);
    }

}