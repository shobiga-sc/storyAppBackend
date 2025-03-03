package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.ReadsDAO;
import com.trustrace.storyApp.model.Payment;
import com.trustrace.storyApp.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminStatsService {

    private final ReadsDAO readsDAO;
    private final PaymentRepository paymentRepository;
    private final ReadsService readsService;

    public AdminStatsService(ReadsDAO readsDAO, PaymentRepository paymentRepository, ReadsService readsService) {
        this.readsDAO = readsDAO;
        this.paymentRepository = paymentRepository;
        this.readsService = readsService;
    }

    public long getTotalReads(int month, int year) {
        return readsDAO.countByMonthAndYear(month, year);
    }

    public long getPaidReads(int month, int year) {
        return readsDAO.countByMonthAndYearAndIsPaid(month, year, true);
    }

    public long getUnpaidReads(int month, int year) {
        return readsDAO.countByMonthAndYearAndIsPaid(month, year, false);
    }

    public double getTotalRevenue(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<Payment> payments = paymentRepository.findByPaymentDateBetween(
                yearMonth.atDay(1).atStartOfDay(),
                yearMonth.atEndOfMonth().atTime(23, 59, 59)
        );
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }

    public List<Map<String, Object>> calculateWriterEarnings(int month, int year) {
        double totalRevenue = getTotalRevenue(month, year);

        double writerRevenuePool = totalRevenue * 0.35 ;

        List<String> authors = readsService.getAllAuthors();
        double totalPopularityScore = 0.0;
        Map<String, Double> authorScores = new HashMap<>();

        for (String authorId : authors) {
            long paidReads = readsService.getPaidReadsByAuthor(authorId, month, year);
            long unpaidReads = readsService.getUnpaidReadsByAuthor(authorId, month, year);
            double popularityScore = (paidReads * 1.0) + (unpaidReads * 0.2);
            authorScores.put(authorId, popularityScore);
            totalPopularityScore += popularityScore;
        }

        List<Map<String, Object>> earningsList = new ArrayList<>();
        for (String authorId : authors) {
            double writerScore = authorScores.getOrDefault(authorId, 0.0);
            double writerEarnings = (writerScore / totalPopularityScore) * writerRevenuePool;

            Map<String, Object> earningsData = new HashMap<>();
            earningsData.put("authorId", authorId);
            earningsData.put("paidReads", readsService.getPaidReadsByAuthor(authorId, month, year));
            earningsData.put("unpaidReads", readsService.getUnpaidReadsByAuthor(authorId, month, year));
            earningsData.put("popularityScore", writerScore);
            earningsData.put("earnings", writerEarnings);

            earningsList.add(earningsData);
        }
        return earningsList;
    }

    public Map<String, Object> calculateOneWriterEarnings(int month, int year, String userId) {
        double totalRevenue = getTotalRevenue(month, year);
        System.out.println("tot" + totalRevenue);
        double writerRevenuePool = totalRevenue * 0.35 ;
        System.out.println("writer pool" + writerRevenuePool);
        List<String> authors = readsService.getAllAuthors();
        double totalPopularityScore = 0.0;
        Map<String, Double> authorScores = new HashMap<>();

        for (String authorId : authors) {
            long paidReads = readsService.getPaidReadsByAuthor(authorId, month, year);
            long unpaidReads = readsService.getUnpaidReadsByAuthor(authorId, month, year);
            double popularityScore = (paidReads * 1.0) + (unpaidReads * 0.2);
            authorScores.put(authorId, popularityScore);
            totalPopularityScore += popularityScore;
        }



            double writerScore = authorScores.getOrDefault(userId, 0.0);
            double writerEarnings = (writerScore / totalPopularityScore) * writerRevenuePool;

            Map<String, Object> earningsData = new HashMap<>();
            earningsData.put("authorId", userId);
            earningsData.put("paidReads", readsService.getPaidReadsByAuthor(userId, month, year));
            earningsData.put("unpaidReads", readsService.getUnpaidReadsByAuthor(userId, month, year));
            earningsData.put("popularityScore", writerScore);
            earningsData.put("earnings", writerEarnings);


        return earningsData;
    }



}
