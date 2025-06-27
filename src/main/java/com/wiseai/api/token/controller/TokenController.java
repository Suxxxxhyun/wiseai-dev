package com.wiseai.api.token.controller;

import static com.wiseai.global.error.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.token.dto.AccessTokenDto;
import com.wiseai.api.token.service.TokenService;
import com.wiseai.api.token.swagger.TokenSwagger;
import com.wiseai.domain.common.Response;
import com.wiseai.global.error.ExceptionDto;
import com.wiseai.global.util.AuthorizationHeaderUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController implements TokenSwagger {

	private final TokenService tokenService;

	@PostMapping("/access-token/issue")
	public ResponseEntity<Response<AccessTokenDto.Response>> createAccessToken(
		final HttpServletRequest httpServletRequest
	) {
		final String authorizationHeader = httpServletRequest.getHeader("Authorization");
		if(authorizationHeader != null){
			AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

			String refreshToken = authorizationHeader.split(" ")[1];
			AccessTokenDto.Response accessTokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);
			return ResponseEntity.ok(Response.ok(accessTokenResponseDto));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(Response.fail(new ExceptionDto(NOT_EXISTS_AUTHORIZATION, "Authorization header is missing")));

	}
}
