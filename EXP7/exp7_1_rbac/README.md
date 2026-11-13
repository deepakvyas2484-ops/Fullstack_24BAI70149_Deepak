# Experiment 7.1 — JWT Authentication and RBAC

A Spring Boot 3.5 / Java 21 application with Spring Security, JWT, H2, and a bright responsive browser UI.

## Requirements
- JDK 21 (JDK 17+ is sufficient)
- Maven 3.9+
- Browser

## Run on Ubuntu
1. Extract this folder.
2. Open a terminal in `exp7_1_rbac`.
3. Check Java and Maven:
   ```bash
   java -version
   mvn -version
   ```
4. Start the app:
   ```bash
   mvn spring-boot:run
   ```
5. Open `http://localhost:8080`.

If Maven is missing: `sudo apt update && sudo apt install maven -y`.

## Demo accounts
| Role | Username | Password |
|---|---|---|
| ADMIN | Deepak | 24BAI70149 |
| EDITOR | editor | editor123 |
| VIEWER | viewer | viewer123 |

## API endpoints
- `POST /api/auth/login` — public login; returns signed JWT.
- `GET /api/me` — authenticated identity.
- `GET /api/viewer` — ADMIN, EDITOR, VIEWER.
- `GET /api/editor` — ADMIN, EDITOR.
- `GET /api/admin` — ADMIN only.

Protected endpoints require `Authorization: Bearer <token>`.

## H2 database
The demo uses an in-memory H2 database. Users are inserted at startup with BCrypt-hashed passwords. Visit `http://localhost:8080/h2-console` while the app is running; JDBC URL: `jdbc:h2:mem:rbacdb`, username `sa`, blank password.

## Important demonstration notes
- The supplied credentials and signing key are for a classroom demo only. Do not deploy them publicly.
- The UI hides buttons for roles without permission, but the actual security boundary is the backend `@PreAuthorize` check. To demonstrate this, sign in as VIEWER and use a direct API request to `/api/admin`; it should return HTTP 403.
- The JWT is stored only in browser session storage for this demonstration. Use HTTPS and stronger key management in production.
