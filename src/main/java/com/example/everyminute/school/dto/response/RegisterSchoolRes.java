package com.example.everyminute.school.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class RegisterSchoolRes {

    @Schema(type = "Long", description = "학교 id", example = "1")
    private Long schoolId;

    public static RegisterSchoolRes toDto(Long schoolId) {
        return RegisterSchoolRes.builder()
                .schoolId(schoolId)
                .build();
    }
}
