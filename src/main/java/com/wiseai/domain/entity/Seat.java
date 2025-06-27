package com.wiseai.domain.entity;

import java.time.LocalDateTime;

import com.wiseai.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Seat extends BaseTimeEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long id; // 좌석 고유 ID
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert; // 연관된 콘서트
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_grade_id", nullable = false)
    private SeatGrade seatGrade; // 연관된 좌석 등급
    
    @Column(name = "row_num", nullable = false, length = 10)
    private String rowNumber; // 행 번호 (예: A, B, C...)
    
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber; // 좌석 번호 (예: 1, 2, 3...)
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SeatStatus status; // 좌석 상태 (AVAILABLE, RESERVED, BOOKED, UNAVAILABLE)
    
    @Column(name = "price", nullable = false)
    private Integer price; // 좌석 가격
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserved_by")
    private User reservedBy; // 예약한 사용자 (임시 예약 시)
    
    @Column(name = "reserved_at")
    private LocalDateTime reservedAt; // 예약 시간 (임시 예약 시)
    
    @Column(name = "reservation_expiry_time")
    private LocalDateTime reservationExpiryTime; // 예약 만료 시간 (임시 예약 시)
    
    public enum SeatStatus {
        AVAILABLE,      // 예매 가능
        RESERVED,       // 임시 예약됨 (10분 타이머)
        BOOKED,         // 예매 완료
        UNAVAILABLE     // 사용 불가
    }
    
    public void setConcert(Concert concert) {
        this.concert = concert;
    }
    
    public void setSeatGrade(SeatGrade seatGrade) {
        this.seatGrade = seatGrade;
    }
    
    public void reserve(User user) {
        this.status = SeatStatus.RESERVED;
        this.reservedBy = user;
        this.reservedAt = LocalDateTime.now();
        this.reservationExpiryTime = LocalDateTime.now().plusMinutes(10);
    }
    
    public void confirmBooking() {
        this.status = SeatStatus.BOOKED;
        this.reservationExpiryTime = null;
    }
    
    public void release() {
        this.status = SeatStatus.AVAILABLE;
        this.reservedBy = null;
        this.reservedAt = null;
        this.reservationExpiryTime = null;
    }
    
    public boolean isReservationExpired() {
        return reservationExpiryTime != null && LocalDateTime.now().isAfter(reservationExpiryTime);
    }
    
    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }
    
    public boolean isReserved() {
        return status == SeatStatus.RESERVED;
    }
    
    public boolean isBooked() {
        return status == SeatStatus.BOOKED;
    }
    
    public String getSeatIdentifier() {
        return rowNumber + "-" + seatNumber;
    }
} 