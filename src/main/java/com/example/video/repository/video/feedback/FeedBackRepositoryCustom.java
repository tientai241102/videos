package com.example.video.repository.video.feedback;

import com.example.video.entities.video.Feedback;

import java.util.List;

public interface FeedBackRepositoryCustom {
    List<Feedback> getFeedbacks(int page,String name,int videoId);
}
