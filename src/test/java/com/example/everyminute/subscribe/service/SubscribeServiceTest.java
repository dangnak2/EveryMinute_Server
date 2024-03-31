package com.example.everyminute.subscribe.service;

import com.example.everyminute.global.utils.RedisUtil;

import com.example.everyminute.school.dto.request.RegisterSchoolReq;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.subscribe.repository.SubscribeRepository;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.everyminute.school.dto.TestSchoolDto.setUpSchool;
import static com.example.everyminute.user.dto.TestUserDto.setUpUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscribeServiceTest {

    @InjectMocks
    private SubscribeService subscribeService;

    @Mock
    private SubscribeRepository subscribeRepository;
    @Mock
    private  SchoolRepository schoolRepository;
    @Mock
    private RedisUtil redisUtil;

    @Test
    @DisplayName("[성공] 학교 구독")
    void subscribeSchool() {
        // given
        User user = setUpUser(1L, Role.STUDENT, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");
        // when
        doReturn(Optional.of(school)).when(schoolRepository).findBySchoolIdAndIsEnable(school.getSchoolId(), true);
        subscribeService.subscribeSchool(user, 7L);

        // then
        verify(schoolRepository, times(1)).findBySchoolIdAndIsEnable(any(Long.class), any(Boolean.class));
        verify(subscribeRepository, times(1)).existsByUserAndSchoolAndIsEnable(any(User.class), any(School.class), any(Boolean.class));
        verify(redisUtil, times(1)).setNewsFeed(any(Long.class), anyList());
        verify(subscribeRepository, times(1)).save(any(Subscribe.class));
    }

    @Test
    void cancel() {
    }

    @Test
    void getSubscriptions() {
    }
}
