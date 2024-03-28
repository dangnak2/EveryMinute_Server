package com.example.everyminute.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BaseResponseCode {

    SUCCESS("S0001", HttpStatus.OK, "요청에 성공했습니다."),

    BAD_REQUEST("G0001", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NO_AUTHENTICATION("G0002", HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // token
    NULL_TOKEN("T0001", HttpStatus.UNAUTHORIZED, "토큰 값을 입력해주세요."),
    INVALID_TOKEN("T0002", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 값입니다."),
    UNSUPPORTED_TOKEN("T0003", HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰 값입니다."),
    MALFORMED_TOKEN("T0004", HttpStatus.UNAUTHORIZED, "잘못된 구조의 토큰 값입니다."),
    EXPIRED_TOKEN("T0005", HttpStatus.FORBIDDEN, "만료된 토큰 값입니다."),
    NOT_ACCESS_HEADER("T0006", HttpStatus.INTERNAL_SERVER_ERROR, "헤더에 접근할 수 없습니다."),
    BLACKLIST_TOKEN("T0007", HttpStatus.FORBIDDEN, "로그아웃 혹은 회원 탈퇴된 토큰입니다."),
    NOTIFICATION_TOKEN_NOT_FOUND("T0008", HttpStatus.NOT_FOUND, "유효하지 않은 알림 토큰입니다."),

    // user
    USER_NOT_FOUND("U0001", HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    ROLE_NOT_FOUND("U0002", HttpStatus.NOT_FOUND, "역할을 찾을 수 없습니다."),
    USER_ALREADY_JOIN("U0003", HttpStatus.NOT_FOUND, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD("U0004", HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // school : SUCCESS 코드와 중복으로 인해 C000X로 지정함
    NOT_EMPTY_SCHOOL_NAME("C0001", HttpStatus.BAD_REQUEST, "학교명을 입력해주세요,"),
    NOT_EMPTY_SCHOOL_REGION("C0002", HttpStatus.BAD_REQUEST, "학교 자역을 입력해주세요,"),
    ALREADY_REGISTERED_SCHOOL("C0003", HttpStatus.BAD_REQUEST, "이미 등록된 학교입니다."),
    SCHOOL_NOT_FOUNT("C0004", HttpStatus.NOT_FOUND, "존재하지 않는 학교입니다."),

    // news
    INVALID_NEWS_TITLE("N0001", HttpStatus.BAD_REQUEST, "이미 존재하는 소식 제목입니다."),
    NOT_EMPTY_NEWS_TITLE("N0002", HttpStatus.BAD_REQUEST, "소식 제목을 입력해주세요."),
    NOT_EMPTY_NEWS_CONTENT("N0003", HttpStatus.BAD_REQUEST, "소식 내용을 입력해주세요."),
    NEWS_NOT_FOUND("N0004", HttpStatus.NOT_FOUND, "존재하지 않는 소식입니다.");

    public final String code;
    public final HttpStatus status;
    public final String message;

    public static BaseResponseCode findByCode(String code) {
        return Arrays.stream(BaseResponseCode.values())
                .filter(b -> b.getCode().equals(code))
                .findAny().orElseThrow(() -> new BaseException(BAD_REQUEST));
    }

}
