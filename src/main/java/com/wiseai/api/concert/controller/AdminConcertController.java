package com.wiseai.api.concert.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wiseai.domain.common.Response;
import com.wiseai.domain.dto.ConcertDto;
import com.wiseai.domain.service.ConcertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Admin Concert", description = "관리자 콘서트 관리 API")
@RestController
@RequestMapping("/api/admin/concerts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminConcertController {
    
    private final ConcertService concertService;
    
    @Operation(summary = "콘서트 생성", description = "새로운 콘서트를 생성합니다.")
    @PostMapping
    public ResponseEntity<Response<ConcertDto.Response>> createConcert(
        @RequestBody ConcertDto.Request request
    ) {
        ConcertDto.Response response = concertService.createConcert(request);
        return ResponseEntity.ok(Response.ok(response));
    }
    
    @Operation(summary = "콘서트 수정", description = "기존 콘서트 정보를 수정합니다.")
    @PutMapping("/{concertId}")
    public ResponseEntity<Response<ConcertDto.Response>> updateConcert(
        @Parameter(description = "콘서트 ID") @PathVariable Long concertId,
        @RequestBody ConcertDto.UpdateRequest request
    ) {
        // TODO: updateConcert 메서드 구현 필요
        return ResponseEntity.ok(Response.ok(null));
    }
    
    @Operation(summary = "콘서트 삭제", description = "콘서트를 삭제합니다.")
    @DeleteMapping("/{concertId}")
    public ResponseEntity<Response<Void>> deleteConcert(
        @Parameter(description = "콘서트 ID") @PathVariable Long concertId
    ) {
        // TODO: deleteConcert 메서드 구현 필요
        return ResponseEntity.ok(Response.ok(null));
    }
} 