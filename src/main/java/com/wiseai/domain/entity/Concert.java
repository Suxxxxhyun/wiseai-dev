package com.wiseai.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wiseai.domain.common.BaseTimeEntity;
import com.wiseai.domain.dto.ConcertDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "concerts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Concert extends BaseTimeEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id", nullable = false)
    private Long id; // 콘서트 고유 ID
    
    @Column(name = "title", nullable = false, length = 200)
    private String title; // 콘서트 제목
    
    @Column(name = "description", nullable = false, length = 500)
    private String description; // 콘서트 설명
    
    @Column(name = "concert_date", nullable = false)
    private LocalDateTime concertDate; // 공연 날짜 및 시간
    
    @Column(name = "venue", nullable = false, length = 200)
    private String venue; // 공연 장소
    
    @Column(name = "artist", nullable = false, length = 100)
    private String artist; // 아티스트명
    
    @Column(name = "booking_open_time", nullable = false)
    private LocalDateTime bookingOpenTime; // 예매 오픈 시간
    
    @Column(name = "booking_close_time", nullable = false)
    private LocalDateTime bookingCloseTime; // 예매 마감 시간
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ConcertStatus status; // 콘서트 상태 (UPCOMING, BOOKING_OPEN, SOLD_OUT, COMPLETED, CANCELLED)
    
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats; // 전체 좌석 수
    
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats; // 예매 가능한 좌석 수
    
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SeatGrade> seatGrades = new ArrayList<>(); // 좌석 등급 목록
    
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Seat> seats = new ArrayList<>(); // 좌석 목록
    
    public static Concert of(ConcertDto.Request request) {
        return Concert.builder()
            .title(request.title())
            .description(request.description())
            .concertDate(request.concertDate())
            .venue(request.venue())
            .artist(request.artist())
            .bookingOpenTime(request.bookingOpenTime())
            .bookingCloseTime(request.bookingCloseTime())
            .status(ConcertStatus.UPCOMING)
            .totalSeats(request.totalSeats())
            .availableSeats(request.totalSeats())
            .build();
    }
    
    public void updateStatus(ConcertStatus status) {
        this.status = status;
    }
    
    public void decreaseAvailableSeats() {
        if (this.availableSeats > 0) {
            this.availableSeats--;
        }
    }
    
    public void increaseAvailableSeats() {
        if (this.availableSeats < this.totalSeats) {
            this.availableSeats++;
        }
    }
    
    public boolean isBookingOpen() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(bookingOpenTime) && now.isBefore(bookingCloseTime) && status == ConcertStatus.BOOKING_OPEN;
    }
    
    public boolean isSoldOut() {
        return availableSeats <= 0;
    }
    
    public void addSeatGrade(SeatGrade seatGrade) {
        this.seatGrades.add(seatGrade);
        seatGrade.setConcert(this);
    }
    
    public void addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setConcert(this);
    }
} 