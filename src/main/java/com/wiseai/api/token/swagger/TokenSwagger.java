package com.wiseai.api.token.swagger;

import org.springframework.http.ResponseEntity;

import com.wiseai.api.token.dto.AccessTokenDto;
import com.wiseai.domain.common.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

public interface TokenSwagger {
	@Tag(name = "authentication")
	@Operation(summary = "Access Token 재발급 API", description = "Access Token 재발급 API")
	public ResponseEntity<Response<AccessTokenDto.Response>> createAccessToken(
		final HttpServletRequest httpServletRequest
	);
}
