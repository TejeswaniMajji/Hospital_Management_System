# Project Overview (Simple)

This is a Hospital Management System built with Spring Boot.

## What This Project Does
- Manages patients, doctors, staff, appointments, inventory, suppliers, and prescriptions.
- Provides both web pages (Thymeleaf) and API endpoints (REST controllers).
- Uses authentication and role-based authorization for secure access.

## How It Is Organized
- `src/main/java/com/hospital/controller`: handles web/API requests.
- `src/main/java/com/hospital/service`: business logic.
- `src/main/java/com/hospital/repository`: database access.
- `src/main/java/com/hospital/model`: entity classes.
- `src/main/java/com/hospital/config`: app/security configuration.
- `src/main/resources/templates`: HTML pages.
- `src/main/resources/static`: CSS/JS/assets.
- `src/main/resources/application.properties`: runtime configuration.

## Technology Stack
- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA + Hibernate
- Thymeleaf
- H2 (default development database)
- Maven

## Current Run Behavior
- Runs on `http://localhost:8080/hospital/`
- Loads sample data automatically on startup.
- Uses in-memory H2 database by default for quick local testing.

## What Was Recently Updated
- Security vulnerability fixes were applied at dependency level.
- Spring Boot baseline was upgraded.
- Project run flow was validated successfully.

## Recommended Next Step for a New Developer
1. Read `RUN_PROJECT.md`.
2. Start the app locally.
3. Explore controllers in `src/main/java/com/hospital/controller`.
4. Review `FUTURE_ENHANCEMENTS.md` for roadmap.
