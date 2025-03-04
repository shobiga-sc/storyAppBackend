package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.ReadsDAO;
import com.trustrace.storyApp.model.Reads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReadsService {

    @Autowired
    private ReadsDAO readsDAO;

    private static final Logger logger = LoggerFactory.getLogger(ReadsService.class);

    public long getTotalReads(String storyId) {
        try {
            return readsDAO.countReadsByStoryId(storyId);
        } catch (Exception e) {
            logger.error("Error retrieving total reads for storyId: {}", storyId, e);
            throw new RuntimeException("Failed to get total reads");
        }
    }

    public void trackStoryRead(String userId, String authorId, String storyId, boolean isPaid) {
        try {
            LocalDate today = LocalDate.now();
            Reads read = new Reads(null, userId, authorId, storyId, isPaid, today.getMonthValue(), today.getYear(), new Date());
            readsDAO.saveRead(read);
            readsDAO.updateStoryReadCount(storyId, userId);
        } catch (Exception e) {
            logger.error("Error tracking story read for userId: {}, storyId: {}", userId, storyId, e);
            throw new RuntimeException("Failed to track story read");
        }
    }

    public Map<String, Long> getMonthlyReadsByAuthor(String authorId, int year, int month) {
        try {
            return readsDAO.getMonthlyReadsByAuthor(authorId, year, month);
        } catch (Exception e) {
            logger.error("Error retrieving monthly reads for authorId: {} in {}/{}", authorId, month, year, e);
            throw new RuntimeException("Failed to get monthly reads");
        }
    }

    public List<String> getAllAuthors() {
        try {
            return readsDAO.getAllAuthors();
        } catch (Exception e) {
            logger.error("Error retrieving all authors", e);
            throw new RuntimeException("Failed to get authors list");
        }
    }

    public long getPaidReadsByAuthor(String authorId, int month, int year) {
        try {
            return readsDAO.getPaidReadsByAuthor(authorId, month, year);
        } catch (Exception e) {
            logger.error("Error retrieving paid reads for authorId: {} in {}/{}", authorId, month, year, e);
            throw new RuntimeException("Failed to get paid reads");
        }
    }

    public long getUnpaidReadsByAuthor(String authorId, int month, int year) {
        try {
            return readsDAO.getUnpaidReadsByAuthor(authorId, month, year);
        } catch (Exception e) {
            logger.error("Error retrieving unpaid reads for authorId: {} in {}/{}", authorId, month, year, e);
            throw new RuntimeException("Failed to get unpaid reads");
        }
    }
}
