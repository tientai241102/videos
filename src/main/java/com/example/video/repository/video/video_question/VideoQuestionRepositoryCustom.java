package com.example.video.repository.video.video_question;

import com.example.video.entities.video.VideoQuestion;

import java.util.List;

public interface VideoQuestionRepositoryCustom{
    List<VideoQuestion> getVideoQuestions(int page, String name, int videoId);
}
