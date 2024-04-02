package com.example.everyminute.news.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PostNewsReq {

    @Schema(type = "String", description = "소식 제목", example = "명지대학교 공지 1", required = true)
    @NotNull(message = "N0002")
    private String title;

    @Schema(type = "String", description = "소식 내용", example = "공지 1입니다.", required = true)
    @NotNull(message = "N0003")
    private String contents;

    @Schema(type = "String", description = "소식 imgKey", example = "photo/example.png", required = true)
    private String imgKey;

}
