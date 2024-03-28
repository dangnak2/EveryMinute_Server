package com.example.everyminute.news.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateNewsReq {

    @Schema(type = "String", description = "수정 할 제목", example = "명지대학교 공지 1(수정됨)", required = true)
    @NotNull(message = "N0002")
    private String title;

    @Schema(type = "String", description = "수정 할 내용", example = "공지 1입니다.(수정됨)", required = true)
    @NotNull(message = "N0003")
    private String contents;
}
