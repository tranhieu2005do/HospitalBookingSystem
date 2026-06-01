# Hospital Appointment Booking System - API Gateway

This is the API Gateway service built with Spring Boot 3.x, Spring Cloud Gateway, and Spring WebFlux. It acts as the single entry point for all client requests.

## Core Responsibilities
- **Authentication**: Verifies Firebase ID Tokens using the Firebase Admin SDK asynchronously.
- **Context Propagation**: Extracts `uid`, `email`, and `role` from the token and injects them as HTTP headers (`X-User-Id`, `X-User-Email`, `X-User-Role`) to downstream services.
- **Routing**: Routes traffic to Booking, Payment, Doctor, and Schedule services based on paths.
- **Cross-Cutting Concerns**: 
  - Generates a Correlation ID (`X-Request-Id`) for request tracing.
  - Rate limiting using Redis.
  - Centralized request/response logging.

## Prerequisites
- **Java 17+**
- **Maven**
- **Redis** (running locally on port 6379 for rate limiting)
- **Firebase Service Account Key**: Place your `firebase-service-account.json` in `src/main/resources/` or update the `firebase.service-account.path` property in `application.yml`.

## How to Run

1. Start Redis (e.g., using Docker):
   ```bash
   docker run --name redis -p 6379:6379 -d redis
   ```

2. Add your Firebase service account JSON key to `src/main/resources/firebase-service-account.json`.

3. Run the application:
   ```bash
   mvn clean spring-boot:run
   ```

## Testing

To test an authenticated request:
```bash
curl -X GET http://localhost:8080/api/doctors/1 \
  -H "Authorization: Bearer <YOUR_FIREBASE_ID_TOKEN>"
```

To test without a token (Expect 401 Unauthorized):
```bash
curl -X GET http://localhost:8080/api/doctors/1
```
