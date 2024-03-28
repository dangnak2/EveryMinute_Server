package com.example.everyminute.school.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.news.entity.News;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 100)
    private String region;

    @OneToMany(mappedBy = "school")
    private List<News> newsList = new ArrayList<News>();
}
