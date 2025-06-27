package com.wiseai.domain.config;

// @Slf4j
// @Configuration
// @RequiredArgsConstructor
// @Profile("!prod") // 프로덕션 환경에서는 실행하지 않음
// public class DataLoader {
//
//     private final ConcertService concertService;
//
//     @Bean
//     public CommandLineRunner loadData() {
//         return args -> {
//             log.info("테스트 데이터 로딩 시작...");
//
//             // 테스트 콘서트 데이터 생성
//             createTestConcerts();
//
//             log.info("테스트 데이터 로딩 완료!");
//         };
//     }
//
//     private void createTestConcerts() {
//         // 1. BTS 콘서트
//         ConcertDto.Request btsConcert = ConcertDto.Request.builder()
//             .title("BTS WORLD TOUR 'MAP OF THE SOUL'")
//             .description("BTS의 월드 투어 콘서트입니다. 전 세계 팬들과 함께하는 특별한 공연!")
//             .concertDate(LocalDateTime.now().plusMonths(2).withHour(19).withMinute(0))
//             .venue("올림픽공원 체조경기장")
//             .artist("BTS")
//             .bookingOpenTime(LocalDateTime.now().plusDays(7))
//             .bookingCloseTime(LocalDateTime.now().plusMonths(1).plusDays(7))
//             .totalSeats(15000)
//             .seatGrades(List.of(
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("VIP")
//                     .price(150000)
//                     .totalSeats(3000)
//                     .description("무대와 가장 가까운 VIP석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("R석")
//                     .price(120000)
//                     .totalSeats(5000)
//                     .description("무대 앞쪽 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("S석")
//                     .price(90000)
//                     .totalSeats(4000)
//                     .description("일반 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("A석")
//                     .price(60000)
//                     .totalSeats(3000)
//                     .description("일반석")
//                     .build()
//             ))
//             .build();
//
//         // 2. IU 콘서트
//         ConcertDto.Request iuConcert = ConcertDto.Request.builder()
//             .title("IU 'The Golden Hour' Concert")
//             .description("IU의 새로운 앨범 발매 기념 콘서트입니다.")
//             .concertDate(LocalDateTime.now().plusMonths(1).withHour(20).withMinute(0))
//             .venue("잠실실내체육관")
//             .artist("IU")
//             .bookingOpenTime(LocalDateTime.now().plusDays(3))
//             .bookingCloseTime(LocalDateTime.now().plusDays(30))
//             .totalSeats(12000)
//             .seatGrades(List.of(
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("VIP")
//                     .price(130000)
//                     .totalSeats(2000)
//                     .description("무대와 가장 가까운 VIP석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("R석")
//                     .price(100000)
//                     .totalSeats(4000)
//                     .description("무대 앞쪽 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("S석")
//                     .price(80000)
//                     .totalSeats(3000)
//                     .description("일반 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("A석")
//                     .price(50000)
//                     .totalSeats(3000)
//                     .description("일반석")
//                     .build()
//             ))
//             .build();
//
//         // 3. 예매 오픈된 콘서트
//         ConcertDto.Request openConcert = ConcertDto.Request.builder()
//             .title("BLACKPINK 'BORN PINK' WORLD TOUR")
//             .description("BLACKPINK의 월드 투어 콘서트입니다.")
//             .concertDate(LocalDateTime.now().plusDays(15).withHour(19).withMinute(30))
//             .venue("고척스카이돔")
//             .artist("BLACKPINK")
//             .bookingOpenTime(LocalDateTime.now().minusDays(1)) // 이미 오픈됨
//             .bookingCloseTime(LocalDateTime.now().plusDays(10))
//             .totalSeats(20000)
//             .seatGrades(List.of(
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("VIP")
//                     .price(180000)
//                     .totalSeats(4000)
//                     .description("무대와 가장 가까운 VIP석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("R석")
//                     .price(140000)
//                     .totalSeats(6000)
//                     .description("무대 앞쪽 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("S석")
//                     .price(110000)
//                     .totalSeats(5000)
//                     .description("일반 우선석")
//                     .build(),
//                 ConcertDto.SeatGradeRequest.builder()
//                     .gradeName("A석")
//                     .price(70000)
//                     .totalSeats(5000)
//                     .description("일반석")
//                     .build()
//             ))
//             .build();
//
//         try {
//             concertService.createConcert(btsConcert);
//             log.info("BTS 콘서트 데이터 생성 완료");
//
//             concertService.createConcert(iuConcert);
//             log.info("IU 콘서트 데이터 생성 완료");
//
//             concertService.createConcert(openConcert);
//             log.info("BLACKPINK 콘서트 데이터 생성 완료");
//
//         } catch (Exception e) {
//             log.warn("테스트 데이터 생성 중 오류 발생: {}", e.getMessage());
//         }
//     }
// }