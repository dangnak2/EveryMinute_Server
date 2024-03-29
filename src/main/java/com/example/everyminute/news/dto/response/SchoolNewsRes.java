package com.example.everyminute.news.dto.response;

import com.example.everyminute.global.utils.DateTimeUtil;
import com.example.everyminute.news.entity.News;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolNewsRes {

    @Schema(type = "String", description = "소식 제목", example = "명지대학교 공지1")
    private String title;
    @Schema(type = "String", description = "소식 내용", example = "명지대학교 공지1입니다.")
    private String contents;
    @Schema(type = "String", description = "작성자", example = "김민기")
    private String  writer;
    @Schema(type = "String", description = "작성일시", example = "2024-03-29 12:00")
    private String createAt;

    public static SchoolNewsRes toDto(News news) {
        return SchoolNewsRes.builder()
                .title(news.getTitle())
                .contents(news.getContents())
                .writer(news.getUser().getName())
                .createAt(DateTimeUtil.dateTimeToString(news.getCreatedAt()))
                .build();
    }

}
