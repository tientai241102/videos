package com.example.video.service.user;



import com.example.video.entities.constant.UserFilterType;
import com.example.video.entities.request.ChangePassRequest;
import com.example.video.entities.request.ForgotPassRequest;
import com.example.video.entities.user.LoginRequest;
import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;

import java.util.Date;
import java.util.List;

public interface UserService {

    User signin(LoginRequest request) throws Exception;

    User adminSignin(User request) throws Exception;

    User userSignup(User request) throws Exception;

    List<User> getUsersForAdmin(int page, UserRole role, String name, Date startTime, Date endTime, Boolean deleted, UserFilterType type) throws Exception;

    long countUsersForAdmin(UserRole role, String name , Date startTime, Date endTime, Boolean deleted) throws Exception;

    void changePassword(ChangePassRequest request) throws Exception;

    void checkPassword(User request) throws Exception;

    User adminAddUser(User request) throws Exception;

    User updateProfile(User request) throws Exception;

    User getProfile(int userId) throws Exception;

    void editUser(User request) throws Exception;

    void deleteUser(User request) throws Exception;



}
