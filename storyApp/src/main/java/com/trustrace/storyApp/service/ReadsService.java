package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.ReadsDAO;
import com.trustrace.storyApp.model.Reads;
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

    public long getTotalReads(String storyId) {
        return readsDAO.countReadsByStoryId(storyId);
    }

    public void trackStoryRead(String userId, String authorId, String storyId, boolean isPaid) {
        LocalDate today = LocalDate.now();

        Reads read = new Reads(null, userId, authorId, storyId, isPaid, today.getMonthValue(), today.getYear(), new Date());

        readsDAO.saveRead(read);
        readsDAO.updateStoryReadCount(storyId, userId);
    }

    public Map<String, Long> getMonthlyReadsByAuthor(String authorId, int year, int month) {
        return readsDAO.getMonthlyReadsByAuthor(authorId, year, month);
    }

    public List<String> getAllAuthors() {
        return readsDAO.getAllAuthors();
    }

    public long getPaidReadsByAuthor(String authorId, int month, int year) {
        return readsDAO.getPaidReadsByAuthor(authorId, month, year);
    }

    public long getUnpaidReadsByAuthor(String authorId, int month, int year) {
        return readsDAO.getUnpaidReadsByAuthor(authorId, month, year);
    }

}
