package com.wiseai.domain.dto;

import com.wiseai.domain.entity.Role;
import com.wiseai.domain.entity.User;
import com.wiseai.global.security.jwt.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto extends UserDto {
	private Long userId;
	private String email;
	private String password;
	private String name;
	private Role role;

	public static CustomUserInfoDto of(User user){
		return new CustomUserInfoDto(
			user.getId(),
			user.getEmail(),
			user.getPassword(),
			user.getName(),
			user.getRole());
	}
}
