package com.example.everyminute.news.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.dto.request.UpdateNewsReq;
import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.news.service.NewsService;
import com.example.everyminute.user.entity.User;
import io.swagger.annotations.Api;
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

    // 소식 작성
    @PostMapping("/{schoolId}")
    public ResponseCustom postNewsByAdmin(
            @Account User user,
            @PathVariable Long schoolId,
            @RequestBody PostNewsReq postNewsReq)
    {
        newsService.postNewsByAdmin(user, schoolId, postNewsReq);
        return ResponseCustom.OK();
    }

    // 소식 삭제
    @DeleteMapping("/{newsId}")
    public ResponseCustom removeNewsByAdmin(
            @Account User user,
            @PathVariable Long newsId)
    {
        newsService.removeNewsByAdmin(user, newsId);
        return ResponseCustom.OK();
    }

    // 소식 수정
    @PatchMapping("/{newsId}")
    public ResponseCustom updateNewsByAdmin(
           @Account User user,
           @PathVariable Long newsId,
           @RequestBody UpdateNewsReq updateNewsReq
    )
    {
        newsService.updateNewsByAdmin(user, newsId, updateNewsReq);
        return ResponseCustom.OK();
    }

    // 학교 페이지별 소식
    @GetMapping("/{schoolId}")
    public ResponseCustom<Page<SchoolNewsRes>> getSchoolNews(
            @PathVariable Long schoolId,
            @PageableDefault(size = 20) Pageable pageable)
    {
        return ResponseCustom.OK(newsService.getSchoolNews(schoolId, pageable));
    }

    /*
    - 학생이 구독중인 학교 뉴스피드
    - 구독 시점부터 최신순으로 노출됨
    - 구독을 취소해도 기존 뉴스피드에 노출된 소식은 유지
     */
    @GetMapping("")
    public ResponseCustom<Page<SchoolNewsRes>> getSchoolNews(
            @Account User user,
            @PageableDefault(size = 20) Pageable pageable)
    {
        return ResponseCustom.OK(newsService.getSchoolNews(user, pageable));
    }
}
