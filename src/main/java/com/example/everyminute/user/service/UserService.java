package com.example.everyminute.user.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.user.dto.request.JoinUserReq;
import com.example.everyminute.user.entity.User;
import com.example.everyminute.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void join(JoinUserReq joinUserReq) {
        if(userRepository.existsByEmailAndIsEnable(joinUserReq.getEmail(), true)) throw new BaseException(BaseResponseCode.USER_ALREADY_JOIN);
        joinUserReq.setPassword(passwordEncoder.encode(joinUserReq.getPassword()));
        userRepository.save(User.toEntity(joinUserReq));
    }
}
