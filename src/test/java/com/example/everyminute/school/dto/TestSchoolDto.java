package com.example.everyminute.school.dto;

import com.example.everyminute.school.dto.request.RegisterSchoolReq;
import com.example.everyminute.school.entity.School;

public class TestSchoolDto {

    public static School setUpSchool(Long schoolId, String name, String region) {
        return School.builder()
                .schoolId(schoolId)
                .name(name)
                .region(region)
                .build();
    }

    public static RegisterSchoolReq setUpRegisterSchoolReq() {
        return RegisterSchoolReq.builder()
                .schoolName("명지대학교")
                .region("서울")
                .build();
    }
}
