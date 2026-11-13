# Experiment 7.2 – AES Encryption and Secure Token Management

## Requirements
- Java JDK 21 (JDK 17+ is sufficient)
- Maven 3.9+
- Browser

## Run on Ubuntu
```bash
cd ~/Downloads/exp7_2_secure_tokens
mvn spring-boot:run
```
Open http://localhost:8081

Stop with Ctrl+C.

## Features
- AES-256-GCM encryption with a fresh 96-bit nonce for each encryption.
- Stores only Base64-encoded nonce + ciphertext in an H2 database.
- Decrypts a saved record by database ID.
- Generates signed JWT access tokens (15 minutes) and refresh tokens (7 days).
- Validates refresh token signature, expiration, and token type before issuing a new access token.
- Bright responsive browser UI.
- H2 console: http://localhost:8081/h2-console
  - JDBC URL: jdbc:h2:mem:exp72db
  - Username: sa
  - Password: (blank)

## API endpoints
- POST /api/crypto/encrypt  body: {"plaintext":"secret text"}
- GET /api/crypto/decrypt/{id}
- GET /api/crypto/records
- POST /api/tokens/generate body: {"username":"Deepak"}
- POST /api/tokens/refresh body: {"refreshToken":"<token>"}

## Important
This is a classroom demonstration. The H2 database is in-memory and is reset on restart.
Demo keys are in application.properties so the project runs immediately. Do not use these keys or this token design as-is in production. Keep real keys in a secret manager/environment variables and add refresh-token rotation/revocation and authentication controls for production.
