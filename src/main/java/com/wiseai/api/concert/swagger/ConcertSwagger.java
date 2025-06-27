package com.wiseai.api.concert.swagger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.ConcertDto;
import com.wiseai.domain.dto.SeatDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Concert", description = "콘서트 API")
public interface ConcertSwagger {
    
    @Operation(summary = "콘서트 목록 조회", description = "모든 콘서트 목록을 페이지네이션으로 조회합니다.")
    ResponseEntity<Response<Page<ConcertDto.Response>>> getConcerts(
        @PageableDefault final Pageable pageable
    );

    @Operation(summary = "예매 가능한 콘서트 목록 조회", description = "현재 예매 가능한 콘서트 목록을 조회합니다.")
    ResponseEntity<Response<Page<ConcertDto.Response>>> getAvailableConcerts(
        @PageableDefault final Pageable pageable
    );
    
    @Operation(summary = "콘서트 상세 조회", description = "특정 콘서트의 상세 정보를 조회합니다.")
    ResponseEntity<Response<ConcertDto.Response>> getConcert(
        @Parameter(description = "콘서트 ID") @PathVariable final Long concertId
    );
    
    @Operation(summary = "콘서트 좌석 배치도 조회", description = "콘서트의 좌석 배치도와 상태를 조회합니다.")
    ResponseEntity<Response<SeatDto.SeatMapResponse>> getConcertSeatMap(
        @Parameter(description = "콘서트 ID") @PathVariable final Long concertId
    );
    
    @Operation(summary = "콘서트 검색", description = "제목으로 콘서트를 검색합니다.")
    ResponseEntity<Response<Page<ConcertDto.Response>>> searchConcerts(
        @Parameter(description = "검색 키워드") @RequestParam final String keyword,
        @PageableDefault final Pageable pageable
    );
    
    @Operation(summary = "아티스트별 콘서트 조회", description = "특정 아티스트의 콘서트 목록을 조회합니다.")
    ResponseEntity<Response<Page<ConcertDto.Response>>> getConcertsByArtist(
        @Parameter(description = "아티스트명") @RequestParam final String artist,
        @PageableDefault final Pageable pageable
    );
} 