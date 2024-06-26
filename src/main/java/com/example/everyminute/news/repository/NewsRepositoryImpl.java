package com.example.everyminute.news.repository;

import com.example.everyminute.news.dto.response.SchoolNewsRes;
import com.example.everyminute.news.entity.News;
import com.example.everyminute.news.entity.QNews;
import com.example.everyminute.school.entity.School;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.everyminute.news.entity.QNews.news;


@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SchoolNewsRes> getSchoolNews(School school, Pageable pageable) {
        List<News> schoolNews = jpaQueryFactory.selectFrom(news)
                .where(news.school.eq(school)
                        .and(news.isEnable.eq(true)))
                .orderBy(news.createdAt.desc())
                .fetch();

        List<SchoolNewsRes> res = schoolNews.stream()
                .map(SchoolNewsRes::toDto).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), res.size());
        return new PageImpl<>(res.subList(start, end), pageable, res.size());
    }

    @Override
    public Page<SchoolNewsRes> getNewsFeed(Set<Object> list, Pageable pageable) {

        List<News> feed = list.stream().map(m -> {
            return jpaQueryFactory.selectFrom(news)
                    .where(news.newsId.eq(Long.valueOf(m.toString()))
                            .and(news.isEnable.eq(true)))
                    .fetchOne();
        }).collect(Collectors.toList());

        feed.sort(Comparator.comparing(News::getCreatedAt).reversed());

        List<SchoolNewsRes> res = feed.stream()
                .map(SchoolNewsRes::toDto).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), res.size());
        return new PageImpl<>(res.subList(start, end), pageable, res.size());
    }
}
