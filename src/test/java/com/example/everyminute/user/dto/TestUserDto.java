package com.example.everyminute.user.dto;

import com.example.everyminute.user.dto.request.JoinReq;
import com.example.everyminute.user.dto.request.LoginReq;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;

public class TestUserDto {

    public static final String PASSWORD = "test";


    public static LoginReq setUpLoginReq(String email, String pw){
        return LoginReq.builder()
                .email(email)
                .password(pw)
                .build();
    }

    public static User setUpUser(Long userId, Role role, String pw){
        return User.builder()
                .userId(userId)
                .name("홍길동")
                .email("test@email.com")
                .password(pw)
                .role(role)
                .build();
    }
}
