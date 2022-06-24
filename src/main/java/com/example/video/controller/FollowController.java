package com.example.video.controller;

import com.example.video.entities.Follow;
import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.user.User;
import com.example.video.service.follow.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Transactional
public class FollowController {
    @Autowired
    private FollowService followService;

    @GetMapping("v1/follow/get-following")
    public ResponseEntity<?> getFollows(@RequestParam int page,
                                         @RequestParam(required = false) String name) {
        try {
            return ResponseEntity.ok(new BaseResponse(followService.getFollows(page,name)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/follow/follow")
    public ResponseEntity<?> BaseResponse(@RequestBody Follow follow) {
        try {
            return ResponseEntity.ok(new BaseResponse(followService.followUser(follow)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/follow/unfollow")
    public ResponseEntity<?> unFollowUser(@RequestBody Follow follow) {
        try {
            followService.unFollowUser(follow);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }

}
