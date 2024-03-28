package com.example.everyminute.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinUserReq {

    @Schema(type = "String", description = "성명", example = "홍길동", required = true)
    @NotBlank(message = "U0007")
    private String name;

    @Schema(type = "String", description = "이메일", example = "1234@email.com", required = true)
    @Email(message = "U0002")
    @NotBlank(message = "U0004")
    private String email;

    @Schema(type = "String", description = "비밀번호", example = "qwer1234!", required = true)
    @Pattern(message = "U0003", regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$")
    @NotBlank(message = "U0005")
    private String password;

    @Schema(type = "String", description = "역할", example = "학생", required = true)
    @NotBlank(message = "U0002")
    private String role;
}
