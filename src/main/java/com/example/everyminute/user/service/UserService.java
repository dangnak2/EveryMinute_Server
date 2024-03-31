package com.example.everyminute.user.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.global.utils.JwtUtil;
import com.example.everyminute.user.dto.TokenDto;
import com.example.everyminute.user.dto.request.JoinReq;
import com.example.everyminute.user.dto.request.LoginReq;
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
    private final JwtUtil jwtUtil;

    // 회원가입
    @Transactional
    public void join(JoinReq joinReq) {
        if(userRepository.existsByEmailAndIsEnable(joinReq.getEmail(), true)) throw new BaseException(BaseResponseCode.ALREADY_USED_EMAIL);
        joinReq.setPassword(passwordEncoder.encode(joinReq.getPassword()));
        userRepository.save(User.of(joinReq));
    }

    // 로그인
    public TokenDto login(LoginReq loginReq) {
        User user = userRepository.findByEmailAndIsEnable(loginReq.getEmail(), true).orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) throw new BaseException(BaseResponseCode.INVALID_PASSWORD);
        return jwtUtil.createToken(user.getUserId(), user.getRole());
    }

}
