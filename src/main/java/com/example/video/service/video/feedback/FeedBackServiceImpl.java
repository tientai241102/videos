package com.example.video.service.video.feedback;

import com.example.video.entities.user.User;
import com.example.video.entities.video.Feedback;
import com.example.video.entities.video.Video;
import com.example.video.repository.video.VideoRepository;
import com.example.video.repository.video.feedback.FeedBackRepository;
import com.example.video.service.BaseService;
import com.example.video.service.video.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackServiceImpl extends BaseService implements FeedBackService {
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Override
    public List<Feedback> getFeedbacks(int page, int videoId) throws Exception {
        return feedBackRepository.getFeedbacks(page, null, videoId);
    }

    @Override
    public Feedback createFeedBack(Feedback feedback) throws Exception {
        User user = getUser();
        if (feedback.getRate() < 0 || StringUtils.isNotBlank(feedback.getComment())
                || !videoRepository.existsById(feedback.getVideoId())) {
            throw new Exception("Ban phai nhap du cac truong yeu cau.");
        }
        feedback.setOwnerId(user.getId());
        feedback = feedBackRepository.save(feedback);
        Video video = videoRepository.findById(feedback.getVideoId()).get();
        video.setNumberRate(video.getNumberRate() + 1);
        video.setRateAVG((feedback.getRate() + feedback.getRate()) / (video.getNumberRate() + 1));
        videoRepository.save(video);
        return null;
    }

    @Override
    public Feedback updateFeedBack(Feedback feedback) throws Exception {
        User user = getUser();
        Feedback updateFeedback = feedBackRepository.findById(feedback.getId()).orElseThrow(() -> new Exception("not_found"));
        if (user.getId() == updateFeedback.getOwnerId()) {
            if (StringUtils.isNotBlank(feedback.getComment())) {
                updateFeedback.setComment(feedback.getComment());
            }
        }

        return feedBackRepository.save(feedback);
    }

    @Override
    public Feedback deleteFeedBack(Feedback feedback) throws Exception {
        Feedback updateFeedback = feedBackRepository.findById(feedback.getId()).orElseThrow(() -> new Exception("not_found"));
        updateFeedback.setDeleted(true);
        return feedBackRepository.save(feedback);
    }
}
