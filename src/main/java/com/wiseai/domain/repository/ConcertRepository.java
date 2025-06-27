package com.wiseai.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wiseai.domain.entity.Concert;
import com.wiseai.domain.entity.ConcertStatus;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    
    // 예매 가능한 콘서트 목록 조회
    @Query("SELECT c FROM Concert c WHERE c.status = :status AND c.bookingOpenTime <= :now AND c.bookingCloseTime >= :now")
    Page<Concert> findAvailableConcerts(@Param("status") ConcertStatus status, @Param("now") LocalDateTime now, Pageable pageable);
    
    // 예매 오픈 예정 콘서트 목록 조회
    @Query("SELECT c FROM Concert c WHERE c.status = :status AND c.bookingOpenTime > :now ORDER BY c.bookingOpenTime ASC")
    List<Concert> findUpcomingConcerts(@Param("status") ConcertStatus status, @Param("now") LocalDateTime now);
    
    // 아티스트별 콘서트 조회
    Page<Concert> findByArtistContainingIgnoreCase(String artist, Pageable pageable);
    
    // 제목으로 콘서트 검색
    Page<Concert> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // 특정 기간 내 콘서트 조회
    @Query("SELECT c FROM Concert c WHERE c.concertDate BETWEEN :startDate AND :endDate")
    List<Concert> findByConcertDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 예매 마감이 임박한 콘서트 조회 (1시간 이내)
    @Query("SELECT c FROM Concert c WHERE c.bookingCloseTime BETWEEN :now AND :oneHourLater AND c.status = :status")
    List<Concert> findConcertsClosingSoon(@Param("now") LocalDateTime now, @Param("oneHourLater") LocalDateTime oneHourLater, @Param("status") ConcertStatus status);
    
    // 매진된 콘서트 조회
    @Query("SELECT c FROM Concert c WHERE c.availableSeats = 0 AND c.status = :status")
    Page<Concert> findSoldOutConcerts(@Param("status") ConcertStatus status, Pageable pageable);
    
    // 콘서트 존재 여부 확인
    boolean existsByTitleAndConcertDate(String title, LocalDateTime concertDate);
} 