package com.example.everyminute.subscribe.repository;

import com.example.everyminute.school.entity.School;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findByUserAndSchoolAndIsEnable(User user, School school, Boolean isEnable);
}
