package com.example.everyminute.user.entity;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    STUDENT("학생"),
    ADMIN("관리자");
    private final String value;

    public static Role getRoleByName(String value) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getValue().equals(value))
                .findAny().orElseThrow(() -> new BaseException(BaseResponseCode.ROLE_NOT_FOUND));
    }
}

