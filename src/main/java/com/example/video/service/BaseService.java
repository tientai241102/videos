package com.example.video.service;


import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;
import com.example.video.repository.user.UserRepository;
import com.example.video.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HttpServletRequest httpServletRequest;

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public User getUser() throws Exception {
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            user = userRepository.findUserById(userPrincipal.getId());
        }
        if(user == null) {
            throw new Exception("login_required");
        }
        return user;
    }

    public User getUser(UserRole... roles) throws Exception {
        User user = getUser();
        if(user.getRole() == UserRole.ROLE_ADMIN) {
            return user;
        }
        for(UserRole role : roles) {
            if(user.getRole() == role) {
                return user;
            }
        }
        throw new Exception("invalid_permission");
    }

    protected String getURLBase() throws MalformedURLException {
        URL requestURL = new URL(httpServletRequest.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;

    }


}
