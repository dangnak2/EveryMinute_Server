package com.example.everyminute.subscribe.dto.response;

import com.example.everyminute.subscribe.entity.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSubscriptionsRes {

    @Schema(type = "Long", description = "학교 id", example = "1")
    private Long schoolId;
    @Schema(type = "String", description = "학교 이름", example = "명지대학교")
    private String schoolName;
    @Schema(type = "String", description = "학교 지역", example = "서울")
    private String region;

    public static GetSubscriptionsRes toDto(Subscribe subscribe) {
        return GetSubscriptionsRes.builder()
                .schoolId(subscribe.getSchool().getSchoolId())
                .schoolName(subscribe.getSchool().getName())
                .region(subscribe.getSchool().getRegion())
                .build();
    }
}
