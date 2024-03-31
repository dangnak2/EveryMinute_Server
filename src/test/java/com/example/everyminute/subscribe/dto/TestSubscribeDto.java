package com.example.everyminute.subscribe.dto;

import com.example.everyminute.school.entity.School;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.user.entity.User;

public class TestSubscribeDto {

    public static Subscribe setUpSubscribe (User user,School school) {
        return Subscribe.builder()
                .subscribeId(1L)
                .user(user)
                .school(school)
                .build();
    }
}
