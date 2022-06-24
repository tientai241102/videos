package com.example.video.service.video.feedback;

import com.example.video.entities.video.Feedback;

import java.util.List;

public interface FeedBackService {
    List<Feedback> getFeedbacks(int page,int videoId) throws Exception;

    Feedback createFeedBack(Feedback feedback) throws Exception;

    Feedback updateFeedBack(Feedback feedback) throws Exception;

    Feedback deleteFeedBack(Feedback feedback) throws Exception;
}
