package com.example.everyminute.news.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.global.utils.RedisUtil;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.news.repository.NewsRepository;
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

import javax.swing.text.html.Option;
import java.util.Optional;

import static com.example.everyminute.news.dto.TestNewsDto.setUpNews;
import static com.example.everyminute.news.dto.TestNewsDto.setUpPostNewsReq;
import static com.example.everyminute.school.dto.TestSchoolDto.setUpSchool;
import static com.example.everyminute.user.dto.TestUserDto.setUpUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private RedisUtil redisUtil;

    @Test
    @DisplayName("[성공] 관리자에 의한 소식 등록")
    void postNewsByAdmin() {
        // given
        User user = setUpUser(14L, Role.ADMIN, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");
        PostNewsReq req = setUpPostNewsReq("명지대학교 소식 1", "명지대학교 소식 1 입니다.");
        News news = setUpNews(req.getTitle(), req.getContents(), user, school);

        // when
        doReturn(Optional.of(school)).when(schoolRepository).findBySchoolIdAndIsEnable(school.getSchoolId(), true);
        when(newsRepository.save(any(News.class))).thenReturn(news);
        newsService.postNewsByAdmin(user, school.getSchoolId(), req);

        // then
        verify(schoolRepository, times(1)).findBySchoolIdAndIsEnable(any(Long.class), any(Boolean.class));
        verify(newsRepository, times(1)).existsByTitleAndIsEnable(any(String.class), any(Boolean.class));
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    void removeNewsByAdmin() {
    }

    @Test
    void updateNewsByAdmin() {
    }

    @Test
    void getNewsFeed() {
    }
}
