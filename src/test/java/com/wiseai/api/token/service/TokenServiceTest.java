package com.wiseai.api.token.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wiseai.api.token.dto.AccessTokenDto;
import com.wiseai.domain.dto.CustomUserInfoDto;
import com.wiseai.domain.entity.Role;
import com.wiseai.domain.entity.User;
import com.wiseai.domain.service.UserService;
import com.wiseai.global.security.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

	@InjectMocks
	TokenService tokenService;

	@Mock
	UserService userService;

	@Mock
	JwtUtil jwtUtil;

	@Test
	@DisplayName("RT에 의해 AT를 재발급한다.")
	void createAccessTokenByRefreshToken() {
		// given
		String refreshToken = "Bearer testRefreshToken";
		String expected = "ACCESS";
		User user = setUser();

		Date accessTokenExpireTime = new Date();

		// when
		given(userService.findUserByRefreshToken(refreshToken)).willReturn(user);
		given(jwtUtil.createAccessTokenExpireTime()).willReturn(accessTokenExpireTime);
		given(jwtUtil.createAccessToken(any(CustomUserInfoDto.class), any(Date.class))).willReturn(expected);


		AccessTokenDto.Response response = tokenService.createAccessTokenByRefreshToken(refreshToken);

		// then
		assertEquals(expected, response.accessToken());
	}

	private User setUser(){
		return User.builder()
			.id(1L)
			.name("test")
			.email("test@test.com")
			.role(Role.MEMBER)
			.build();
	}
}