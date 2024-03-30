package com.example.everyminute.subscribe.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.subscribe.dto.response.GetSubscriptionsRes;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.subscribe.repository.SubscribeRepository;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final SchoolRepository schoolRepository;

    // todo 구독 중복 예외처리
    @Transactional
    public void subscribeSchool(User user, Long schoolId) {
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));
        user.addSubscribe(subscribeRepository.save(Subscribe.of(user, school)));
    }

    @Transactional
    public void cancel(User user, Long schoolId) {
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));
        Subscribe subscribe = subscribeRepository.findByUserAndSchoolAndIsEnable(user, school, true).orElseThrow(() -> new BaseException(BaseResponseCode.SUBSCRIBE_NOT_FOUND));
        subscribe.cancel();
        user.cancelSubscribe(subscribe);
    }

    public Page<GetSubscriptionsRes> getSubscriptions(User user, Pageable pageable) {
        return subscribeRepository.getSubscriptionsByUser(user, pageable);
    }

}
