package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(String userId) {
        try {
            return userRepository.findById(userId);
        } catch (Exception e) {
            logger.error("Error fetching user by ID", e);
            throw new RuntimeException("Error fetching user", e);
        }
    }

    public void updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void updatePrimeStatus(String userId, boolean isPrimeSubscriber) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPrimeSubscriber(isPrimeSubscriber);
                userRepository.save(user);
            } else {
                logger.warn("User with ID {} not found for updating prime status", userId);
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            logger.error("Error updating prime status", e);
            throw new RuntimeException("Error updating prime status", e);
        }
    }

    public void updateFreeRead(String userId, List<String> freeRead) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setFreeRead(freeRead);
                userRepository.save(user);
            } else {
                logger.warn("User with ID {} not found for updating free read", userId);
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            logger.error("Error updating free read", e);
            throw new RuntimeException("Error updating free read", e);
        }
    }
}