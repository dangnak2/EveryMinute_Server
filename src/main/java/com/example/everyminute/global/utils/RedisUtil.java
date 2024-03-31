package com.example.everyminute.global.utils;

import com.example.everyminute.global.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
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
    public void setValue(Long key, List<Long> value) {
        for (Long l : value) {
            redisTemplate.opsForList().leftPush(Constants.REDIS.FEED_KEY+key, l);
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
