package com.example.everyminute.news.repository;

import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.school.entity.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsCustom {
    Page<SchoolNewsRes> getSchoolNews(School school, Pageable pageable);

    Page<SchoolNewsRes> getSchoolNews(List<School> schools, Pageable pageable);
}
