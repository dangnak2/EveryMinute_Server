package com.example.everyminute.news.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.global.utils.RedisUtil;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.dto.request.UpdateNewsReq;
import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.news.repository.NewsRepository;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final SchoolRepository schoolRepository;
    private final RedisUtil redisUtil;

    @Transactional
    public void postNewsByAdmin(User user, Long schoolId, PostNewsReq postNewsReq) {
        checkAdminRole(user);
        School school = schoolRepository.findBySchoolIdAndIsEnable(schoolId, true).orElseThrow(() -> new BaseException(BaseResponseCode.SCHOOL_NOT_FOUNT));

        // 소식 제목이 겹칠 경우 예외처리 (제목은 중복되지 않는다.)
        if (newsRepository.existsByTitleAndIsEnable(postNewsReq.getTitle(), true))
            throw new BaseException(BaseResponseCode.INVALID_NEWS_TITLE);

        // 소식 저장 후 학교 엔티티 양방향 연관관계 저장
        News news = newsRepository.save(News.of(postNewsReq, school, user));
        school.addNews(news);

        // 뉴스 발행 후 피드 DB 업데이트
        redisUtil.updateNewsFeed(school.getSubscribeList(), news.getNewsId());
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

    // 유저별 뉴스피드 조회
    public Page<SchoolNewsRes> getNewsFeed(User user, Pageable pageable) {
        return newsRepository.getNewsFeed(redisUtil.getNewsListByUserId(user.getUserId()), pageable);
    }

}
