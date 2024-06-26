package com.example.everyminute.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class LoginReq {

    @Schema(type = "String", description = "이메일", example = "student@email.com", required = true)
    @Email(message = "U0005")
    @NotBlank(message = "U0006")
    private String email;

    @Schema(type = "String", description = "비밀번호", example = "qwer1234!", required = true)
    @Pattern(message = "U0007", regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$")
    @NotBlank(message = "U0003")
    private String password;
}
