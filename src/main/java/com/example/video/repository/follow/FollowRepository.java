package com.example.video.repository.follow;

import com.example.video.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer>,FollowRepositoryCustom {
    void deleteByOwnerIdAndPartnerId(int ownerId, int partnerId);

    boolean existsByOwnerIdAndPartnerId(int ownerId, int partnerId);
}
