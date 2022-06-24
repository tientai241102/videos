package com.example.video.repository.user;

import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;


import java.util.Date;
import java.util.List;
import java.util.Optional;

interface UserRepositoryCustom {
    List<User> searchUsers(String name, long ownerId, int page);
    List<User> getUsersForAdmin(int page, UserRole role, String name, Date startTime, Date endTime, Boolean deleted);

    Optional<User> findUserByName(int userId, String name);

    long countUsersForAdmin(UserRole role, String name, Date startTime, Date endTime, Boolean deleted);


}
