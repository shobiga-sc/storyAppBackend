package com.trustrace.storyApp.repository;
import com.trustrace.storyApp.model.Payout;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface PayoutRepository extends MongoRepository<Payout, String> {
    List<Payout> findByMonthAndYear(int month, int year);
    Optional<Payout> findByWriterIdAndMonthAndYear(String writerId, int month, int year);
}
