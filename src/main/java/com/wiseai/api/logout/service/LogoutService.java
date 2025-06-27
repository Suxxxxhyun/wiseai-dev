package com.wiseai.api.logout.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wiseai.domain.entity.User;
import com.wiseai.domain.service.UserService;
import com.wiseai.global.error.ErrorCode;
import com.wiseai.global.error.exception.AuthenticationException;
import com.wiseai.global.security.jwt.constant.TokenType;
import com.wiseai.global.security.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LogoutService {
	private final UserService userService;
	private final JwtUtil jwtUtil;

	public void logout(final String accessToken){

		// 1. 토큰 검증
		jwtUtil.validateToken(accessToken);

		// 2. 토큰 타입 확인
		final Claims tokenClaims = jwtUtil.getTokenClaims(accessToken);
		final String tokenType = tokenClaims.getSubject();
		if(!TokenType.isAccessToken(tokenType)){
			throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
		}

		// 3. refresh token 만료 처리
		final String email = String.valueOf(tokenClaims.get("email"));
		final User user = userService.findByEmail(email);
		user.expireRefreshToken(LocalDateTime.now());
	}
}
