package com.example.everyminute.user.controller;

import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.user.dto.TokenDto;
import com.example.everyminute.user.dto.request.JoinUserReq;
import com.example.everyminute.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001)회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "(U0014)존재하는 이메일입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("")
    public ResponseCustom<?> join(@RequestBody JoinUserReq joinUserReq) {
        userService.join(joinUserReq);
        return ResponseCustom.OK();
    }
}
