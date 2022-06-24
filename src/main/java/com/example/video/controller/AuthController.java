package com.example.video.controller;


import com.example.video.entities.response.BaseResponse;
import com.example.video.entities.user.LoginRequest;
import com.example.video.entities.user.User;
import com.example.video.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Transactional
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("v1/auth/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
        try {
            if(request.getUsername() == null || request.getPassword() == null) {
                throw new Exception("required_fields");
            }
            return ResponseEntity.ok(new BaseResponse(userService.signin(request), "login_succecss"));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }

    @PostMapping("admin/v1/auth/signin")
    public ResponseEntity<?> adminSignin(@Valid @RequestBody final User request) {
        try {
            if(request.getEmail() == null || request.getPassword() == null) {
                throw new Exception("required_fields");
            }
            return ResponseEntity.ok(new BaseResponse(userService.adminSignin(request),"login_succecss"));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }


    @PostMapping("v1/auth/signup")
    public ResponseEntity<?> userSignup(@Valid @RequestBody final User request) {
        try {
            if(StringUtils.isEmpty(request.getPassword())
                    || (StringUtils.isEmpty(request.getEmail()) && StringUtils.isEmpty(request.getPhone()))
                    || StringUtils.isEmpty(request.getName())) {
                throw new Exception("required_fields");
            }
            return ResponseEntity.ok(new BaseResponse(userService.userSignup(request),"register_success"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage()));
        }
    }

}
