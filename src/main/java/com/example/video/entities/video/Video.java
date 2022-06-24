package com.example.video.entities.video;

import com.example.video.entities.DateAudit;
import com.example.video.entities.UploadFile;
import com.example.video.entities.user.User;
import com.example.video.entities.video.constant.VideoCategory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "videos")
public class Video extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ownerId;
    private VideoCategory category;
    private String title;
    private String content;
    private int time;
    private float rateAVG;
    private int numberRate;
    private int numberView;
    private Integer avatarId;
    private Integer fileId;
    @Transient
    private List<VideoQuestion> videoQuestions;
    @Transient
    private List<Feedback> feedbacks;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatarId", referencedColumnName = "id", insertable = false, updatable = false)
    private UploadFile avatar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId", referencedColumnName = "id", insertable = false, updatable = false)
    private UploadFile video;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getRateAVG() {
        return rateAVG;
    }

    public void setRateAVG(float rateAVG) {
        this.rateAVG = rateAVG;
    }

    public VideoCategory getCategory() {
        return category;
    }

    public void setCategory(VideoCategory category) {
        this.category = category;
    }

    public int getNumberView() {
        return numberView;
    }

    public void setNumberView(int numberView) {
        this.numberView = numberView;
    }

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public int getNumberRate() {
        return numberRate;
    }

    public void setNumberRate(int numberRate) {
        this.numberRate = numberRate;
    }

    public List<VideoQuestion> getVideoQuestions() {
        return videoQuestions;
    }

    public void setVideoQuestions(List<VideoQuestion> videoQuestions) {
        this.videoQuestions = videoQuestions;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public UploadFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadFile avatar) {
        this.avatar = avatar;
    }

    public UploadFile getVideo() {
        return video;
    }

    public void setVideo(UploadFile video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
