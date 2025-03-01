package com.trustrace.storyApp.service;

import com.trustrace.storyApp.repository.StoryReadTrackerRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.StoryReadTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Optional;

@Service
public class StoryReadService {

    @Autowired
    private StoryReadTrackerRepository storyReadTrackerRepository;

    @Autowired
    private StoryRepository storyRepository;

    public long getTotalReads(String storyId) {
        return storyReadTrackerRepository.countByStoryId(storyId);
    }

    public int trackAndFetchReadCount(String userId, String storyId) {
        Story story = storyRepository.findById(storyId).orElse(null);
        if (story == null) return -1; // Story not found

        Optional<StoryReadTracker> trackerOpt = storyReadTrackerRepository.findByUserIdAndStoryId(userId, storyId);

        if (trackerOpt.isEmpty()) {
            // First-time read, create tracker entry
            StoryReadTracker tracker = new StoryReadTracker();
            tracker.setUserId(userId);
            tracker.setStoryId(storyId);
            tracker.setStartedAt(LocalDateTime.now());
            tracker.setCounted(false);
            storyReadTrackerRepository.save(tracker);
        } else {
            // Check if 3+ minutes have passed
            StoryReadTracker tracker = trackerOpt.get();
            LocalDateTime now = LocalDateTime.now();

            if (!tracker.isCounted() && Duration.between(tracker.getStartedAt(), now).toMinutes() >= 3) {
                tracker.setCounted(true);
                storyReadTrackerRepository.save(tracker);

                // Increment view count
                story.setViewCount(story.getViewCount() + 1);
                storyRepository.save(story);
            }
        }

        return story.getViewCount();
    }
}
