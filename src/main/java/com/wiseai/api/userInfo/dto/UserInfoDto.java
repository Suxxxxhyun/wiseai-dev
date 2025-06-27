package com.wiseai.api.userInfo.dto;

import com.wiseai.domain.entity.Role;
import com.wiseai.domain.entity.User;

import lombok.Builder;

public class UserInfoDto {
	@Builder
	public record Response(
		String email,
		String name,
		Role role
	){
		public static Response of(User user){
			return new Response(
				user.getEmail(),
				user.getName(),
				user.getRole()
			);
		}
	}
}
