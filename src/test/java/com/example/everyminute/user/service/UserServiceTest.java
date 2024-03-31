package com.example.everyminute.user.service;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.global.utils.JwtUtil;
import com.example.everyminute.user.dto.TokenDto;
import com.example.everyminute.user.dto.request.JoinReq;
import com.example.everyminute.user.dto.request.LoginReq;
import com.example.everyminute.user.entity.Role;
import com.example.everyminute.user.entity.User;
import com.example.everyminute.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.everyminute.user.dto.TestUserDto.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    JwtUtil jwtUtil;
    @Spy
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("[성공] 로그인")
    void login(){
        // given
        User user = setUpUser(1L, Role.ADMIN, passwordEncoder.encode(PASSWORD));
        LoginReq req = setUpLoginReq("test@email.com", "test");

        // when
        // stub 생성
        doReturn(Optional.of(user)).when(userRepository).findByEmailAndIsEnable(req.getEmail(), true);
        when(jwtUtil.createToken(user.getUserId(), user.getRole())).thenReturn(TokenDto.toDto("accessToken", "refreshToken", user.getRole()));
        TokenDto dto = userService.login(req);

        // then
        assertThat(req.getEmail()).isEqualTo(user.getEmail());
        assertThat(passwordEncoder.matches(req.getPassword(), user.getPassword())).isTrue();
        assertThat(dto.getAccessToken()).isEqualTo("accessToken");
        assertThat(dto.getRefreshToken()).isEqualTo("refreshToken");

        // verify
        verify(userRepository, times(1)).findByEmailAndIsEnable(any(String.class), any(Boolean.class));
        verify(jwtUtil, times(1)).createToken(any(Long.class), any(Role.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    @Test
    @DisplayName("[실패] 로그인")
    void loginFail(){
        // given
        LoginReq req = setUpLoginReq("test1@email.com", "test");
        // when
        doThrow(new BaseException(BaseResponseCode.USER_NOT_FOUND)).when(userRepository).findByEmailAndIsEnable(req.getEmail(), true);
        // then
        BaseException exception = assertThrows(BaseException.class, () -> {
            userService.login(req);
        });
        assertThat(exception.getBaseResponseCode()).isEqualTo(BaseResponseCode.USER_NOT_FOUND);
    }
}
