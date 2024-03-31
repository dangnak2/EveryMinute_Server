package com.example.everyminute.global.utils;

import com.example.everyminute.global.Constants;
import com.example.everyminute.subscribe.entity.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<Object, Object> redisTemplate;

    public void setValue(String key, String value, Duration time) {
        redisTemplate.opsForValue().set(key, value, time);
    }

    public void setValue(String key, String value, Long exp, TimeUnit time){
        redisTemplate.opsForValue().set(key, value, exp, time);
    }

    @Transactional
    public void setNewsFeed(Long key, List<Long> value) {
        for (Long l : value) {
            redisTemplate.opsForSet().add(Constants.REDIS.FEED_KEY+key, l);
        }
    }

    public Set<Object> getNewsListByUserId(Long key) {
        return redisTemplate.opsForSet().members(Constants.REDIS.FEED_KEY + key);
    }

    public void updateNewsFeed(List<Subscribe> subscribeList, Long value) {
        for (Subscribe subscribe : subscribeList) {
            redisTemplate.opsForSet().add(Constants.REDIS.FEED_KEY+subscribe.getUser().getUserId(), value);
        }
    }

    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    @Transactional
    public void deleteValue(String key){
        redisTemplate.delete(key);
    }
}
