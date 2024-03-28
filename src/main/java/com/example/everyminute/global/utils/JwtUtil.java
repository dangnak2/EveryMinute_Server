package com.example.everyminute.global.utils;

import com.example.everyminute.global.exception.BaseException;
import com.example.everyminute.global.exception.BaseResponseCode;
import com.example.everyminute.user.dto.TokenDto;
import com.example.everyminute.user.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

import static com.example.everyminute.global.Constants.JWT.BEARER_PREFIX;
import static com.example.everyminute.global.Constants.JWT.CLAIM_NAME;


@Component
public class JwtUtil {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;    // 1일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final Key key;
    private final RedisUtil redisUtil;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenDto createToken(Long userId, Role role){
        long now = new Date().getTime();
        String accessToken = createAccessToken(userId, now).compact();
        String refreshToken = createToken(now, REFRESH_TOKEN_EXPIRE_TIME).compact();
        return TokenDto.toDto(BEARER_PREFIX + accessToken, BEARER_PREFIX + refreshToken, role);
    }

    // create Token 생성
    private JwtBuilder createAccessToken(Long userId, long now) {
        return createToken(now, JwtUtil.ACCESS_TOKEN_EXPIRE_TIME)
                .claim(CLAIM_NAME, userId)
                .setSubject(userId.toString());
    }

    // Token 기본 생성 및 RefreshToken 생성
    private JwtBuilder createToken(long now, long expireTime) {
        return Jwts.builder()
                .setExpiration(new Date(now + expireTime))
                .signWith(key, SignatureAlgorithm.HS256);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException e) {
            throw new BaseException(BaseResponseCode.INVALID_TOKEN);
        } catch (MalformedJwtException e){
            throw new BaseException(BaseResponseCode.MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BaseException(BaseResponseCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(BaseResponseCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new BaseException(BaseResponseCode.NULL_TOKEN);
        }
    }

    // 토큰 내 정보 불러오기
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // redis 내 token 가져오기
    public String getTokenInRedis(String token){
        return redisUtil.getValue(token);
    }

    // userId 불러오기
    public Long getUserIdFromJWT(String accessToken) {
        String userId = String.valueOf(parseClaims(accessToken).get(CLAIM_NAME));
        return Long.parseLong(userId);
    }
    // token의 남은 시간 계산
    private Long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    public static String replaceBearerToken(String token){
        return token.substring(BEARER_PREFIX.length());
    }
}
