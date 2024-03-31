package com.example.everyminute.subscribe.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.subscribe.dto.response.GetSubscriptionsRes;
import com.example.everyminute.subscribe.service.SubscribeService;
import com.example.everyminute.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(tags = "구독 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribes")
public class SubscribeController {

    private final SubscribeService subscribeService;

    // 학교 구독
    @Operation(summary = "학교 구독", description = "학교를 페이지를 구독한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 학교 구독 성공"),
            @ApiResponse(responseCode = "404", description = "(C0004) 존재하지 않는 학교입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("/{schoolId}")
    public ResponseCustom subscribe(
            @Account User user,
            @Parameter(description = "(Long) 학교 Id", example = "1") @PathVariable Long schoolId)
    {
        subscribeService.subscribeSchool(user, schoolId);
        return ResponseCustom.OK();
    }

    // 학교 구독 취소
    @Operation(summary = "학교 구독 취소", description = "학교 페이지 구독을 취소한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 학교 구독 취소 성공"),
            @ApiResponse(responseCode = "404", description = "(C0004) 존재하지 않는 학교입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @DeleteMapping("/{schoolId}")
    public ResponseCustom cancel(
            @Account User user,
            @Parameter(description = "(Long) 학교 Id", example = "1") @PathVariable Long schoolId)
    {
        subscribeService.cancel(user, schoolId);
        return ResponseCustom.OK();
    }

    // 학교 구독 목록 조회
    @Operation(summary = "학교 구독 목록 조회", description = "학교 구독 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 구독 목록 조회 성공"),
    })
    @GetMapping("")
    public ResponseCustom<Page<GetSubscriptionsRes>> getSubscriptions(
            @Account User user,
            @PageableDefault(size = 20) Pageable pageable)
    {
        return ResponseCustom.OK(subscribeService.getSubscriptions(user, pageable));
    }
}
