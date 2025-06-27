package com.wiseai.api.signup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.signup.swagger.SignUpSwagger;
import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.UserDto;
import com.wiseai.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignUpController implements SignUpSwagger {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Response<UserDto.Response>> signup(
		@RequestBody @Valid final UserDto.Request request
	){
		var response = userService.createUser(request);
		return ResponseEntity.ok(Response.ok(response));
	}
}
