package com.example.everyminute.school.service;

import com.example.everyminute.school.dto.request.RegisterSchoolReq;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.everyminute.school.dto.TestSchoolDto.setUpRegisterSchoolReq;
import static com.example.everyminute.school.dto.TestSchoolDto.setUpSchool;
import static com.example.everyminute.user.dto.TestUserDto.PASSWORD;
import static com.example.everyminute.user.dto.TestUserDto.setUpUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {

    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private SchoolRepository schoolRepository;

    @Test
    @DisplayName("[성공] 관리자에 의한 학교 등록")
    void registerSchoolByAdmin() {
        // given
        User user = setUpUser(1L, Role.ADMIN, "test");
        School school = setUpSchool(1L, "명지대학교", "서울");
        RegisterSchoolReq req = setUpRegisterSchoolReq();

        // when
        when(schoolRepository.save(any(School.class))).then(AdditionalAnswers.returnsFirstArg());
        schoolService.registerSchoolByAdmin(user, req);

        // then
        verify(schoolRepository, times(1)).existsByNameAndRegionAndIsEnable(any(String.class), any(String.class), any(Boolean.class));
        verify(schoolRepository, times(1)).save(any(School.class));
    }
}
