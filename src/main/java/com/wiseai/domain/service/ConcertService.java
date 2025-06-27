package com.wiseai.domain.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wiseai.domain.dto.ConcertDto;
import com.wiseai.domain.dto.SeatDto;
import com.wiseai.domain.entity.Concert;
import com.wiseai.domain.entity.ConcertStatus;
import com.wiseai.domain.entity.Seat;
import com.wiseai.domain.entity.SeatGrade;
import com.wiseai.domain.entity.User;
import com.wiseai.domain.repository.ConcertRepository;
import com.wiseai.domain.repository.SeatGradeRepository;
import com.wiseai.domain.repository.SeatRepository;
import com.wiseai.domain.repository.UserRepository;
import com.wiseai.global.error.ErrorCode;
import com.wiseai.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {
    
    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;
    private final SeatGradeRepository seatGradeRepository;
    private final UserRepository userRepository;
    
    /**
     * 콘서트 생성
     */
    @Transactional
    public ConcertDto.Response createConcert(final ConcertDto.Request request) {
        // 중복 콘서트 체크
        if (concertRepository.existsByTitleAndConcertDate(request.title(), request.concertDate())) {
            throw new BusinessException(ErrorCode.DUPLICATE_CONCERT);
        }
        
        // 콘서트 생성
        Concert concert = Concert.of(request);
        
        // 좌석 등급 생성
        for (ConcertDto.SeatGradeRequest gradeRequest : request.seatGrades()) {
            SeatGrade seatGrade = SeatGrade.of(gradeRequest);
            concert.addSeatGrade(seatGrade);
        }
        
        // 좌석 생성
        createSeatsForConcert(concert);
        
        Concert savedConcert = concertRepository.save(concert);
        return ConcertDto.Response.from(savedConcert);
    }
    
    /**
     * 콘서트 목록 조회
     */
    public Page<ConcertDto.Response> getConcerts(Pageable pageable) {
        Page<Concert> concertPage = concertRepository.findAll(pageable);
        return concertPage.map(ConcertDto.Response::from);
    }
    
    /**
     * 예매 가능한 콘서트 목록 조회
     */
    public Page<ConcertDto.Response> getAvailableConcerts(Pageable pageable) {
        Page<Concert> concertPage = concertRepository.findAvailableConcerts(
            ConcertStatus.BOOKING_OPEN,
            LocalDateTime.now(), 
            pageable
        );
        return concertPage.map(ConcertDto.Response::from);
    }
    
    /**
     * 콘서트 상세 조회
     */
    public ConcertDto.Response getConcert(Long concertId) {
        Concert concert = findConcertById(concertId);
        // seatGrades를 즉시 로딩
        concert.getSeatGrades().size(); // 지연 로딩 해제
        return ConcertDto.Response.from(concert);
    }
    
    /**
     * 콘서트 좌석 배치도 조회
     */
    public SeatDto.SeatMapResponse getConcertSeatMap(Long concertId) {
        Concert concert = findConcertById(concertId);
        return SeatDto.SeatMapResponse.from(concert);
    }
    
    /**
     * 좌석 선택 (임시 예약)
     */
    @Transactional
    public SeatDto.SeatSelectionResponse selectSeats(Long userId, SeatDto.SeatSelectionRequest request) {
        User user = findUserById(userId);
        Concert concert = findConcertById(request.concertId());
        
        // 예매 가능 시간 체크
        if (!concert.isBookingOpen()) {
            throw new BusinessException(ErrorCode.BOOKING_NOT_AVAILABLE);
        }
        
        List<Seat> selectedSeats = new ArrayList<>();
        int totalPrice = 0;
        
        for (String seatIdentifier : request.seatIdentifiers()) {
            String[] parts = seatIdentifier.split("-");
            if (parts.length != 2) {
                throw new BusinessException(ErrorCode.INVALID_SEAT_IDENTIFIER);
            }
            
            String rowNumber = parts[0];
            String seatNumber = parts[1];
            
            // 좌석 조회 (동시성 제어를 위해 PESSIMISTIC_LOCK 사용)
            Optional<Seat> seatOpt = seatRepository.findByConcertIdAndRowNumberAndSeatNumber(
                request.concertId(), rowNumber, seatNumber);
            
            if (seatOpt.isEmpty()) {
                throw new BusinessException(ErrorCode.SEAT_NOT_FOUND);
            }
            
            Seat seat = seatOpt.get();
            
            // 좌석 상태 체크
            if (!seat.isAvailable()) {
                throw new BusinessException(ErrorCode.SEAT_NOT_AVAILABLE);
            }
            
            // 좌석 임시 예약
            seat.reserve(user);
            selectedSeats.add(seat);
            totalPrice += seat.getPrice();
        }
        
        // 콘서트 가용 좌석 수 감소
        concert.decreaseAvailableSeats();
        
        LocalDateTime reservationExpiryTime = LocalDateTime.now().plusMinutes(10);
        
        return SeatDto.SeatSelectionResponse.from(selectedSeats, totalPrice, reservationExpiryTime);
    }
    
    /**
     * 만료된 예약 좌석 해제 (스케줄러)
     */
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    public void releaseExpiredReservations() {
        log.info("만료된 예약 좌석 해제 작업 시작");
        
        int releasedCount = seatRepository.releaseExpiredReservations(
            Seat.SeatStatus.RESERVED, 
            Seat.SeatStatus.AVAILABLE, 
            LocalDateTime.now()
        );
        
        if (releasedCount > 0) {
            log.info("{}개의 만료된 예약 좌석이 해제되었습니다.", releasedCount);
        }
    }
    
    /**
     * 콘서트 검색
     */
    public Page<ConcertDto.Response> searchConcerts(String keyword, Pageable pageable) {
        Page<Concert> concertPage = concertRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        return concertPage.map(ConcertDto.Response::from);
    }
    
    /**
     * 아티스트별 콘서트 조회
     */
    public Page<ConcertDto.Response> getConcertsByArtist(String artist, Pageable pageable) {
        Page<Concert> concertPage = concertRepository.findByArtistContainingIgnoreCase(artist, pageable);
        return concertPage.map(ConcertDto.Response::from);
    }
    
    // Private helper methods
    private Concert findConcertById(Long concertId) {
        return concertRepository.findById(concertId)
            .orElseThrow(() -> new BusinessException(ErrorCode.CONCERT_NOT_FOUND));
    }
    
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
    
    private void createSeatsForConcert(Concert concert) {
        for (SeatGrade seatGrade : concert.getSeatGrades()) {
            for (int i = 1; i <= seatGrade.getTotalSeats(); i++) {
                Seat seat = Seat.builder()
                    .concert(concert)
                    .seatGrade(seatGrade)
                    .rowNumber("ROW" + (i / 20 + 1)) // 20개씩 한 줄
                    .seatNumber(String.valueOf(i % 20 == 0 ? 20 : i % 20))
                    .status(Seat.SeatStatus.AVAILABLE)
                    .price(seatGrade.getPrice())
                    .build();
                
                concert.addSeat(seat);
            }
        }
    }
} 