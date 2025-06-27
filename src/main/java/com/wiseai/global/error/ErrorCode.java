package com.wiseai.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
	NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
	NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
	NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "A-008", "인증되지 않은 사용자입니다."),
	NOT_VALID_ERROR(HttpStatus.BAD_REQUEST, "A-009", "잘못된 요청입니다."),
	
	// 콘서트 관련 에러
	CONCERT_NOT_FOUND(HttpStatus.NOT_FOUND, "C-001", "콘서트를 찾을 수 없습니다."),
	DUPLICATE_CONCERT(HttpStatus.CONFLICT, "C-002", "이미 존재하는 콘서트입니다."),
	BOOKING_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "C-003", "예매 가능한 시간이 아닙니다."),
	CONCERT_SOLD_OUT(HttpStatus.BAD_REQUEST, "C-004", "매진된 콘서트입니다."),
	
	// 좌석 관련 에러
	SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "S-001", "좌석을 찾을 수 없습니다."),
	SEAT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "S-002", "예매 가능하지 않은 좌석입니다."),
	INVALID_SEAT_IDENTIFIER(HttpStatus.BAD_REQUEST, "S-003", "잘못된 좌석 식별자입니다."),
	SEAT_ALREADY_RESERVED(HttpStatus.CONFLICT, "S-004", "이미 예약된 좌석입니다."),
	RESERVATION_EXPIRED(HttpStatus.BAD_REQUEST, "S-005", "좌석 예약이 만료되었습니다."),
	
	// 사용자 관련 에러
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U-001", "사용자를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}

