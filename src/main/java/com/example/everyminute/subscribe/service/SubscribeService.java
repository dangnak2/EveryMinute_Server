package com.example.everyminute.subscribe.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.subscribe.repository.SubscribeRepository;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public void subscribeSchool(User user, Long schoolId) {
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));
        subscribeRepository.save(Subscribe.of(user, school));
    }
}
