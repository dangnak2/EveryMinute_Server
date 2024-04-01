package com.example.everyminute.school.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.school.dto.request.RegisterSchoolReq;
import com.example.everyminute.school.dto.response.RegisterSchoolRes;
import com.example.everyminute.school.service.SchoolService;
import com.example.everyminute.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "학교 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/schools")
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "학교 생성", description = "학교 페이지를 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 학교 생성 성공"),
            @ApiResponse(responseCode = "404", description = "(C0003) 이미 등록된 학교입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("")
    public ResponseCustom<RegisterSchoolRes> registerSchoolByAdmin(
            @Account User user,
            @RequestBody @Valid RegisterSchoolReq registerSchoolReq)
    {
        return ResponseCustom.OK(schoolService.registerSchoolByAdmin(user, registerSchoolReq));
    }
}
