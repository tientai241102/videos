package com.example.video.service.video;

import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.constant.VideoCategory;

import java.util.List;

public interface VideoService {
    List<Video> getVideos(int page, String name, Integer ownerId, VideoFilterType type, VideoCategory category) throws Exception;

    Long countVideos(String name, Integer ownerId, VideoFilterType type, VideoCategory category) throws Exception;

    Video getDetailVideo(int videoId) throws Exception;

    Video createVideo(Video video) throws Exception;

    Video updateVideo(Video video) throws Exception;

    void viewVideo(Video video) throws Exception;

    void deleteVideo(Video video) throws Exception;
}
