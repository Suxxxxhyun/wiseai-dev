package com.wiseai.api.concert.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.SeatDto;
import com.wiseai.global.security.details.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public interface ConcertAuthSwagger {
	@Operation(summary = "좌석 선택", description = "좌석을 선택하여 임시 예약합니다. (10분 유효)")
	ResponseEntity<Response<SeatDto.SeatSelectionResponse>> selectSeats(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Parameter(description = "콘서트 ID") @PathVariable final Long concertId,
		@RequestBody final SeatDto.SeatSelectionRequest request
	);
}
