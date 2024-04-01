package com.example.everyminute.user.dto.response;

import com.example.everyminute.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginRes {

    @Schema(type = "String", description = "AccessToken", example = "bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTY3NTg2OTcsInVzZXJJZHgiOjEsInN1YiI6IjEifQ.DSBuBlStkjhT05vuzjWd-cg7naG5KikUxII734u3nUw")
    @NotBlank(message = "G0001")
    private String accessToken;

    @Schema(type = "String", description = "사용자 역할", example = "BASIC / ADMIN")
    private Role role;

    public static LoginRes toDto(String accessToken, Role role){
        return LoginRes.builder()
                .accessToken(accessToken)
                .role(role)
                .build();
    }
}
