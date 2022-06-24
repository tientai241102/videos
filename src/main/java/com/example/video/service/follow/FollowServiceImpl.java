package com.example.video.service.follow;

import com.example.video.entities.Follow;
import com.example.video.entities.user.User;
import com.example.video.repository.follow.FollowRepository;
import com.example.video.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FollowServiceImpl extends BaseService implements FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Override
    public List<Follow> getFollows(int page, String name) throws Exception {
       User user = getUser();
        return followRepository.getFollowList(page,name,user.getId());
    }

    @Override
    public Follow followUser(Follow follow) throws Exception {
        User user = getUser();
        follow.setOwnerId(user.getId());
        followRepository.save(follow);
        User partner = userRepository.findUserById(follow.getPartnerId());
        partner.setTotalFollower(partner.getTotalFollower()+1);
        userRepository.save(partner);
        return follow;
    }

    @Override
    public void unFollowUser(Follow follow) throws Exception {
        User user = getUser();
        followRepository.deleteByOwnerIdAndPartnerId(user.getId(),follow.getPartnerId());
        User partner = userRepository.findUserById(follow.getPartnerId());
        partner.setTotalFollower(partner.getTotalFollower()-1);
        userRepository.save(partner);
    }
}
