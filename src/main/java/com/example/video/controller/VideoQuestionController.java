package com.example.video.controller;

import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.video.VideoQuestion;
import com.example.video.service.video.VideoService;
import com.example.video.service.video.video_question.VideoQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Transactional
public class VideoQuestionController {
    @Autowired
    private VideoQuestionService videoQuestionService;

    @GetMapping("v1/video-question/get-videos")
    public ResponseEntity<?> getVideoQuestions(@RequestParam int page,
                                       @RequestParam(required = false) Integer videoId) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoQuestionService.getVideoQuestions(page,videoId)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video-question/create")
    public ResponseEntity<?> createNewVideoQuestion(@RequestBody VideoQuestion videoQuestion) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoQuestionService.createNewVideoQuestion(videoQuestion)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video-question/update")
    public ResponseEntity<?> updateVideoQuestion(@RequestBody VideoQuestion videoQuestion) {
        try {
            return ResponseEntity.ok(new BaseResponse(videoQuestionService.updateVideoQuestion(videoQuestion)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/video-question/delete")
    public ResponseEntity<?> deleteVideoQuestion(@RequestBody VideoQuestion videoQuestion) {
        try {
            videoQuestionService.deleteVideoQuestion(videoQuestion);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
}
