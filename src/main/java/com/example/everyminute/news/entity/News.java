package com.example.everyminute.news.entity;

import com.example.everyminute.global.config.AwsS3ImageUrlUtil;
import com.example.everyminute.global.entity.BaseEntity;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.dto.request.UpdateNewsReq;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    @Size(max = 255)
    private String imgKey;

    @ManyToOne
    @JoinColumn(name = "school_Id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public News(Long newsId, @NonNull String title, String contents, String imgKey, School school, User user) {
        this.newsId = newsId;
        this.title = title;
        this.contents = contents;
        this.imgKey = imgKey;
        this.school = school;
        this.user = user;
    }

    public static News of(PostNewsReq postNewsReq, School school, User user) {
        return News.builder()
                .title(postNewsReq.getTitle())
                .contents(postNewsReq.getContents())
                .imgKey(postNewsReq.getImgKey())
                .school(school)
                .user(user)
                .build();
    }

    public void remove() {
        this.setIsEnable(false);
    }

    public void update(UpdateNewsReq updateNewsReq) {
        if (!Objects.equals(title, updateNewsReq.getTitle())) title = updateNewsReq.getTitle();
        if (!Objects.equals(contents, updateNewsReq.getContents())) contents = updateNewsReq.getContents();
        if (!Objects.equals(AwsS3ImageUrlUtil.toUrl(imgKey), updateNewsReq.getImgKey())) imgKey = updateNewsReq.getImgKey();
    }
}
