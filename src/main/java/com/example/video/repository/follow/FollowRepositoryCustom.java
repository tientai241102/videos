package com.example.video.repository.follow;

import com.example.video.entities.Follow;

import java.util.List;

public interface FollowRepositoryCustom {
    List<Follow> getFollowList(int page, String name,int ownerId);
}
