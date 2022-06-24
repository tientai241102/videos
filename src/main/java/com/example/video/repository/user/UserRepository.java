package com.example.video.repository.user;

import com.example.video.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    Optional<User> findByPhone(String phone);

    User findUserById(int id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

}
