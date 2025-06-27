package com.wiseai.global.error.exception;

import com.wiseai.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}

}

