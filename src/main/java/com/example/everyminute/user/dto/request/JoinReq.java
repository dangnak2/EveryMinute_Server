package com.example.everyminute.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinReq {

    @Schema(type = "String", description = "성명", example = "홍길동", required = true)
    @NotBlank(message = "U0008")
    private String name;

    @Schema(type = "String", description = "이메일", example = "student@email.com", required = true)
    @Email(message = "U0005")
    @NotBlank(message = "U0006")
    private String email;

    @Schema(type = "String", description = "비밀번호", example = "qwer1234!", required = true)
    @Pattern(message = "U0007", regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$")
    @NotBlank(message = "U0003")
    private String password;

    @Schema(type = "String", description = "역할", example = "학생", required = true)
    @NotBlank(message = "U0002")
    private String role;
}
