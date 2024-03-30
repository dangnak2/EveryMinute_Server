package com.example.everyminute.user.controller;

import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.user.dto.TokenDto;
import com.example.everyminute.user.dto.request.JoinReq;
import com.example.everyminute.user.dto.request.LoginReq;
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
            @ApiResponse(responseCode = "409", description = "(U0003)존재하는 이메일입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("/join")
    public ResponseCustom join(@RequestBody JoinReq joinReq) {
        userService.join(joinReq);
        return ResponseCustom.OK();
    }

    @Operation(summary = "로그인", description = "로그인을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001)로그인 성공"),
            @ApiResponse(responseCode = "409", description = "(U0003) 존재하지 않는 이메일입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "409", description = "(U0004) 패스워드가 일치하지 않습니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("/login")
    public ResponseCustom<TokenDto> login(@RequestBody LoginReq loginReq) {
        return ResponseCustom.OK(userService.login(loginReq));
    }
}
