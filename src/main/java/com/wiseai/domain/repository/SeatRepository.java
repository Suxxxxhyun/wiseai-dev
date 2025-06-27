package com.wiseai.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wiseai.domain.entity.Seat;
import com.wiseai.domain.entity.Seat.SeatStatus;

import jakarta.persistence.LockModeType;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    // 콘서트의 모든 좌석 조회
    List<Seat> findByConcertId(Long concertId);
    
    // 콘서트의 특정 등급 좌석 조회
    List<Seat> findByConcertIdAndSeatGradeId(Long concertId, Long seatGradeId);
    
    // 콘서트의 사용 가능한 좌석 조회
    @Query("SELECT s FROM Seat s WHERE s.concert.id = :concertId AND s.status = :status")
    List<Seat> findAvailableSeatsByConcertId(@Param("concertId") Long concertId, @Param("status") SeatStatus status);
    
    // 특정 좌석 조회 (동시성 제어를 위해 PESSIMISTIC_LOCK 사용)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Seat> findById(Long id);
    
    // 좌석 식별자로 조회
    Optional<Seat> findByConcertIdAndRowNumberAndSeatNumber(Long concertId, String rowNumber, String seatNumber);
    
    // 만료된 예약 좌석 조회
    @Query("SELECT s FROM Seat s WHERE s.status = :status AND s.reservationExpiryTime < :now")
    List<Seat> findExpiredReservations(@Param("status") SeatStatus status, @Param("now") LocalDateTime now);
    
    // 사용자별 예약된 좌석 조회
    List<Seat> findByReservedById(Long userId);
    
    // 콘서트별 좌석 상태별 개수 조회
    @Query("SELECT s.status, COUNT(s) FROM Seat s WHERE s.concert.id = :concertId GROUP BY s.status")
    List<Object[]> countSeatsByStatus(@Param("concertId") Long concertId);
    
    // 만료된 예약 좌석 해제
    @Modifying
    @Query("UPDATE Seat s SET s.status = :newStatus, s.reservedBy = null, s.reservedAt = null, s.reservationExpiryTime = null WHERE s.status = :oldStatus AND s.reservationExpiryTime < :now")
    int releaseExpiredReservations(@Param("oldStatus") SeatStatus oldStatus, @Param("newStatus") SeatStatus newStatus, @Param("now") LocalDateTime now);
    
    // 특정 등급의 사용 가능한 좌석 개수 조회
    @Query("SELECT COUNT(s) FROM Seat s WHERE s.concert.id = :concertId AND s.seatGrade.id = :seatGradeId AND s.status = :status")
    long countAvailableSeatsByGrade(@Param("concertId") Long concertId, @Param("seatGradeId") Long seatGradeId, @Param("status") SeatStatus status);
} 