-- 기존 테이블 삭제 (외래키 제약조건 고려)
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS seat_grades;
DROP TABLE IF EXISTS concerts;
DROP TABLE IF EXISTS users;

-- 사용자 테이블
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(20) NOT NULL,
    role VARCHAR(10) NOT NULL,
    refresh_token VARCHAR(250),
    token_expiration_time DATETIME,
    created_date DATETIME,
    last_modified_date DATETIME
);

-- 콘서트 테이블
CREATE TABLE concerts (
    concert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(500) NOT NULL,
    concert_date DATETIME NOT NULL,
    venue VARCHAR(200) NOT NULL,
    artist VARCHAR(100) NOT NULL,
    booking_open_time DATETIME NOT NULL,
    booking_close_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    created_date DATETIME,
    last_modified_date DATETIME
);

-- 좌석 등급 테이블
CREATE TABLE seat_grades (
    seat_grade_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    concert_id BIGINT NOT NULL,
    grade_name VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    description VARCHAR(200),
    created_date DATETIME,
    last_modified_date DATETIME,
    FOREIGN KEY (concert_id) REFERENCES concerts(concert_id) ON DELETE CASCADE
);

-- 좌석 테이블
CREATE TABLE seats (
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    concert_id BIGINT NOT NULL,
    seat_grade_id BIGINT NOT NULL,
    row_num VARCHAR(10) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    price INT NOT NULL,
    reserved_by BIGINT,
    reserved_at DATETIME,
    reservation_expiry_time DATETIME,
    created_date DATETIME,
    last_modified_date DATETIME,
    FOREIGN KEY (concert_id) REFERENCES concerts(concert_id) ON DELETE CASCADE,
    FOREIGN KEY (seat_grade_id) REFERENCES seat_grades(seat_grade_id) ON DELETE CASCADE,
    FOREIGN KEY (reserved_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- 인덱스 생성
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_concerts_artist ON concerts(artist);
CREATE INDEX idx_concerts_status ON concerts(status);
CREATE INDEX idx_concerts_booking_time ON concerts(booking_open_time, booking_close_time);
CREATE INDEX idx_seats_concert_status ON seats(concert_id, status);
CREATE INDEX idx_seats_reserved_by ON seats(reserved_by);
CREATE INDEX idx_seats_reservation_expiry ON seats(reservation_expiry_time); 