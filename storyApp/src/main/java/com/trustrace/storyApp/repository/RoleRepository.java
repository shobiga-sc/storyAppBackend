package com.trustrace.storyApp.repository;
import java.util.Optional;

import com.trustrace.storyApp.model.ERole;
import com.trustrace.storyApp.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}