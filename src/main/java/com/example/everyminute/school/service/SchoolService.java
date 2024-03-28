package com.example.everyminute.school.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.school.dto.request.RegisterSchoolReq;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    public void registerSchoolByAdmin(User user, RegisterSchoolReq registerSchoolReq) {
        if(!user.getRole().equals(Role.ADMIN)) throw new BaseException(BaseResponseCode.NO_AUTHENTICATION);

        Boolean isRegister = schoolRepository.existsByNameAndRegionAndIsEnable(registerSchoolReq.getSchoolName(), registerSchoolReq.getRegion(), true);
        if(isRegister) throw new BaseException(BaseResponseCode.ALREADY_REGISTERED_SCHOOL);

        schoolRepository.save(School.of(registerSchoolReq));
    }
}
