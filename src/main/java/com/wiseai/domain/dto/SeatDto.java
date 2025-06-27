package com.wiseai.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.wiseai.domain.entity.Concert;
import com.wiseai.domain.entity.Seat;
import com.wiseai.domain.entity.Seat.SeatStatus;

import lombok.Builder;

public class SeatDto {
    
    @Builder
    public record Response(
        Long id,
        Long concertId,
        Long seatGradeId,
        String rowNumber,
        String seatNumber,
        SeatStatus status,
        Integer price,
        String seatIdentifier,
        LocalDateTime reservedAt,
        LocalDateTime reservationExpiryTime
    ) {
        public static Response from(Seat seat) {
            return Response.builder()
                .id(seat.getId())
                .concertId(seat.getConcert().getId())
                .seatGradeId(seat.getSeatGrade().getId())
                .rowNumber(seat.getRowNumber())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .price(seat.getPrice())
                .seatIdentifier(seat.getSeatIdentifier())
                .reservedAt(seat.getReservedAt())
                .reservationExpiryTime(seat.getReservationExpiryTime())
                .build();
        }
    }
    
    @Builder
    public record SeatMapResponse(
        Long concertId,
        String concertTitle,
        String venue,
        LocalDateTime concertDate,
        List<SeatGradeInfo> seatGrades,
        List<SeatInfo> seats
    ) {
        public static SeatMapResponse from(Concert concert) {
            List<SeatGradeInfo> seatGrades = concert.getSeatGrades().stream()
                .map(SeatGradeInfo::from)
                .toList();
            
            List<SeatInfo> seats = concert.getSeats().stream()
                .map(SeatInfo::from)
                .toList();
            
            return SeatMapResponse.builder()
                .concertId(concert.getId())
                .concertTitle(concert.getTitle())
                .venue(concert.getVenue())
                .concertDate(concert.getConcertDate())
                .seatGrades(seatGrades)
                .seats(seats)
                .build();
        }
    }
    
    @Builder
    public record SeatGradeInfo(
        Long id,
        String gradeName,
        Integer price,
        Integer totalSeats,
        Integer availableSeats,
        String description
    ) {
        public static SeatGradeInfo from(com.wiseai.domain.entity.SeatGrade seatGrade) {
            return SeatGradeInfo.builder()
                .id(seatGrade.getId())
                .gradeName(seatGrade.getGradeName())
                .price(seatGrade.getPrice())
                .totalSeats(seatGrade.getTotalSeats())
                .availableSeats(seatGrade.getAvailableSeats())
                .description(seatGrade.getDescription())
                .build();
        }
    }
    
    @Builder
    public record SeatInfo(
        Long id,
        String rowNumber,
        String seatNumber,
        SeatStatus status,
        Integer price,
        String gradeName
    ) {
        public static SeatInfo from(Seat seat) {
            return SeatInfo.builder()
                .id(seat.getId())
                .rowNumber(seat.getRowNumber())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .price(seat.getPrice())
                .gradeName(seat.getSeatGrade().getGradeName())
                .build();
        }
    }
    
    @Builder
    public record SeatSelectionRequest(
        Long concertId,
        List<String> seatIdentifiers // "A-1", "A-2" 형태
    ) {}
    
    @Builder
    public record SeatSelectionResponse(
        List<Response> selectedSeats,
        Integer totalPrice,
        LocalDateTime reservationExpiryTime
    ) {
        public static SeatSelectionResponse from(List<Seat> seats, Integer totalPrice, LocalDateTime reservationExpiryTime) {
            List<Response> selectedSeats = seats.stream()
                .map(Response::from)
                .toList();
            
            return SeatSelectionResponse.builder()
                .selectedSeats(selectedSeats)
                .totalPrice(totalPrice)
                .reservationExpiryTime(reservationExpiryTime)
                .build();
        }
    }
} 