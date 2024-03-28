package com.example.everyminute.news.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.news.dto.request.PostNewsReq;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.news.repository.NewsRepository;
import com.example.everyminute.school.entity.School;
import com.example.everyminute.school.repository.SchoolRepository;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static void checkAdminRole(User user) {
        if (!user.checkRole(Role.ADMIN)) throw new BaseException(BaseResponseCode.NO_AUTHENTICATION);
    }
}
