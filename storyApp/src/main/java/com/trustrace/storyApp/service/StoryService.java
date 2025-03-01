package com.trustrace.storyApp.service;

import com.trustrace.storyApp.dao.StoryDao;
import com.trustrace.storyApp.model.Story;
import com.trustrace.storyApp.model.Follow;
import com.trustrace.storyApp.model.StoryStatus;
import com.trustrace.storyApp.repository.FollowRepository;
import com.trustrace.storyApp.repository.StoryRepository;
import com.trustrace.storyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private StoryDao storyDAO;


    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Story createStory(Story story) {

        Story savedStory = storyRepository.save(story);


        List<String> followers = followRepository.findByAuthorId(savedStory.getAuthorId())
                .stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());



        for (String followerId : followers) {
            String followerEmail = userRepository.findById(followerId).get().getEmail();


            Story story_data = storyRepository.findById(savedStory.getId()).orElse(null);

            if (story_data != null) {
                String authorName = story_data.getAuthorName();

                String subject = " New Story by " + authorName + "! Don't Miss It!";
                String body = "Hi there,\n\n"
                        + "Exciting news! Your favorite author, *" + authorName + "*, has just published a new story: *" + story_data.getTitle() + "*.\n\n"
                        + "Here's a short preview:\n\n"
                        + "\"" + story_data.getSummary() + "\"\n\n"
                        + "Happy reading!\n\n"
                        + "— The StoryApp Team";

                emailService.sendEmail(followerEmail, subject, body);
            }
        }


        return savedStory;
    }




    public List<Story> getStoriesByUserIdAndStatus(String userId, StoryStatus status) {
        return storyDAO.getStoriesByUserIdAndStatus(userId, status);
    }

    public Optional<Story> getStoryById(String storyId){
        return storyRepository.findById(storyId);
    }

    public List<Story> getAllPublishedStories(){
        return storyRepository.findAllByStatus(StoryStatus.PUBLISHED);
    }
}
