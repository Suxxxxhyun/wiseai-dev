package com.wiseai.api.token.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wiseai.api.token.dto.AccessTokenDto;
import com.wiseai.domain.dto.CustomUserInfoDto;
import com.wiseai.domain.entity.User;
import com.wiseai.domain.service.UserService;
import com.wiseai.global.security.jwt.constant.GrantType;
import com.wiseai.global.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	public AccessTokenDto.Response createAccessTokenByRefreshToken(final String refreshToken) {
		final User user = userService.findUserByRefreshToken(refreshToken);

		final CustomUserInfoDto infoDto = CustomUserInfoDto.of(user);
		final Date accessTokenExpireTime = jwtUtil.createAccessTokenExpireTime();
		final String accessToken = jwtUtil.createAccessToken(infoDto, accessTokenExpireTime);

		return AccessTokenDto.Response.builder()
			.grantType(GrantType.BEARER.getType())
			.accessToken(accessToken)
			.accessTokenExpireTime(accessTokenExpireTime)
			.build();
	}
}
