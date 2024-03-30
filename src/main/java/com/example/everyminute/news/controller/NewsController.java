package com.example.everyminute.news.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.dto.request.UpdateNewsReq;
import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.news.service.NewsService;
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

@Api(tags = "학교 소식 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    // 소식 등록
    @Operation(summary = "소식 등록", description = "관리자가 학교 소식을 등록한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 소식 등록 성공"),
            @ApiResponse(responseCode = "409", description = "(C0004) 존재하지 않는 학교입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class))),
            @ApiResponse(responseCode = "409", description = "(N0001) 이미 존재하는 소식 제목입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PostMapping("/{schoolId}")
    public ResponseCustom postNewsByAdmin(
            @Account User user,
            @Parameter(description = "(Long) 학교 Id", example = "1") @PathVariable Long schoolId,
            @RequestBody PostNewsReq postNewsReq)
    {
        newsService.postNewsByAdmin(user, schoolId, postNewsReq);
        return ResponseCustom.OK();
    }

    // 소식 삭제
    @Operation(summary = "소식 삭제", description = "관리자가 학교 소식을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 소식 삭제 성공"),
            @ApiResponse(responseCode = "409", description = "(N0001) 존재하지 않는 소식입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @DeleteMapping("/{newsId}")
    public ResponseCustom removeNewsByAdmin(
            @Account User user,
            @Parameter(description = "(Long) 소식 Id", example = "1") @PathVariable Long newsId)
    {
        newsService.removeNewsByAdmin(user, newsId);
        return ResponseCustom.OK();
    }

    // 소식 수정
    @Operation(summary = "소식 수정", description = "관리자가 학교 소식을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 소식 수정 성공"),
            @ApiResponse(responseCode = "409", description = "(N0001) 존재하지 않는 소식입니다.", content = @Content(schema = @Schema(implementation = ResponseCustom.class)))
    })
    @PatchMapping("/{newsId}")
    public ResponseCustom updateNewsByAdmin(
           @Account User user,
           @Parameter(description = "(Long) 소식 Id", example = "1") @PathVariable Long newsId,
           @RequestBody UpdateNewsReq updateNewsReq
    )
    {
        newsService.updateNewsByAdmin(user, newsId, updateNewsReq);
        return ResponseCustom.OK();
    }

    // 학교 페이지별 소식
    @Operation(summary = "학교 페이지별 소식 조회", description = "학교 페이지별로 소식을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 소식 조회 성공"),
    })
    @GetMapping("/{schoolId}")
    public ResponseCustom<Page<SchoolNewsRes>> getSchoolNews(
            @Parameter(description = "(Long) 학교 Id", example = "1") @PathVariable Long schoolId,
            @PageableDefault(size = 20) Pageable pageable)
    {
        return ResponseCustom.OK(newsService.getSchoolNews(schoolId, pageable));
    }

    /*
    - 학생이 구독중인 학교 뉴스피드
    - 구독 시점부터 최신순으로 노출됨
    - 구독을 취소해도 기존 뉴스피드에 노출된 소식은 유지
     */
    @Operation(summary = "뉴스피드 조회", description = "유저별 뉴스피드를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "(S0001) 조회 성공"),
    })
    @GetMapping("")
    public ResponseCustom<Page<SchoolNewsRes>> getSchoolNews(
            @Account User user,
            @PageableDefault(size = 20) Pageable pageable)
    {
        return ResponseCustom.OK(newsService.getSchoolNews(user, pageable));
    }
}
