package com.example.video.service.video;

import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.user.User;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.VideoQuestion;
import com.example.video.repository.follow.FollowRepository;
import com.example.video.repository.video.VideoRepository;
import com.example.video.repository.video.feedback.FeedBackRepository;
import com.example.video.repository.video.video_question.VideoQuestionRepository;
import com.example.video.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl extends BaseService implements VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoQuestionRepository videoQuestionRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private FollowRepository followRepository;

    @Override
    public List<Video> getVideos(int page, String name, Integer ownerId, VideoFilterType type) throws Exception {
        List<Video> videos = videoRepository.getVideos(page, name, ownerId,type);
        return videos;
    }

    @Override
    public Long countVideos(String name, Integer ownerId) throws Exception {
        return videoRepository.countVideos( name, ownerId);
    }

    @Override
    public Video getDetailVideo(int videoId) throws Exception {
        User user = getUser();
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new Exception("not_found"));
        video.setVideoQuestions(videoQuestionRepository.getVideoQuestions(0, null, videoId));
        video.setFeedbacks(feedBackRepository.getFeedbacks(0, null, videoId));
        video.getUser().setFollow(followRepository.existsByOwnerIdAndPartnerId(user.getId(),video.getOwnerId()));
        return video;
    }

    @Override
    public Video createVideo(Video video) throws Exception {
        User user = getUser();
        if (StringUtils.isBlank(video.getTitle()) || StringUtils.isBlank(video.getContent())
                || video.getTime() < 0 || video.getFileId() == null || video.getCategory() == null) {
            throw new Exception("Ban phai nhap du cac truong yeu cau.");
        }
        video.setOwnerId(user.getId());
        video.setNumberRate(0);
        video.setRateAVG(0);
        video.setNumberView(0);
        video = videoRepository.save(video);
        if (video.getVideoQuestions() != null) {
            for (VideoQuestion videoQuestion : video.getVideoQuestions()) {
                if (StringUtils.isNotBlank(videoQuestion.getQuestionData())
                        || videoQuestion.getType() != null
                        || videoQuestion.getDuration() >= 0) {
                    videoQuestion.setVideoId(video.getId());
                    videoQuestionRepository.save(videoQuestion);
                }
            }
        }
        return video;
    }

    @Override
    public Video updateVideo(Video video) throws Exception {
        User user = getUser();
        Video newVideo = videoRepository.findById(video.getId()).orElseThrow(() -> new Exception("not_found"));
        if (user.getId() == video.getOwnerId()) {
            if (StringUtils.isNotBlank(video.getTitle())) {
                newVideo.setTitle(video.getTitle());
            }
            if (StringUtils.isNotBlank(video.getContent())) {
                newVideo.setContent(video.getContent());
            }
            if (video.getTime() > 0) {
                newVideo.setTime(video.getTime());
            }
            if (video.getAvatarId() != null) {
                newVideo.setAvatarId(video.getAvatarId());
            }
            if (video.getFileId() > 0) {
                newVideo.setFileId(video.getFileId());
            }
        }
        return videoRepository.save(newVideo);
    }

    @Override
    public void viewVideo(Video video) throws Exception {
        User user = getUser();
        Video newVideo = videoRepository.findById(video.getId()).orElseThrow(() -> new Exception("not_found"));
        newVideo.setNumberView(newVideo.getNumberView() + 1);
        videoRepository.save(newVideo);
    }

    @Override
    public void deleteVideo(Video video) throws Exception {
        User user = getUser();
        Video newVideo = videoRepository.findById(video.getId()).orElseThrow(() -> new Exception("not_found"));
        if (user.getId() != video.getOwnerId()) {
         throw new  Exception("Bạn không có quyền chỉnh sửa.");
        }
        newVideo.setDeleted(true);
        videoRepository.save(newVideo);
    }
}
