package com.wiseai.api.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.login.dto.LoginDto.LoginDto;
import com.wiseai.api.login.service.LoginService;
import com.wiseai.api.login.swagger.LoginSwagger;
import com.wiseai.domain.common.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController implements LoginSwagger {
	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Response<LoginDto.Response>> login(
		@RequestBody @Valid final LoginDto.Request request
	){
		final var response = loginService.login(request);
		return ResponseEntity.ok(Response.ok(response));
	}
}
