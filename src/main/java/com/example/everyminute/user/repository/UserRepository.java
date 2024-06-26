package com.example.everyminute.user.repository;

import com.example.everyminute.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndIsEnable(Long userId, Boolean isEnable);

    Boolean existsByEmailAndIsEnable(String email, Boolean isEnable);

    Optional<User> findByEmailAndIsEnable(String email, Boolean isEnable);
}
