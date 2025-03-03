package com.trustrace.storyApp.repository;

import com.trustrace.storyApp.model.Payment;
import com.trustrace.storyApp.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
}
