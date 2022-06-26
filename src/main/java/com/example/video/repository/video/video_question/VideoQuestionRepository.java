package com.example.video.repository.video.video_question;

import com.example.video.entities.video.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoQuestionRepository extends JpaRepository<VideoQuestion,Integer>,VideoQuestionRepositoryCustom {
    void deleteAllByVideoIdAndDeleted(int videoId, boolean deleted);
}
