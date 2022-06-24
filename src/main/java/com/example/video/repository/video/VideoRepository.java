package com.example.video.repository.video;

import com.example.video.entities.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Integer>,VideoRepositoryCustom {
}
