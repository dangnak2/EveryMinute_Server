package com.example.everyminute.news.repository;

import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.school.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface NewsCustom {
    Page<SchoolNewsRes> getSchoolNews(School school, Pageable pageable);

    Page<SchoolNewsRes> getNewsFeed(Set<Object> list, Pageable pageable);
}
