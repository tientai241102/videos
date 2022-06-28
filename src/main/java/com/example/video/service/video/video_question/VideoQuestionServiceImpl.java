package com.example.video.service.video.video_question;

import com.example.video.entities.video.VideoQuestion;
import com.example.video.repository.video.video_question.VideoQuestionRepository;
import com.example.video.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoQuestionServiceImpl extends BaseService implements VideoQuestionService {
    @Autowired
    private VideoQuestionRepository videoQuestionRepository;

    @Override
    public List<VideoQuestion> getVideoQuestions(int page, int videoId) throws Exception {
        return videoQuestionRepository.getVideoQuestions(page, null, videoId);
    }

    @Override
    public VideoQuestion createNewVideoQuestion(VideoQuestion videoQuestion) throws Exception {
        if (StringUtils.isBlank(videoQuestion.getQuestionData()) || videoQuestion.getDuration() < 0
                || videoQuestion.getType() != null || videoQuestion.getVideoId() <= 0) {
            throw new Exception("Ban phai nhap du cac truong yeu cau.");

        }
        if (StringUtils.isBlank(videoQuestion.getAnswer())){
            videoQuestion.setAnswer("answer");
        }
        return videoQuestionRepository.save(videoQuestion);
    }

    @Override
    public VideoQuestion updateVideoQuestion(VideoQuestion videoQuestion) throws Exception {
        VideoQuestion question = videoQuestionRepository.findById(videoQuestion.getId()).orElseThrow(() -> new Exception("not_found"));
        if (StringUtils.isNotBlank(videoQuestion.getQuestionData())) {
            question.setQuestionData(videoQuestion.getQuestionData());
        }
        if (StringUtils.isNotBlank(videoQuestion.getQuestionContent())) {
            question.setQuestionContent(videoQuestion.getQuestionContent());
        }
        if (StringUtils.isNotBlank(videoQuestion.getAnswer())) {
            question.setAnswer(videoQuestion.getAnswer());
        }
        if (videoQuestion.getType() != null) {
            question.setType(videoQuestion.getType());
        }
        if (videoQuestion.getDuration() >= 0) {
            question.setDuration(videoQuestion.getDuration());
        }
        return videoQuestionRepository.save(videoQuestion);
    }

    @Override
    public void deleteVideoQuestion(VideoQuestion videoQuestion) throws Exception {
        VideoQuestion question = videoQuestionRepository.findById(videoQuestion.getId()).orElseThrow(() -> new Exception("not_found"));
        question.setDeleted(true);
        videoQuestionRepository.save(videoQuestion);
    }
}
