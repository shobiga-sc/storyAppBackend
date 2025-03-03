package com.trustrace.storyApp.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import  com.trustrace.storyApp.service.SubscriptionService;


@Service
public class RazorpayService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    private final SubscriptionService subscriptionService;

    public RazorpayService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public String createOrder(String userId) throws RazorpayException {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);


            double subscriptionAmount = subscriptionService.getCurrentAmount();
            int amountInPaise = (int) (subscriptionAmount * 100);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + userId);
            orderRequest.put("payment_capture", 1);

            Order order = razorpay.orders.create(orderRequest);
            return order.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Error creating Razorpay order", e);
        }
    }
}
