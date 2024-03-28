package com.example.everyminute.school.repository;

import com.example.everyminute.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    Boolean existsByNameAndRegionAndIsEnable(String name, String region, Boolean isEnable);

    Optional<School> findBySchoolIdAndIsEnable(Long schoolId, Boolean isEnable);
}
