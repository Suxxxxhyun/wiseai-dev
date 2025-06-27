-- 테스트 사용자 데이터 삽입 (비밀번호는 BCrypt로 암호화된 "password123")
INSERT INTO users (email, password, name, role, created_date, last_modified_date) VALUES
('admin@concertmania.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '관리자', 'ADMIN', NOW(), NOW()),
('user1@concertmania.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '일반사용자1', 'MEMBER', NOW(), NOW()),
('user2@concertmania.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '일반사용자2', 'MEMBER', NOW(), NOW()),
('user3@concertmania.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', '일반사용자3', 'MEMBER', NOW(), NOW());

-- 테스트 콘서트 데이터 삽입
INSERT INTO concerts (title, description, concert_date, venue, artist, booking_open_time, booking_close_time, status, total_seats, available_seats, created_date, last_modified_date) VALUES
('BTS WORLD TOUR ''MAP OF THE SOUL''', 'BTS의 월드 투어 콘서트입니다. 전 세계 팬들과 함께하는 특별한 공연!', '2024-06-15 19:00:00', '올림픽공원 체조경기장', 'BTS', '2024-05-01 10:00:00', '2024-06-10 23:59:59', 'BOOKING_OPEN', 15000, 15000, NOW(), NOW()),
('IU ''The Golden Hour'' Concert', 'IU의 새로운 앨범 발매 기념 콘서트입니다.', '2024-07-20 20:00:00', '잠실실내체육관', 'IU', '2024-06-01 10:00:00', '2024-07-15 23:59:59', 'UPCOMING', 12000, 12000, NOW(), NOW()),
('BLACKPINK ''BORN PINK'' WORLD TOUR', 'BLACKPINK의 월드 투어 콘서트입니다.', '2024-08-10 19:30:00', '고척스카이돔', 'BLACKPINK', '2024-07-01 10:00:00', '2024-08-05 23:59:59', 'UPCOMING', 20000, 20000, NOW(), NOW());

-- BTS 콘서트 좌석 등급 데이터
INSERT INTO seat_grades (concert_id, grade_name, price, total_seats, available_seats, description, created_date, last_modified_date) VALUES
(1, 'VIP', 150000, 3000, 3000, '무대와 가장 가까운 VIP석', NOW(), NOW()),
(1, 'R석', 120000, 5000, 5000, '무대 앞쪽 우선석', NOW(), NOW()),
(1, 'S석', 90000, 4000, 4000, '일반 우선석', NOW(), NOW()),
(1, 'A석', 60000, 3000, 3000, '일반석', NOW(), NOW());

-- IU 콘서트 좌석 등급 데이터
INSERT INTO seat_grades (concert_id, grade_name, price, total_seats, available_seats, description, created_date, last_modified_date) VALUES
(2, 'VIP', 130000, 2000, 2000, '무대와 가장 가까운 VIP석', NOW(), NOW()),
(2, 'R석', 100000, 4000, 4000, '무대 앞쪽 우선석', NOW(), NOW()),
(2, 'S석', 80000, 3000, 3000, '일반 우선석', NOW(), NOW()),
(2, 'A석', 50000, 3000, 3000, '일반석', NOW(), NOW());

-- BLACKPINK 콘서트 좌석 등급 데이터
INSERT INTO seat_grades (concert_id, grade_name, price, total_seats, available_seats, description, created_date, last_modified_date) VALUES
(3, 'VIP', 180000, 4000, 4000, '무대와 가장 가까운 VIP석', NOW(), NOW()),
(3, 'R석', 140000, 6000, 6000, '무대 앞쪽 우선석', NOW(), NOW()),
(3, 'S석', 110000, 5000, 5000, '일반 우선석', NOW(), NOW()),
(3, 'A석', 70000, 5000, 5000, '일반석', NOW(), NOW());

-- BTS 콘서트 좌석 데이터 (VIP석 일부만 생성)
INSERT INTO seats (concert_id, seat_grade_id, row_num, seat_number, status, price, created_date, last_modified_date) VALUES
-- VIP석 (첫 번째 행, 1-20번)
(1, 1, 'ROW1', '1', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '2', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '3', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '4', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '5', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '6', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '7', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '8', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '9', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '10', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '11', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '12', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '13', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '14', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '15', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '16', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '17', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '18', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '19', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW1', '20', 'AVAILABLE', 150000, NOW(), NOW()),
-- VIP석 (두 번째 행, 1-20번)
(1, 1, 'ROW2', '1', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '2', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '3', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '4', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '5', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '6', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '7', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '8', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '9', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '10', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '11', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '12', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '13', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '14', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '15', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '16', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '17', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '18', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '19', 'AVAILABLE', 150000, NOW(), NOW()),
(1, 1, 'ROW2', '20', 'AVAILABLE', 150000, NOW(), NOW());

-- R석 데이터 (일부만 생성)
INSERT INTO seats (concert_id, seat_grade_id, row_num, seat_number, status, price, created_date, last_modified_date) VALUES
-- R석 (첫 번째 행, 1-20번)
(1, 2, 'ROW3', '1', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '2', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '3', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '4', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '5', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '6', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '7', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '8', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '9', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '10', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '11', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '12', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '13', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '14', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '15', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '16', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '17', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '18', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '19', 'AVAILABLE', 120000, NOW(), NOW()),
(1, 2, 'ROW3', '20', 'AVAILABLE', 120000, NOW(), NOW()); 