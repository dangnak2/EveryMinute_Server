package com.example.everyminute.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private BaseResponseCode baseResponseCode;
}
