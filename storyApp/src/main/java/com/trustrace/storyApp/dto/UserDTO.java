package com.trustrace.storyApp.dto;

import com.trustrace.storyApp.model.User;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private boolean primeSubscriber;
    private LocalDate primeSubscriptionExpiry;
    private LocalDateTime signUpDate;
    private List<String> freeRead;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.primeSubscriber = user.isPrimeSubscriber();
        this.primeSubscriptionExpiry = user.getPrimeSubscriptionExpiry();
        this.signUpDate = user.getSignUpDate();
        this.freeRead = user.getFreeRead();
    }


    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isPrimeSubscriber() { return primeSubscriber; }
    public LocalDate getPrimeSubscriptionExpiry() { return primeSubscriptionExpiry; }
    public LocalDateTime getSignUpDate() { return signUpDate; }
    public List<String> getFreeRead(){
        return  freeRead;
    }
}
