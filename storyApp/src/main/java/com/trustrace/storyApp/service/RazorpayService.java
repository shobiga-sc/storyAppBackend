package com.trustrace.storyApp.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import  com.trustrace.storyApp.service.SubscriptionService;
import org.springframework.web.client.RestTemplate;



@Service
public class RazorpayService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    private final SubscriptionService subscriptionService;

    private final RestTemplate restTemplate = new RestTemplate();



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



    public String createPayout(String writerId, double amount) throws Exception {

        String fundAccountId = "fa_Q3165HIoOZ21IV";

        JSONObject payoutRequest = new JSONObject();
        payoutRequest.put("fund_account_id", fundAccountId);
        payoutRequest.put("amount", amount * 100);
        payoutRequest.put("currency", "INR");
        payoutRequest.put("purpose", "Payout for Writer ID: " + writerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + getAuthorizationHeader());

        HttpEntity<String> entity = new HttpEntity<>(payoutRequest.toString(), headers);


        String razorpayPayoutUrl = "https://api.razorpay.com/v1/payouts";


        ResponseEntity<String> response = restTemplate.exchange(razorpayPayoutUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {

            JSONObject payoutResponse = new JSONObject(response.getBody());
            String payoutId = payoutResponse.getString("id");
            return payoutId;
        } else {

            throw new Exception("Error creating payout: " + response.getBody());
        }
    }

    private String getAuthorizationHeader() {

        String auth = keyId + ":" + keySecret;
        return new String(java.util.Base64.getEncoder().encode(auth.getBytes()));
    }

}
