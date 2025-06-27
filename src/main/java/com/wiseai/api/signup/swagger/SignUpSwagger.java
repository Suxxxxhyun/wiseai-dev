package com.wiseai.api.signup.swagger;

import org.springframework.http.ResponseEntity;

import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "member", description = "회원가입/회원정보조회")
public interface SignUpSwagger {

	@Tag(name = "member")
	@Operation(summary = "회원가입 API", description = "회원가입 API")
	ResponseEntity<Response<UserDto.Response>> signup(final UserDto.Request request);
}
