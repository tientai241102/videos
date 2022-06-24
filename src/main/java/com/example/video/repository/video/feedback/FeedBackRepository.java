package com.example.video.repository.video.feedback;

import com.example.video.entities.video.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<Feedback,Integer>,FeedBackRepositoryCustom {
}
