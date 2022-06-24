package com.example.video.repository.video;

import com.example.video.entities.video.Video;

import java.util.List;

public interface VideoRepositoryCustom {
    List<Video> getVideos(int page, String name, Integer ownerId);
    long countVideos(String name, Integer ownerId);
}
