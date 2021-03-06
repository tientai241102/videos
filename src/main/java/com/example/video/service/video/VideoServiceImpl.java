package com.example.video.service.video;

import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.user.User;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.VideoQuestion;
import com.example.video.entities.video.constant.VideoCategory;
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
    public List<Video> getVideos(int page, String name, Integer ownerId, VideoFilterType type, VideoCategory category) throws Exception {
        List<Video> videos = videoRepository.getVideos(page, name, ownerId,type,category);
        return videos;
    }

    @Override
    public Long countVideos(String name, Integer ownerId, VideoFilterType type, VideoCategory category) throws Exception {
        return videoRepository.countVideos( name, ownerId,type,category);
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
                    if (StringUtils.isBlank(videoQuestion.getAnswer())){
                        videoQuestion.setAnswer("answer");
                    }
                    if (videoQuestionRepository.existsByVideoIdAndDuration(video.getId(),videoQuestion.getDuration())){
                        throw new Exception("Th???i gian kh??nd ???????c tr??ng nhau.");
                    }
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
        if (user.getId() == newVideo.getOwnerId()) {
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
            if (video.getFileId() != null ) {
                newVideo.setFileId(video.getFileId());
            }
            if (video.getVideoQuestions() != null) {
                videoQuestionRepository.deleteAllByVideoIdAndDeleted(newVideo.getId(),false);
                for (VideoQuestion videoQuestion : video.getVideoQuestions()) {
                    if (StringUtils.isNotBlank(videoQuestion.getQuestionData())
                            || videoQuestion.getType() != null
                            || videoQuestion.getDuration() >= 0) {
                        videoQuestion.setVideoId(newVideo.getId());
                        if (videoQuestionRepository.existsByVideoIdAndDuration(newVideo.getId(),videoQuestion.getDuration())){
                            throw new Exception("Th???i gian kh??nd ???????c tr??ng nhau.");
                        }
                        if (StringUtils.isBlank(videoQuestion.getAnswer())){
                            videoQuestion.setAnswer("answer");
                        }
                        videoQuestionRepository.save(videoQuestion);
                    }
                }
            }
        }
        Video video1 = videoRepository.save(newVideo);
        video1.setVideoQuestions(videoQuestionRepository.getVideoQuestions(0, null, video1.getId()));
        video1.setFeedbacks(feedBackRepository.getFeedbacks(0, null, video1.getId()));
        return video1;
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
        if (user.getId() != newVideo.getOwnerId()) {
         throw new  Exception("B???n kh??ng c?? quy???n ch???nh s???a.");
        }
        newVideo.setDeleted(true);
        videoRepository.save(newVideo);
    }
}
