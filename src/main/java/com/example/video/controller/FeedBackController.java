package com.example.video.controller;

import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.video.Feedback;
import com.example.video.entities.video.VideoQuestion;
import com.example.video.service.video.feedback.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Transactional
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;

    @GetMapping("v1/feedback/get-feedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam int page,
                                       @RequestParam Integer videoId) {
        try {
            return ResponseEntity.ok(new BaseResponse(feedBackService.getFeedbacks(page,videoId)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/feedback/create")
    public ResponseEntity<?> createFeedBack(@RequestBody Feedback feedback) {
        try {
            return ResponseEntity.ok(new BaseResponse(feedBackService.createFeedBack(feedback)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/feedback/update")
    public ResponseEntity<?> updateFeedBack(@RequestBody Feedback feedback)  {
        try {
            return ResponseEntity.ok(new BaseResponse(feedBackService.updateFeedBack(feedback)));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("v1/feedback/delete")
    public ResponseEntity<?> deleteFeedBack(@RequestBody Feedback feedback)  {
        try {
            feedBackService.deleteFeedBack(feedback);
            return ResponseEntity.ok(new BaseResponse());
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }
}
