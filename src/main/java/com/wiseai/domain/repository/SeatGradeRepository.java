package com.wiseai.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wiseai.domain.entity.SeatGrade;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {
    
    // 콘서트의 모든 좌석 등급 조회
    List<SeatGrade> findByConcertId(Long concertId);
    
    // 콘서트의 사용 가능한 좌석 등급 조회
    @Query("SELECT sg FROM SeatGrade sg WHERE sg.concert.id = :concertId AND sg.availableSeats > 0 ORDER BY sg.price ASC")
    List<SeatGrade> findAvailableGradesByConcertId(@Param("concertId") Long concertId);
    
    // 특정 가격 범위의 좌석 등급 조회
    @Query("SELECT sg FROM SeatGrade sg WHERE sg.concert.id = :concertId AND sg.price BETWEEN :minPrice AND :maxPrice")
    List<SeatGrade> findByConcertIdAndPriceBetween(@Param("concertId") Long concertId, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);
} 