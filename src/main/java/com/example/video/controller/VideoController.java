package com.example.video.controller;

import com.example.video.entities.constant.VideoFilterType;
import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.video.Video;
import com.example.video.entities.video.constant.VideoCategory;
import com.example.video.service.follow.FollowService;
import com.example.video.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Transactional
public class VideoController {
    @Autowired
    private VideoService  videoService;

    @GetMapping("v1/video/get-videos")
    public ResponseEntity<?> getVideos(@RequestParam int page,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer ownerId,
                                       @RequestParam(required = false) VideoCategory category,
                                       @RequestParam(required = false) VideoFilterType type) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoService.getVideos(page,name,ownerId,type,category),
                    videoService.countVideos(name,ownerId,type,category)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @GetMapping("v1/video/get-detail")
    public ResponseEntity<?> getDetailVideo(@RequestParam Integer videoId) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoService.getDetailVideo(videoId)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video/create")
    public ResponseEntity<?> createVideo(@RequestBody Video video) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoService.createVideo(video)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video/update")
    public ResponseEntity<?> updateVideo(@RequestBody Video video) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoService.updateVideo(video)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video/view")
    public ResponseEntity<?> viewVideo(@RequestBody Video video) {
        try {
            videoService.viewVideo(video);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video/delete")
    public ResponseEntity<?> deleteVideo(@RequestBody Video video) {
        try {
            videoService.deleteVideo(video);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
}
