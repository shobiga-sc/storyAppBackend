package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.StoryReadTrackingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StoryAnalyticsService {

    @Autowired
    private StoryReadTrackingDAO trackingDAO;



    public Map<String, Long> getMonthlyViewsByAuthor(String authorId, int year, int month) {
        return trackingDAO.getMonthlyViewsByAuthor(authorId, year, month);
    }



}
