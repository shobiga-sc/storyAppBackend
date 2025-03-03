package com.trustrace.storyApp.repository;


import com.trustrace.storyApp.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
}
