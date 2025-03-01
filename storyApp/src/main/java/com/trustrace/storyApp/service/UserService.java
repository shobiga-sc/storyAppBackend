package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  @Autowired
   private UserRepository userRepository;

    public Optional<User> getUserById(String userId){
     return userRepository.findById(userId);
 }
    public void updateUser(User user) {
        userRepository.save(user);
    }

}
