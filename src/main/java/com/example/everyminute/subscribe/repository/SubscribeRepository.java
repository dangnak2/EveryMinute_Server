package com.example.everyminute.subscribe.repository;

import com.example.everyminute.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
}
