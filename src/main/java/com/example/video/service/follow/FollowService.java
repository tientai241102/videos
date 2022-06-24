package com.example.video.service.follow;

import com.example.video.entities.Follow;

import java.util.List;

public interface FollowService {
    List<Follow> getFollows(int page, String name) throws Exception;

    Follow followUser(Follow follow) throws Exception;

    void unFollowUser(Follow follow) throws Exception;
}
