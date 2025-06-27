package com.wiseai.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.wiseai.domain.entity.Concert;
import com.wiseai.domain.entity.ConcertStatus;

import lombok.Builder;

public class ConcertDto {
    
    @Builder
    public record Request(
        String title,
        String description,
        LocalDateTime concertDate,
        String venue,
        String artist,
        LocalDateTime bookingOpenTime,
        LocalDateTime bookingCloseTime,
        Integer totalSeats,
        List<SeatGradeRequest> seatGrades
    ) {}
    
    @Builder
    public record Response(
        Long id,
        String title,
        String description,
        LocalDateTime concertDate,
        String venue,
        String artist,
        LocalDateTime bookingOpenTime,
        LocalDateTime bookingCloseTime,
        ConcertStatus status,
        Integer totalSeats,
        Integer availableSeats,
        List<SeatGradeResponse> seatGrades,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
    ) {
        public static Response from(Concert concert) {
            return Response.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .concertDate(concert.getConcertDate())
                .venue(concert.getVenue())
                .artist(concert.getArtist())
                .bookingOpenTime(concert.getBookingOpenTime())
                .bookingCloseTime(concert.getBookingCloseTime())
                .status(concert.getStatus())
                .totalSeats(concert.getTotalSeats())
                .availableSeats(concert.getAvailableSeats())
                .seatGrades(concert.getSeatGrades() != null ?
                    concert.getSeatGrades().stream()
                        .map(SeatGradeResponse::from)
                        .toList() : List.of())
                .createdDate(concert.getCreatedDate())
                .lastModifiedDate(concert.getLastModifiedDate())
                .build();
        }
    }
    
    @Builder
    public record UpdateRequest(
        String title,
        String description,
        LocalDateTime concertDate,
        String venue,
        String artist,
        LocalDateTime bookingOpenTime,
        LocalDateTime bookingCloseTime,
        ConcertStatus status
    ) {}
    
    @Builder
    public record SeatGradeRequest(
        String gradeName,
        Integer price,
        Integer totalSeats,
        String description
    ) {}
    
    @Builder
    public record SeatGradeResponse(
        Long id,
        String gradeName,
        Integer price,
        Integer totalSeats,
        Integer availableSeats,
        String description
    ) {
        public static SeatGradeResponse from(com.wiseai.domain.entity.SeatGrade seatGrade) {
            return SeatGradeResponse.builder()
                .id(seatGrade.getId())
                .gradeName(seatGrade.getGradeName())
                .price(seatGrade.getPrice())
                .totalSeats(seatGrade.getTotalSeats())
                .availableSeats(seatGrade.getAvailableSeats())
                .description(seatGrade.getDescription())
                .build();
        }
    }
} 