package com.example.video.controller;

import com.example.video.entities.request.ChangePassRequest;
import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;
import com.example.video.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Transactional
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("v1/user/get-user")
    public ResponseEntity<?> getUserForAdmin(@RequestParam int page,
                                             @RequestParam(required = false) UserRole role,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false)@DateTimeFormat(pattern="yyyy/MM/dd") Date timeStart,
                                             @RequestParam(required = false)@DateTimeFormat(pattern="yyyy/MM/dd") Date timeEnd,
                                             @RequestParam(required = false) Boolean deleted) {
        try {
            List<User> data = userService.getUsersForAdmin(page, role, name, timeStart, timeEnd, deleted);
            long totalRecord = userService.countUsersForAdmin(role, name, timeStart, timeEnd, deleted);
            return ResponseEntity.ok(new BaseResponse(data, totalRecord));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }


    @PostMapping("v1/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody final ChangePassRequest request) {
        try {
            if (request.getNewPass() == null || request.getOldPass() == null) {
                throw new Exception("required_fields");
            }
            userService.changePassword(request);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }

    @PostMapping("v1/user/check-password")
    public ResponseEntity<?> checkPassword(@RequestBody final User request) {
        try {
            if (request.getPassword() == null) {
                throw new Exception("required_fields");
            }
            userService.checkPassword(request);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }


    @GetMapping("v1/user/detail")
    public ResponseEntity<?> getProfile(@RequestParam int userId) {
        try {
            return ResponseEntity.ok().body(new BaseResponse(userService.getProfile(userId)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }


    @PostMapping("v1/user/update")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        try {
            return ResponseEntity.ok().body(new BaseResponse(userService.updateProfile(user)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }


    @PostMapping("admin/v1/user/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody final User request) {
        try {
            userService.deleteUser(request);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
}
