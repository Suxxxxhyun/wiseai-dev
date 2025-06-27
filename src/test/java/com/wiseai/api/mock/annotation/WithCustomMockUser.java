package com.wiseai.api.mock.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import com.wiseai.api.mock.security.WithCustomMockUserSecurityContextFactory;
import com.wiseai.domain.entity.Role;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

	long id() default 1L;

	String email() default "test@test.com";

	String name() default "test";

	String password() default "1234";

	Role role() default Role.MEMBER;
}
