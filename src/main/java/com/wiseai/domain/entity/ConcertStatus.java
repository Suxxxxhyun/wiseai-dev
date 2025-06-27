package com.wiseai.domain.entity;

public enum ConcertStatus {
	UPCOMING,    // 예매 오픈 전
	BOOKING_OPEN, // 예매 진행중
	SOLD_OUT,    // 매진
	COMPLETED,   // 공연 완료
	CANCELLED    // 취소됨
}
