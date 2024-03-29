package com.example.everyminute.news.repository;

import com.example.everyminute.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, NewsCustom {

    Boolean existsByTitleAndIsEnable(String title, Boolean isEnable);

    Optional<News> findByNewsIdAndIsEnable(Long newsId, Boolean isEnable);
}
