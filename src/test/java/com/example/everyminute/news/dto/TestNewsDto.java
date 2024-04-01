package com.example.everyminute.news.dto;

import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.user.entity.User;

public class TestNewsDto {

    public static PostNewsReq setUpPostNewsReq(String title, String contents) {
        return PostNewsReq.builder()
                .title(title)
                .contents(contents)
                .build();
    }

    public static News setUpNews(String title, String contents, User user, School school) {
        return News.builder()
                .newsId(1L)
                .title(title)
                .contents(contents)
                .user(user)
                .school(school)
                .build();
    }
}
