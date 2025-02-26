package com.trustrace.storyApp.model;


import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    private boolean isPrimeSubscriber = false;
    private LocalDate primeSubscriptionExpiry;
    private List<String> followedAuthors;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isPrimeSubscriber() {
        return isPrimeSubscriber;
    }

    public void setPrimeSubscriber(boolean primeSubscriber) {
        isPrimeSubscriber = primeSubscriber;
    }

    public LocalDate getPrimeSubscriptionExpiry() {
        return primeSubscriptionExpiry;
    }

    public void setPrimeSubscriptionExpiry(LocalDate primeSubscriptionExpiry) {
        this.primeSubscriptionExpiry = primeSubscriptionExpiry;
    }

    public List<String> getFollowedAuthors() {
        return followedAuthors;
    }

    public void setFollowedAuthors(List<String> followedAuthors) {
        this.followedAuthors = followedAuthors;
    }
}