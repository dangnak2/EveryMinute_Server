package com.example.everyminute.school.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class RegisterSchoolReq {

    @Schema(type = "String", description = "학교명", example = "명지대학교", required = true)
    @NotBlank(message = "C0001")
    private String schoolName;

    @Schema(type = "String", description = "학교 지역", example = "서울", required = true)
    @NotBlank(message = "C0002")
    private String region;

}
