package com.example.video.entities.user;

import com.example.video.entities.DateAudit;
import com.example.video.entities.UploadFile;
import com.example.video.entities.user.constant.UserGender;
import com.example.video.entities.user.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;

    private String email;

    private String password;

    private String name;

    private String address;

    private Integer avatarId;

    private UserRole role;
    private int totalFollower;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private String job;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatarId", referencedColumnName = "id", insertable = false, updatable = false)
    private UploadFile avatar;

    private UserGender gender;

    @Basic
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Transient
    private String accessToken;

    @Transient
    private Boolean follow;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UploadFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadFile avatar) {
        this.avatar = avatar;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getTotalFollower() {
        return totalFollower;
    }

    public void setTotalFollower(int totalFollower) {
        this.totalFollower = totalFollower;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow(Boolean follow) {
        this.follow = follow;
    }
}