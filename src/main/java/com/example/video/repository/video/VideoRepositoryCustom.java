package com.example.video.repository.video;

import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.constant.VideoCategory;

import java.util.List;

public interface VideoRepositoryCustom {
    List<Video> getVideos(int page, String name, Integer ownerId, VideoFilterType type, VideoCategory category);
    long countVideos(String name, Integer ownerId, VideoFilterType type, VideoCategory category);
}
