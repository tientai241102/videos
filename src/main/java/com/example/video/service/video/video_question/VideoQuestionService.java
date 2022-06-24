package com.example.video.service.video.video_question;

import com.example.video.entities.video.VideoQuestion;

import java.util.List;

public interface VideoQuestionService {
    List<VideoQuestion> getVideoQuestions(int page, int videoId) throws Exception;

    VideoQuestion createNewVideoQuestion(VideoQuestion videoQuestion) throws Exception;

    VideoQuestion updateVideoQuestion(VideoQuestion videoQuestion) throws Exception;

    void deleteVideoQuestion(VideoQuestion videoQuestion) throws Exception;

}
