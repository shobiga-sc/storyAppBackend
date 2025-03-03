package com.trustrace.storyApp.service;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private double subscriptionAmount = 499.0;

    public double getCurrentAmount() {
        return subscriptionAmount;
    }

    public void updateAmount(double amount) {
        this.subscriptionAmount = amount;
    }
}
