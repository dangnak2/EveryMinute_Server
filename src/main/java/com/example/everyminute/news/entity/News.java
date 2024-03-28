package com.example.everyminute.news.entity;

import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(columnDefinition="TEXT")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "school_Id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public News(@NonNull String title, String contents, School school, User user) {
        this.title = title;
        this.contents = contents;
        this.school = school;
        this.user = user;
    }

    public static News of(PostNewsReq postNewsReq, School school, User user) {
        return News.builder()
                .title(postNewsReq.getTitle())
                .contents(postNewsReq.getContents())
                .school(school)
                .user(user)
                .build();
    }

    public void remove() {
        this.setIsEnable(false);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
