package com.example.everyminute.subscribe.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
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
import static com.example.everyminute.subscribe.dto.TestSubscribeDto.setUpSubscribe;
import static com.example.everyminute.user.dto.TestUserDto.setUpUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("[실패] 학교 구독 - 학교가 존재하지 않는 경우")
    void subscribeSchoolFail() {
        // given
        User user = setUpUser(1L, Role.STUDENT, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");

        // when
        BaseException exception = assertThrows(BaseException.class, () -> {
            subscribeService.subscribeSchool(user, school.getSchoolId());
        });

        // then
        assertThat(exception.getBaseResponseCode()).isEqualTo(BaseResponseCode.SCHOOL_NOT_FOUNT);
    }

    @Test
    @DisplayName("[성공] 구독 취소")
    void cancel() {
        // given
        User user = setUpUser(1L, Role.STUDENT, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");
        Subscribe subscribe = setUpSubscribe(user, school);

        // when
        doReturn(Optional.of(school)).when(schoolRepository).findBySchoolIdAndIsEnable(school.getSchoolId(), true);
        doReturn(Optional.of(subscribe)).when(subscribeRepository).findByUserAndSchoolAndIsEnable(user, school, true);
        subscribeService.cancel(user, 7L);

        // then
        verify(schoolRepository, times(1)).findBySchoolIdAndIsEnable(any(Long.class), any(Boolean.class));
        verify(subscribeRepository, times(1)).findByUserAndSchoolAndIsEnable(any(User.class), any(School.class), any(Boolean.class));
        verify(subscribeRepository, times(1)).delete(any(Subscribe.class));
    }

    @Test
    @DisplayName("[실패] 구독 취소 - 구독 내역이 존재하지 않는 경우")
    void cancelFail() {
        // given
        User user = setUpUser(1L, Role.STUDENT, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");

        // when
        doReturn(Optional.of(school)).when(schoolRepository).findBySchoolIdAndIsEnable(school.getSchoolId(), true);
        BaseException exception = assertThrows(BaseException.class, () -> {
            subscribeService.cancel(user, school.getSchoolId());
        });

        // then
        assertThat(exception.getBaseResponseCode()).isEqualTo(BaseResponseCode.SUBSCRIBE_NOT_FOUND);
    }

    @Test
    void getSubscriptions() {
    }
}
