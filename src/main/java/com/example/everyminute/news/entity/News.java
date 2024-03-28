package com.example.everyminute.news.entity;

import com.example.everyminute.school.entity.School;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

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
