# Experiment 9.2 — Continuous Deployment and Canary Release
Backend: http://localhost:8401 | Frontend: http://localhost:3005

Backend terminal: `cd backend && mvn clean package && mvn spring-boot:run`
For Docker: `docker build -t exp92-cd:1.0 .` then `docker run --rm -p 8401:8401 exp92-cd:1.0` (run from backend).
Frontend terminal: `cd frontend && npm install && npm run dev`; open http://localhost:3005.
GitHub Actions workflow `.github/workflows/cd.yml` builds/tests and publishes to Docker Hub on push to main. Configure repository secrets `DOCKERHUB_USERNAME` and `DOCKERHUB_TOKEN`.
Canary traffic control is a local simulation, not a production reverse proxy or multi-instance deployment.
