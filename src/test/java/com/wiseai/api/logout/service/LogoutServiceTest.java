package com.wiseai.api.logout.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wiseai.domain.entity.Role;
import com.wiseai.domain.entity.User;
import com.wiseai.domain.service.UserService;
import com.wiseai.global.error.ErrorCode;
import com.wiseai.global.error.exception.AuthenticationException;
import com.wiseai.global.security.jwt.constant.TokenType;
import com.wiseai.global.security.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

	@InjectMocks
	LogoutService logoutService;

	@Mock
	JwtUtil jwtUtil;

	@Mock
	UserService userService;

	@Test
	@DisplayName("AT를 기반으로 로그아웃을 수행한다.")
	void logout() {
		// given
		String testToken = "accessToken";
		Claims claims = Jwts.claims();
		claims.put("email", "test@test.com");
		claims.put("role", Role.MEMBER);

		User user = mock(User.class);

		Jwts.builder().setClaims(claims)
				.setSubject(TokenType.ACCESS.name());

		given(jwtUtil.validateToken(testToken)).willReturn(true);
		given(jwtUtil.getTokenClaims(testToken)).willReturn(claims);
		given(userService.findByEmail("test@test.com")).willReturn(user);

		// when
		logoutService.logout(testToken);

		// then
		verify(user).expireRefreshToken(any(LocalDateTime.class));
	}

	@Test
	@DisplayName("유효하지 않은 AT로 요청시, 로그아웃 수행 실패")
	void logout_ThrowsException_IfAccessTokenNotValid() {
		// given
		String testToken = "accessToken";

		given(jwtUtil.validateToken(testToken))
			.willThrow(new AuthenticationException(ErrorCode.NOT_VALID_TOKEN));

		// when & then
		assertThatThrownBy(
			() -> logoutService.logout(testToken))
			.isInstanceOf(AuthenticationException.class);
	}

	@Test
	@DisplayName("토큰이 AT가 아닌 경우, 로그아웃 수행 실패")
	void logout_ThrowsException_IfNotAccessToken() {
		// given
		String testToken = "accessToken";
		Claims claims = Jwts.claims();
		claims.put("email", "test@test.com");
		claims.put("role", Role.MEMBER);

		Jwts.builder().setClaims(claims)
			.setSubject(TokenType.REFRESH.name());

		given(jwtUtil.validateToken(testToken)).willReturn(true);
		given(jwtUtil.getTokenClaims(testToken)).willReturn(claims);

		// when & then
		assertThatThrownBy(
			() -> logoutService.logout(testToken))
			.isInstanceOf(AuthenticationException.class)
			.hasMessage(ErrorCode.NOT_ACCESS_TOKEN_TYPE.getMessage());
	}

	@Test
	@DisplayName("이메일로 회원을 찾지 못한 경우, 로그아웃 수행 실패")
	void logout_ThrowsException_IfUserNotFound() {
		// given
		String testToken = "accessToken";
		Claims claims = Jwts.claims();
		claims.put("email", "test@test.com");
		claims.put("role", Role.MEMBER);

		Jwts.builder().setClaims(claims)
			.setSubject(TokenType.ACCESS.name());

		given(jwtUtil.validateToken(testToken)).willReturn(true);
		given(jwtUtil.getTokenClaims(testToken)).willReturn(claims);
		given(userService.findByEmail("test@test.com")).willThrow(new UsernameNotFoundException("회원이 존재하지 않습니다."));


		// when & then
		assertThatThrownBy(
			() -> logoutService.logout(testToken))
			.isInstanceOf(UsernameNotFoundException.class);
	}
}