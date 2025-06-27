package com.wiseai.domain.entity;

import com.wiseai.domain.common.BaseTimeEntity;
import com.wiseai.domain.dto.ConcertDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "seat_grades")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SeatGrade extends BaseTimeEntity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_grade_id", nullable = false)
    private Long id; // 좌석 등급 고유 ID
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert; // 연관된 콘서트
    
    @Column(name = "grade_name", nullable = false, length = 50)
    private String gradeName; // 등급명 (예: VIP, R석, S석, A석)
    
    @Column(name = "price", nullable = false)
    private Integer price; // 등급별 가격
    
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats; // 해당 등급의 전체 좌석 수
    
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats; // 해당 등급의 예매 가능한 좌석 수
    
    @Column(name = "description", length = 200)
    private String description; // 등급 설명 (예: "무대와 가장 가까운 VIP석")

    public static SeatGrade of(ConcertDto.SeatGradeRequest request){
        return new SeatGrade(
            null,
            null,
            request.gradeName(),
            request.price(),
            request.totalSeats(),
            request.totalSeats(),
            request.description()
        );
    }
    
    public void setConcert(Concert concert) {
        this.concert = concert;
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
    
    public boolean isAvailable() {
        return availableSeats > 0;
    }
} 