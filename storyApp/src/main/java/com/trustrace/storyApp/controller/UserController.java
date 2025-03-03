package com.trustrace.storyApp.controller;

import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.UserRepository;
import com.trustrace.storyApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER') or  hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/{userId}/update-prime")
    public ResponseEntity<String> updatePrimeStatus(@PathVariable String userId, @RequestBody Map<String, Boolean> request) {
        boolean isPrimeSubscriber = request.getOrDefault("isPrimeSubscriber", false);
        userService.updatePrimeStatus(userId, isPrimeSubscriber);
        return ResponseEntity.ok("Prime subscription status updated successfully.");
    }




}
