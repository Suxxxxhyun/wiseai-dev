package com.wiseai.api.concert.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.concert.swagger.ConcertAuthSwagger;
import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.SeatDto;
import com.wiseai.domain.service.ConcertService;
import com.wiseai.global.security.details.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth/concerts")
@RequiredArgsConstructor
public class ConcertAuthController implements ConcertAuthSwagger {

	private final ConcertService concertService;

	@PostMapping("/{concertId}/seats/select")
	public ResponseEntity<Response<SeatDto.SeatSelectionResponse>> selectSeats(
		CustomUserDetails userDetails,
		final Long concertId,
		final SeatDto.SeatSelectionRequest request
	) {
		SeatDto.SeatSelectionResponse response = concertService.selectSeats(userDetails.getUser().getUserId(), request);
		return ResponseEntity.ok(Response.ok(response));
	}
}
