package com.example.everyminute.subscribe.controller;

import com.example.everyminute.global.resolver.Account;
import com.example.everyminute.global.response.ResponseCustom;
import com.example.everyminute.subscribe.service.SubscribeService;
import com.example.everyminute.user.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "구독 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscribes")
public class SubscribeController {

    private final SubscribeService subscribeService;

    // 학교 구독
    @PostMapping("/{schoolId}")
    public ResponseCustom subscribeSchool(
            @Account User user,
            @PathVariable Long schoolId)
    {
        subscribeService.subscribeSchool(user, schoolId);
        return ResponseCustom.OK();
    }
}
