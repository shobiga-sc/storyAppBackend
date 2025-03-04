package com.trustrace.storyApp.service;

import com.trustrace.storyApp.model.User;
import com.trustrace.storyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void updatePrimeStatus(String userId, boolean isPrimeSubscriber) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPrimeSubscriber(isPrimeSubscriber);
            userRepository.save(user);
        }
    }

    public void updateFreeRead(String userId, List<String> freeRead) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFreeRead(freeRead);
            userRepository.save(user);
        }
    }

}
