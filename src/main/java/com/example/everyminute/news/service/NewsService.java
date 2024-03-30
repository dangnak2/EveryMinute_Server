package com.example.everyminute.news.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.dto.request.UpdateNewsReq;
import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.news.repository.NewsRepository;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public void postNewsByAdmin(User user, Long schoolId, PostNewsReq postNewsReq) {
        checkAdminRole(user);
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));

        // 소식 제목이 겹칠 경우 예외처리 (제목은 중복되지 않는다.)
        if (newsRepository.existsByTitleAndIsEnable(postNewsReq.getTitle(), true))
            throw new BaseException(BaseResponseCode.INVALID_NEWS_TITLE);

        // 소식 저장 후 학교 엔티티 양방향 연관관계 저장
        school.addNews(newsRepository.save(News.of(postNewsReq, school, user)));
    }

    @Transactional
    public void removeNewsByAdmin(User user, Long newsId) {
        checkAdminRole(user);
        News news = newsRepository.findByNewsIdAndIsEnable(newsId, true).orElseThrow(() -> new BaseException(BaseResponseCode.NEWS_NOT_FOUND));

        // 양방향 연관관계 해제 후 News 삭제
        news.getSchool().removeNews(news);
        news.remove();
    }

    @Transactional
    public void updateNewsByAdmin(User user, Long newsId, UpdateNewsReq updateNewsReq) {
        checkAdminRole(user);
        News news = newsRepository.findByNewsIdAndIsEnable(newsId, true).orElseThrow(() -> new BaseException(BaseResponseCode.NEWS_NOT_FOUND));
        news.update(updateNewsReq.getTitle(), updateNewsReq.getContents());
    }

    private static void checkAdminRole(User user) {
        if (!user.checkRole(Role.ADMIN)) throw new BaseException(BaseResponseCode.NO_AUTHENTICATION);
    }

    // 학교 페이지 소식 모음
    public Page<SchoolNewsRes> getSchoolNews(Long schoolId, Pageable pageable) {
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));
        return newsRepository.getSchoolNews(school, pageable);
    }

    // 유저가 구독한 학교 페이지 소식 모음
    public Page<SchoolNewsRes> getSchoolNews(User user, Pageable pageable) {
        List<School> schools = user.getSubscribeList().stream().filter(s -> s.getIsEnable().equals(true)).map(Subscribe::getSchool).collect(Collectors.toList());
        return newsRepository.getSchoolNews(schools, pageable);
    }
}
