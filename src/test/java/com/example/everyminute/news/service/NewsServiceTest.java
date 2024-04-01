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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        // verify
        verify(schoolRepository, times(1)).findBySchoolIdAndIsEnable(any(Long.class), any(Boolean.class));
        verify(newsRepository, times(1)).existsByTitleAndIsEnable(any(String.class), any(Boolean.class));
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    @DisplayName("[실패] 관리자에 의한 소식 등록 - 제목이 중복될 경우")
    void postNewsByAdminFail() {
        // given
        User user = setUpUser(14L, Role.ADMIN, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");
        PostNewsReq req = setUpPostNewsReq("명지대학교 소식 1", "명지대학교 소식 1 입니다.");

        // when
        doReturn(Optional.of(school)).when(schoolRepository).findBySchoolIdAndIsEnable(school.getSchoolId(), true);
        doReturn(true).when(newsRepository).existsByTitleAndIsEnable(req.getTitle(), true);
        BaseException exception = assertThrows(BaseException.class, () -> {
            newsService.postNewsByAdmin(user, school.getSchoolId(), req);
        });

        // then
        assertThat(exception.getBaseResponseCode()).isEqualTo(BaseResponseCode.INVALID_NEWS_TITLE);
    }

    @Test
    @DisplayName("[성공] 관리자에 의한 소식 삭제")
    void removeNewsByAdmin() {
        // given
        User user = setUpUser(14L, Role.ADMIN, "test");
        School school = setUpSchool(7L, "명지대학교", "서울");
        PostNewsReq req = setUpPostNewsReq("명지대학교 소식 1", "명지대학교 소식 1 입니다.");
        News news = setUpNews(req.getTitle(), req.getContents(), user, school);

        // when
        doReturn(Optional.of(news)).when(newsRepository).findByNewsIdAndIsEnable(news.getNewsId(), true);
        newsService.removeNewsByAdmin(user, news.getNewsId());

        // verify
        verify(newsRepository, times(1)).findByNewsIdAndIsEnable(any(Long.class), any(Boolean.class));
    }

    @Test
    @DisplayName("[실패] 관리자에 의한 소식 삭제 - 소식이 존재하지 않을 경우")
    void removeNewsByAdminFail() {
        // when
        User user = setUpUser(14L, Role.ADMIN, "test");
        Long newsId = 1L;

        // then
        BaseException exception = assertThrows(BaseException.class, () -> {
            newsService.removeNewsByAdmin(user, newsId);
        });

        // then
        assertThat(exception.getBaseResponseCode()).isEqualTo(BaseResponseCode.NEWS_NOT_FOUND);
    }

    @Test
    void updateNewsByAdmin() {
    }

    @Test
    void getNewsFeed() {
    }
}
