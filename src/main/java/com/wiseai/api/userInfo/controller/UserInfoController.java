package com.wiseai.api.userInfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.userInfo.dto.UserInfoDto;
import com.wiseai.api.userInfo.service.UserInfoService;
import com.wiseai.api.userInfo.swagger.UserInfoSwagger;
import com.wiseai.domain.common.Response;
import com.wiseai.domain.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserInfoController implements UserInfoSwagger {
	private final UserInfoService userInfoService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/me")
	public ResponseEntity<Response<UserInfoDto.Response>> getUserInfo(
		@AuthenticationPrincipal User user
	){
		final var userInfo = userInfoService.getUserInfo(user.getEmail());
		return ResponseEntity.ok(Response.ok(userInfo));
	}
}
