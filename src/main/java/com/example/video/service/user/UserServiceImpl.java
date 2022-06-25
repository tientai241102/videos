package com.example.video.service.user;

import com.example.video.entities.UploadFile;
import com.example.video.entities.constant.UserFilterType;
import com.example.video.entities.request.ChangePassRequest;
import com.example.video.entities.user.LoginRequest;
import com.example.video.entities.user.User;
import com.example.video.entities.user.constant.UserRole;
import com.example.video.repository.FileRepository;
import com.example.video.repository.follow.FollowRepository;
import com.example.video.security.CustomUserDetailsService;
import com.example.video.security.JwtTokenProvider;
import com.example.video.security.UserPrincipal;
import com.example.video.service.BaseService;
import com.example.video.util.Util;
import com.sun.java.accessibility.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private FileRepository mediaRepository;
    @Autowired
    private FollowRepository followRepository;

    @Override
    public User signin(LoginRequest request) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            throw new Exception("login_fail");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user;
        if (Util.validateEmail(request.getUsername())) {
            user = userRepository.findByEmail(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("user_not_found_with_email", request.getUsername())));
        } else {
            user = userRepository.findByPhone(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException(String.format("user_not_found_with_email", request.getUsername())));
        }
        if (user.getDeleted()) {
            throw new Exception("Tài khoản đã bị xoá");
        }

        String jwt = tokenProvider.generateToken(authentication);
        user.setAccessToken(jwt);

        return user;
    }

    @Override
    public User adminSignin(User request) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new Exception("login_fail");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(String.format("user_not_found_with_email", request.getEmail())));
        if (user.getDeleted()) {
            throw new Exception("account_deleted");
        }
        if (user.getRole() != UserRole.ROLE_STAFF && user.getRole() != UserRole.ROLE_ADMIN) {
            throw new Exception("invalid_permission");
        }
        String jwt = tokenProvider.generateToken(authentication);
        user.setAccessToken(jwt);
        return user;
    }


    private void addToken(User user) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserById(user.getId());
        String jwt = tokenProvider.generateTokenByUser((UserPrincipal) userDetails);
        user.setAccessToken(jwt);
    }


    @Override
    public User userSignup(User request) throws Exception {
        if (!StringUtils.isEmpty(request.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new Exception("phone_exists");
            }
        }
        if (!StringUtils.isEmpty(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new Exception("email_exists");
            }
        }

        request.setRole(UserRole.ROLE_USER);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request = userRepository.save(request);

        UserDetails userDetails = customUserDetailsService.loadUserById(request.getId());
        String jwt = tokenProvider.generateTokenByUser((UserPrincipal) userDetails);
        request.setAccessToken(jwt);
        return request;
    }

    @Override
    public List<User> getUsersForAdmin(int page, UserRole role, String name, Date startTime, Date endTime, Boolean deleted, UserFilterType type) throws Exception {
       User myUser = getUser();
        List<User> users= userRepository.getUsersForAdmin(page, role, name, startTime, endTime, deleted,type);
        for (User user: users){
            user.setFollow(followRepository.existsByOwnerIdAndPartnerId(myUser.getId(),user.getId()));
        }
        return users;
    }

    @Override
    public long countUsersForAdmin(UserRole role, String name, Date startTime, Date endTime, Boolean deleted) throws Exception {
        return userRepository.countUsersForAdmin(role, name, startTime, endTime, deleted);
    }

    @Override
    public User updateProfile(User request) throws Exception {
        User user = getUser();
        if (!StringUtils.isEmpty(request.getPhone()) && (StringUtils.isEmpty(user.getPhone()) || !request.getPhone().equals(user.getPhone()))) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new Exception("phone_exists");
            }
            user.setPhone(request.getPhone());
        }
        if (!StringUtils.isEmpty(request.getEmail()) && (StringUtils.isEmpty(user.getEmail()) || !request.getEmail().equals(user.getEmail()))) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new Exception("email_exists");
            }
            user.setEmail(request.getEmail());
        }
        if (!StringUtils.isEmpty(request.getName())) {
            user.setName(request.getName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (!StringUtils.isEmpty(request.getAddress())) {
            user.setAddress(request.getAddress());
        }
        if (!StringUtils.isEmpty(request.getDescription())) {
            user.setDescription(request.getDescription());
        }
        if (!StringUtils.isEmpty(request.getJob())) {
            user.setJob(request.getJob());
        }
        if (request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
        }
        user = userRepository.save(user);
        return user;
    }


    @Override
    public void changePassword(ChangePassRequest request) throws Exception {
        User user = getUser();
        if (passwordEncoder.matches(request.getOldPass(), user.getPassword())) {
            logger.info("change-pass by: " + user.getId());
            user.setPassword(passwordEncoder.encode(request.getNewPass()));
            userRepository.save(user);
        } else {
            throw new Exception("old_password_no_match");
        }
    }

    @Override
    public void checkPassword(User request) throws Exception {
        User user = getUser();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("old_password_no_match");
        }
    }
    @Override
    public User adminAddUser(User request) throws Exception {
//        getUser(UserRole.ROLE_STAFF);
        if (!StringUtils.isEmpty(request.getPhone())) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new Exception("phone_exists");
            }
        }
        if (!StringUtils.isEmpty(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new Exception("email_exists");
            }
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request = userRepository.save(request);
        return request;
    }

    @Override
    public User getProfile(int userId) throws Exception {

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("not_found"));
        return user;

    }
    @Override
    public void editUser(User request) throws Exception {
//        getUser(UserRole.ROLE_STAFF);
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new Exception("user_not_found"));
        if (!StringUtils.isEmpty(request.getPhone()) && (StringUtils.isEmpty(user.getPhone()) || !request.getPhone().equals(user.getPhone()))) {
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new Exception("SDT đã tồn tại ở một tài khoản khác");
            }
            user.setPhone(request.getPhone());
        }
        if (!StringUtils.isEmpty(request.getEmail()) && (StringUtils.isEmpty(user.getEmail()) || !request.getEmail().equals(user.getEmail()))) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new Exception("Email đã tồn tại ở một tài khoản khác");
            }
            user.setEmail(request.getEmail());
        }
        if (!StringUtils.isEmpty(request.getName())) {
            user.setName(request.getName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (!StringUtils.isEmpty(request.getAddress())) {
            user.setAddress(request.getAddress());
        }
        if (request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
        }
        if (request.getAvatarId() != null) {
            user.setAvatarId(request.getAvatarId());
            UploadFile uploadFile = mediaRepository.findById(request.getAvatarId()).orElseThrow(() -> new Exception("Không tìm thấy hình ảnh"));
            user.setAvatar(uploadFile);
        }
        userRepository.save(user);
    }


    @Override
    public void deleteUser(User request) throws Exception {
        User currentUser = getUser(UserRole.ROLE_STAFF);
        if (request.getId() == currentUser.getId()) {
            throw new Exception("Bạn không thể xoá tài khoản chính bạn");
        }
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new Exception("user_not_found"));
        user.setDeleted(true);
        userRepository.save(user);
    }


}
