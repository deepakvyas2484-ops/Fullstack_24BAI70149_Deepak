# Experiment 8.1 – KafkaPulse

## Ports (distinct from Exp 7.1 and 7.2)
- Frontend: http://localhost:3002
- Backend: http://localhost:8300
- Kafka: localhost:9092
- Main topic: `scheduled-posts`; DLQ topic: `scheduled-posts-dlq`
- Consumer group: `scheduled-posts-group`
- H2 file: `backend/data/kafka-reliability-db`

## Requirements
Java 21+, Maven 3.9+, Node.js/npm, Docker Engine with Docker Compose.

## 1. Start Kafka
From this folder run `docker compose up -d`.
Both experiments intentionally share one Kafka broker on port 9092 but use isolated topic names and consumer groups. Start the broker only once. If it is already running for the other experiment, reuse it.

## 2. Start backend (Terminal 1)
`cd backend && mvn spring-boot:run`

## 3. Start frontend (Terminal 2)
`cd frontend && npm install && npm run dev`
Open http://localhost:3002

## Test
- Set failures to 0: event should become PROCESSED.
- Set failures to 2: transient retries should occur, then PROCESSED.
- Set failures to 6: retries exhaust and event should be routed to DLQ.
- Submit an existing event ID again: API returns HTTP 409.
- Dashboard refreshes every 2.5 seconds and derives metrics from persisted event records.

Retry policy: initial 1 second, multiplier 2, maximum 4 seconds, 3 retries.
API: POST /api/events, GET /api/events, GET /api/events/{eventId}.
H2 console: http://localhost:8300/h2-console ; JDBC URL `jdbc:h2:file:./data/kafka-reliability-db`, user `sa`, blank password.

Stop frontend/backend with Ctrl+C. Stop Kafka with `docker compose down` only after you have finished both experiments.
