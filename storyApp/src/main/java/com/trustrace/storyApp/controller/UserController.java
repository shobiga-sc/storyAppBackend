package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.UserRepository;
import com.trustrace.storyApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        try {
            logger.info("Fetching user details with ID: {}", userId);
            return userService.getUserById(userId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching user details", e);
            return ResponseEntity.internalServerError().body("Error fetching user details");
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userRepository.deleteById(id);
            logger.info("Deleting user details with ID: {}", id);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            return ResponseEntity.internalServerError().body("Error deleting user");
        }
    }

    @PutMapping("/{userId}/update-prime")
    public ResponseEntity<String> updatePrimeStatus(@PathVariable String userId, @RequestBody Map<String, Boolean> request) {
        try {
            boolean isPrimeSubscriber = request.getOrDefault("isPrimeSubscriber", false);
            logger.info("Updating subscription status to {} for user with ID: {}", isPrimeSubscriber, userId);
            userService.updatePrimeStatus(userId, isPrimeSubscriber);
            return ResponseEntity.ok("Prime subscription status updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating prime subscription status", e);
            return ResponseEntity.internalServerError().body("Error updating prime subscription status");
        }
    }

    @PutMapping("/{userId}/update-freeRead")
    public ResponseEntity<String> updateFreeRead(@PathVariable String userId, @RequestBody Map<String, List<String>> request) {
        try {
            List<String> freeRead = request.getOrDefault("freeRead", new ArrayList<>());
            logger.info("Updating freeRead for user with ID: {}", userId);
            userService.updateFreeRead(userId, freeRead);
            return ResponseEntity.ok("freeRead updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating freeRead", e);
            return ResponseEntity.internalServerError().body("Error updating freeRead");
        }
    }
}