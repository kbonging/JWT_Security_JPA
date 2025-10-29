# JWT_Security_JPA (JPA 버전)

**작성일:** 2025-10-29  

Spring Boot + JPA + Spring Security + JWT를 사용한 **보안 템플릿 프로젝트(JPA 버전)**입니다.  
기존 MyBatis 버전에서 **JPA로 전환**하며 코드 구조를 단순화하고, **템플릿처럼 재사용 가능하도록** 설계했습니다.

---

## 🔹 프로젝트 특징

- **JWT 기반 인증**
  - 로그인 시 DB 정보를 확인 후 JWT 토큰 발급
  - 발급된 토큰을 서버로 보내 유효성 확인
  - SecurityContext에 사용자 정보 저장
- **권한 설정**
  - `ROLE_USER`, `ROLE_ADMIN` 지원
  - 권한별 접근 제한 예제 구현
- **회원 관리**
  - 회원 가입 기능 미구현
  - 비밀번호는 **BCrypt 암호화 후 저장 예정** (미구현)
  - 로그인 기능 구현
  - 회원 정보 조회 기능 포함
- **JPA 기반**
  - 엔티티(Entity) + Spring Data JPA Repository 사용
  - MyBatis보다 간결하고 유지보수 용이

---

## 🔹 기본 DB 설정 및 샘플 데이터 (JPA 기준)

```sql
-- 데이터베이스 생성
CREATE DATABASE dbName;
USE dbName;

-- 회원 테이블
CREATE TABLE tb_member (
    member_idx BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '회원 고유번호',
    member_id VARCHAR(60) NOT NULL UNIQUE COMMENT '로그인 아이디',
    member_pw VARCHAR(255) NOT NULL COMMENT '비밀번호 (암호화 저장 예정)',
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE COMMENT '계정 활성 여부',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='회원 기본 정보';

-- 권한 테이블
CREATE TABLE tb_member_auth (
    auth_idx BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '권한 고유번호',
    member_idx BIGINT NOT NULL COMMENT '회원 고유번호 (FK)',
    auth VARCHAR(50) NOT NULL COMMENT '권한명 (ex: ROLE_USER, ROLE_ADMIN)',
    CONSTRAINT fk_member_auth_member FOREIGN KEY (member_idx)
        REFERENCES tb_member(member_idx)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='회원 권한 정보';

-- 샘플 사용자
INSERT INTO tb_member(member_id, member_pw)
VALUES('test1', '$2a$10$9gyJQgOXFeLqigSmLrEzWOj5k86ar.2qyEVtV9RQzOIN4oRECSLYe');

INSERT INTO tb_member_auth(member_idx, auth)
VALUES(1, 'ROLE_USER');
