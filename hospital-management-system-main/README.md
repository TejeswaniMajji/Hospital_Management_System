# Hospital Database Management System

A Spring Boot based hospital management application for handling patients, doctors, staff, appointments, inventory, prescriptions, and related operations.

## Project Status
- Status: Running locally and actively maintained.
- Last updated: March 12, 2026.
- Security: High-severity CVE remediation has been applied.

## What Has Been Completed
*   **Core Functional Modules**: Comprehensive management system for Patients, Doctors, Staff, Appointments, Prescriptions, Inventory, and Suppliers.
*   **Security Implementation**: Robust authentication and Role-Based Access Control (RBAC) using Spring Security with BCrypt password hashing.
*   **Dual Interface Layer**: Fully functional Web UI using Thymeleaf templates alongside a suite of REST API endpoints for programmatic access.
*   **Data Persistence**: Optimized Spring Data JPA and Hibernate integration, utilizing an H2 in-memory database for rapid local development.
*   **Security & Maintenance Hardening**:
    *   Proactive remediation of high-severity dependency vulnerabilities.
    *   Updated `com.mysql:mysql-connector-j` to `9.1.0` and upgraded the Spring Boot baseline to `3.4.3`.
    *   Validated end-to-end run flow in local environments.
*   **Project Optimization**: Completed a major repository cleanup (March 2026), removing 150MB+ of build artifacts and temporary files (98.5% size reduction) and archiving 78 historical documents for better maintainability.
*   **Enhanced Documentation**: Added clear onboarding summaries, skill maps, and a future enhancement roadmap.

## Tech Stack
- Java 17+
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA + Hibernate
- Thymeleaf
- H2 Database (default local profile)
- Maven

## Project Structure
```text
src/main/java/com/hospital/
  config/
  controller/
  model/
  repository/
  service/
src/main/resources/
  application.properties
  templates/
  static/
```

## Quick Run
1. Ensure Java and Maven are installed.
2. From project root:
```bash
mvn clean install
mvn spring-boot:run
```
3. Open:
- `http://localhost:8080/hospital/`
- `http://localhost:8080/hospital/auth/login`

For full platform-specific instructions, see `RUN_PROJECT.md`.

## Configuration
Main config file: `src/main/resources/application.properties`
- Port: `8080`
- Context path: `/hospital`
- Database URL: `jdbc:h2:mem:hospital_db`

## Added Documentation Files
- `FUTURE_ENHANCEMENTS.md`: roadmap and improvement backlog.
- `RUN_PROJECT.md`: complete run instructions and troubleshooting.
- `SKILLS_USED_IN_PROJECT.md`: technologies and engineering skills used.
- `PROJECT_OVERVIEW_SIMPLE.md`: easy-to-understand summary for quick onboarding.

## Notes
- The repository includes many archived documents under `archived_md_files/`.
- The files listed above are the current concise guides for day-to-day development.

## Cloud Deployment (AWS)

This application is deployed on AWS using the following infrastructure:

### Architecture

User Browser
↓
AWS EC2 (Ubuntu Server)
↓
Spring Boot Application
↓
AWS RDS (MySQL Database)

---

# Live Application

Application URL

http://54.159.20.178:8080/hospital

Login page

http://54.159.20.178:8080/hospital/auth/login

---

# End-to-End Deployment Procedure

The following steps describe how the application was deployed to AWS.

---

## 1. Push Code to GitHub

Initialize git repository

```
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/nithineleti/hospital-management-system.git
git push -u origin main
```

---

## 2. Create EC2 Instance

Create an instance using:

* OS: Ubuntu
* Instance Type: t3.micro
* Storage: 8GB

Security Group Rules

| Type       | Port | Purpose                 |
| ---------- | ---- | ----------------------- |
| SSH        | 22   | Server access           |
| HTTP       | 80   | Web access              |
| Custom TCP | 8080 | Spring Boot application |

---

## 3. Connect to EC2 Server

Download key pair and connect using SSH

```
chmod 400 hospital-key.pem

ssh -i hospital-key.pem ubuntu@54.159.20.178
```

---

## 4. Install Required Software

Update server

```
sudo apt update
```

Install Java

```
sudo apt install openjdk-17-jdk -y
```

Verify Java

```
java -version
```

Install Maven

```
sudo apt install maven -y
```

Verify Maven

```
mvn -version
```

Install Git

```
sudo apt install git -y
```

---

## 5. Clone Project on Server

```
git clone https://github.com/nithineleti/hospital-management-system.git

cd hospital-management-system
```

Verify files

```
ls
```

---

## 6. Configure AWS RDS Database

Create a MySQL instance in AWS RDS.

Database configuration example

```
Database Name: hospital_db
Username: admin
Port: 3306
```

Update application configuration

File

```
src/main/resources/application.properties
```

Example configuration

```
spring.datasource.url=jdbc:mysql://RDS_ENDPOINT:3306/hospital_db
spring.datasource.username=admin
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 7. Allow EC2 to Access RDS

Open the RDS security group.

Add inbound rule:

| Type  | Port | Source    |
| ----- | ---- | --------- |
| MySQL | 3306 | 0.0.0.0/0 |

---

## 8. Build Application

Run the Maven build command

```
mvn clean install -DskipTests
```

Expected result

```
BUILD SUCCESS
```

---

## 9. Run Spring Boot Application

Start the application

```
mvn spring-boot:run
```

Application starts on

```
Tomcat started on port(s): 8080
```

---

## 10. Access the Application

Open browser

```
http://54.159.20.178:8080/hospital
```

Dashboard will load.

---

# Database Query Examples

Example queries used for verification.

Create database

```
CREATE DATABASE hospital_db;
```

View tables

```
SHOW TABLES;
```

Check users

```
SELECT user, host FROM mysql.user;
```

Check patient records

```
SELECT * FROM patient;
```

Check doctors

```
SELECT * FROM doctor;
```

---

# Production Improvements

Future enhancements include:

* Nginx reverse proxy
* Docker containerization
* Cloudflare CDN
* CI/CD pipeline using GitHub Actions
* HTTPS with Let's Encrypt

---

# Author

Nithin Neleti

GitHub
https://github.com/nithineleti
