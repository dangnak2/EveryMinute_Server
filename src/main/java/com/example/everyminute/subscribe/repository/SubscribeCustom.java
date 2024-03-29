package com.example.everyminute.subscribe.repository;

import com.example.everyminute.subscribe.dto.response.GetSubscriptionsRes;
import com.example.everyminute.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscribeCustom {
    Page<GetSubscriptionsRes> getSubscriptionsByUser(User user, Pageable pageable);
}
