# RideRent — Vehicle Rental Management System

Stack: Java 21, Spring Boot 3.5.7, Spring Data JPA, H2, React and Vite.

## Ports
- React frontend: http://localhost:3006
- Spring Boot API: http://localhost:8500
- H2 console: http://localhost:8500/h2-console

## Run backend
```bash
cd backend
mvn clean spring-boot:run
```

## Run frontend in a second terminal
```bash
cd frontend
npm install
npm run dev
```

## Implemented
- Vehicle catalog and admin create/update/delete
- Availability checks for selected date ranges
- Booking creation with overlapping-booking prevention
- Inclusive daily price calculation
- Customer profiles and rental history API
- Booking list and dashboard summary
- Booking cancellation API: PATCH /api/bookings/{id}/cancel

Demo customer is seeded automatically: Demo Customer, demo@riderent.in.

## Limitations
This is an academic local starter, not production-ready. Authentication/authorization, payments, license verification, image upload, and transactional locking against simultaneous booking races are not included. Do not expose the admin API publicly without adding security.
