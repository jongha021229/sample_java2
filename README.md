# sample_java2

Spring Boot 기반 Java 샘플 백엔드 프로젝트 (Movie API)

## 프로젝트 요약

- 목적: Spring Boot 기반의 간단한 REST API 샘플
- 도메인: Movie CRUD + 검색 + Health Check
- 현재 상태: 테스트 코드 포함

## 기술 스택/버전

- Java 17 toolchain
- Spring Boot 3.4.2
- Gradle Wrapper 8.7 설정
- Validation: `jakarta.validation`

## 실행

```bash
./gradlew bootRun
```

## 테스트

```bash
./gradlew test
```

## 빌드

```bash
./gradlew build
```

## 기능 명세(API)

### GET /health

응답:

```json
{ "status": "ok" }
```

### POST /movies

요청 예시:

```json
{ "title": "Inception", "rating": 9.0, "synopsis": "Dream within a dream" }
```

검증:

- title: 공백 불가
- rating: 양수 필수

성공:

- 201 Created + 생성된 movie(id 포함)

### GET /movies

전체 목록 반환

### GET /movies/{id}

- 존재 시 200 + movie
- 미존재 시 404 + `{ "error": "not found" }`

### DELETE /movies/{id}

- 존재 시 200 + `{ "status": "deleted" }`
- 미존재 시 404 + `{ "error": "not found" }`

### GET /movies/search?q=키워드

title 기준 부분일치 검색(대소문자 무시)

## 데이터 저장 방식

- DB 없음, 메모리(Map) 저장
- 앱 재시작 시 데이터 초기화