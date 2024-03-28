package com.example.everyminute.news.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.school.entity.School;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    @NonNull
    @Size(max = 70)
    private String title;

    @Column(columnDefinition="TEXT")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "school_Id")
    private School school;
}
