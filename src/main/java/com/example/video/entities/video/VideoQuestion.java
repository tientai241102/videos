package com.example.video.entities.video;

import com.example.video.entities.DateAudit;
import com.example.video.entities.video.constant.VideoQuestionType;

import javax.persistence.*;

@Entity
@Table(name = "video_questions")
public class VideoQuestion extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int videoId;
    private int duration;
    private VideoQuestionType type;
    private String questionContent;
    private String questionData;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public VideoQuestionType getType() {
        return type;
    }

    public void setType(VideoQuestionType type) {
        this.type = type;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionData() {
        return questionData;
    }

    public void setQuestionData(String questionData) {
        this.questionData = questionData;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
