package com.example.everyminute.subscribe.repository;

import com.example.everyminute.subscribe.dto.response.GetSubscriptionsRes;
import com.example.everyminute.subscribe.entity.Subscribe;
import com.example.everyminute.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.everyminute.subscribe.entity.QSubscribe.subscribe;

@RequiredArgsConstructor
public class SubscribeRepositoryImpl implements SubscribeCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GetSubscriptionsRes> getSubscriptionsByUser(User user, Pageable pageable) {
        List<Subscribe> subscribes = jpaQueryFactory.selectFrom(subscribe)
                .where(subscribe.user.eq(user)
                        .and(subscribe.isEnable.eq(true)))
                .orderBy(subscribe.createdAt.desc())
                .fetch();

        List<GetSubscriptionsRes> res = subscribes.stream()
                .map(GetSubscriptionsRes::toDto)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), res.size());
        return new PageImpl<>(res.subList(start, end), pageable, res.size());
    }
}
