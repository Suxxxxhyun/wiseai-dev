package com.wiseai.api.concert.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.api.concert.swagger.ConcertSwagger;
import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.ConcertDto;
import com.wiseai.domain.dto.SeatDto;
import com.wiseai.domain.service.ConcertService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertController implements ConcertSwagger {
    
    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<Response<Page<ConcertDto.Response>>> getConcerts(
        @PageableDefault final Pageable pageable
    ) {
        Page<ConcertDto.Response> response = concertService.getConcerts(pageable);
        return ResponseEntity.ok(Response.ok(response));
    }

    @GetMapping("/available")
    public ResponseEntity<Response<Page<ConcertDto.Response>>> getAvailableConcerts(
        @PageableDefault final Pageable pageable
    ) {
        Page<ConcertDto.Response> response = concertService.getAvailableConcerts(pageable);
        return ResponseEntity.ok(Response.ok(response));
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<Response<ConcertDto.Response>> getConcert(
        final Long concertId
    ) {
        ConcertDto.Response response = concertService.getConcert(concertId);
        return ResponseEntity.ok(Response.ok(response));
    }

    @GetMapping("/{concertId}/seats")
    public ResponseEntity<Response<SeatDto.SeatMapResponse>> getConcertSeatMap(
        final Long concertId
    ) {
        SeatDto.SeatMapResponse response = concertService.getConcertSeatMap(concertId);
        return ResponseEntity.ok(Response.ok(response));
    }

	@GetMapping("/search")
    public ResponseEntity<Response<Page<ConcertDto.Response>>> searchConcerts(
        final String keyword,
        @PageableDefault final Pageable pageable
    ) {
        Page<ConcertDto.Response> response = concertService.searchConcerts(keyword, pageable);
        return ResponseEntity.ok(Response.ok(response));
    }

    @GetMapping("/artist")
    public ResponseEntity<Response<Page<ConcertDto.Response>>> getConcertsByArtist(
        final String artist,
        @PageableDefault final Pageable pageable
    ) {
        Page<ConcertDto.Response> response = concertService.getConcertsByArtist(artist, pageable);
        return ResponseEntity.ok(Response.ok(response));
    }
} 