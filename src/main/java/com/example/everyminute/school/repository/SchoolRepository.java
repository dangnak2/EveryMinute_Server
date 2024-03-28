package com.example.everyminute.school.repository;

import com.example.everyminute.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    Boolean existsByNameAndRegionAndIsEnable(String name, String region, Boolean isEnable);

}