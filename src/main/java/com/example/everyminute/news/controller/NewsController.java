package com.example.everyminute.news.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.service.NewsService;
import com.example.everyminute.user.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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

}
