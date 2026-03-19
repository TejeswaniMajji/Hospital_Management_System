# Hospital Management System - Complete Setup Guide

## 📋 Table of Contents
1. [System Requirements](#system-requirements)
2. [Installation Steps](#installation-steps)
3. [Configuration](#configuration)
4. [Running the Application](#running-the-application)
5. [Testing the Application](#testing-the-application)
6. [Troubleshooting](#troubleshooting)

## 🖥️ System Requirements

### Hardware
- **RAM**: Minimum 4GB, Recommended 8GB
- **Disk Space**: Minimum 2GB
- **Processor**: Intel Core i5 or equivalent

### Software
| Component | Version | Download |
|-----------|---------|----------|
| Java Development Kit (JDK) | 17+ | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/) |
| MySQL Server | 8.0+ | [MySQL Community](https://dev.mysql.com/downloads/mysql/) |
| Apache Maven | 3.6+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| Git | Latest | [Git](https://git-scm.com/downloads) (Optional) |
| IDE | Latest | [IntelliJ IDEA](https://www.jetbrains.com/idea/), [Eclipse](https://www.eclipse.org/), or [VS Code](https://code.visualstudio.com/) |

## 📦 Installation Steps

### Step 1: Install Java Development Kit (JDK)

#### On Windows:
1. Download JDK 17+ from [Oracle](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer and follow the wizard
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties → Advanced system settings
   - Click "Environment Variables"
   - Add new system variable: `JAVA_HOME` = `C:\Program Files\Java\jdk-17`
4. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### On macOS:
```bash
# Using Homebrew
brew install openjdk@17
# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
echo $JAVA_HOME
```

#### On Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
```

### Step 2: Install MySQL Server

#### On Windows:
1. Download [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
2. Run the installer
3. Choose setup type (Recommended: Developer Default)
4. Configure MySQL Server as Windows Service
5. Set root password
6. Complete the installation

#### On macOS:
```bash
brew install mysql
brew services start mysql
mysql -u root -p
```

#### On Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

### Step 3: Install Apache Maven

#### On Windows:
1. Download [Apache Maven](https://maven.apache.org/download.cgi)
2. Extract to `C:\Program Files\apache-maven-3.x.x`
3. Set MAVEN_HOME environment variable
4. Add to PATH: `%MAVEN_HOME%\bin`
5. Verify: `mvn -version`

#### On macOS:
```bash
brew install maven
mvn -version
```

#### On Linux:
```bash
sudo apt update
sudo apt install maven
mvn -version
```

### Step 4: Clone or Extract Project
```bash
cd ~/projects
# or navigate to your desired directory
```

## ⚙️ Configuration

### Step 1: Create MySQL Database
```bash
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE hospital_db;
USE hospital_db;

# Exit MySQL
EXIT;
```

### Step 2: Configure Database Connection
Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/hospital

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=your_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

# Logging
logging.level.root=INFO
logging.level.com.hospital=DEBUG
```

### Step 3: Update Maven Dependencies (Optional)
The pom.xml already includes all necessary dependencies. Maven will download them automatically during build.

## 🚀 Running the Application

### Method 1: Using Maven
```bash
# Navigate to project directory
cd Hospital-Management-System

# Clean and build
mvn clean install

# Run the application
mvn spring-boot:run
```

### Method 2: Using IDE (IntelliJ IDEA)
1. Open the project folder
2. Wait for Maven to download dependencies
3. Right-click on `HospitalManagementApplication.java`
4. Select "Run HospitalManagementApplication"

### Method 3: Using IDE (Eclipse)
1. Import project: File → Import → Existing Maven Projects
2. Select project folder
3. Right-click project → Run As → Spring Boot App

### Method 4: Build and Run JAR
```bash
# Create executable JAR
mvn clean package -DskipTests

# Run JAR file
java -jar target/hospital-management-system-1.0.0.war
```

## ✅ Testing the Application

### 1. Access the Application
- **URL**: http://localhost:8080/hospital
- You should see the Hospital Management System dashboard

### 2. Load Sample Data (Optional)
```bash
# Connect to MySQL
mysql -u root -p hospital_db < sample_data.sql
```

### 3. Test Patient Management
1. Click "Patients" → "Add New Patient"
2. Fill in patient details
3. Click "Save Patient"
4. Verify patient appears in the list
5. Test Edit and Delete functions

### 4. Test Doctor Management
1. Navigate to "Doctors" → "Add New Doctor"
2. Enter doctor information
3. Verify doctor is added successfully

### 5. Test Appointment Scheduling
1. Go to "Appointments" → "Schedule New Appointment"
2. Select patient and doctor
3. Set appointment date and time
4. Verify appointment appears in the list

### 6. Test Search Functionality
1. Try searching by different criteria
2. Verify correct results are returned

## 🔍 Troubleshooting

### Issue 1: "Port 8080 already in use"
```bash
# Change port in application.properties
server.port=8081

# Or kill process on port 8080
# On Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# On macOS/Linux:
lsof -i :8080
kill -9 <PID>
```

### Issue 2: "Connection refused" - MySQL not running
```bash
# On Windows - Check services
# Services → MySQL80 → Start

# On macOS:
brew services start mysql

# On Linux:
sudo systemctl start mysql
```

### Issue 3: "Access denied for user 'root'@'localhost'"
- Verify MySQL username and password in application.properties
- Reset MySQL root password if forgotten:
```bash
# On Windows
mysql -u root --skip-password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'your_new_password';
FLUSH PRIVILEGES;
EXIT;
```

### Issue 4: "Tables not created" in MySQL
1. Check if Hibernate is set to "create" or "update"
2. Ensure `spring.jpa.hibernate.ddl-auto=update` in properties
3. Check console for errors
4. Manually create tables using sample_data.sql

### Issue 5: JSP pages showing blank or errors
- Verify JSP files exist in `src/main/webapp/WEB-INF/jsp/`
- Check server logs for 404 errors
- Ensure controller paths match JSP view names

### Issue 6: CSS/JS not loading
- Check browser console for 404 errors
- Verify files are in correct directories:
  - CSS: `src/main/webapp/css/`
  - JS: `src/main/webapp/js/`
- Check file paths in JSP files

## 📊 Useful MySQL Commands

```sql
-- Check databases
SHOW DATABASES;

-- Select database
USE hospital_db;

-- Check tables
SHOW TABLES;

-- View table structure
DESCRIBE patients;

-- Check data
SELECT COUNT(*) FROM patients;
SELECT * FROM patients;

-- Backup database
mysqldump -u root -p hospital_db > backup.sql

-- Restore database
mysql -u root -p hospital_db < backup.sql

-- Reset tables (delete all data)
TRUNCATE TABLE appointments;
TRUNCATE TABLE patients;
TRUNCATE TABLE doctors;
TRUNCATE TABLE hospital_staff;
```

## 📱 Application URLs

| Feature | URL |
|---------|-----|
| Dashboard | http://localhost:8080/hospital/ |
| Patient List | http://localhost:8080/hospital/patient/list |
| Add Patient | http://localhost:8080/hospital/patient/add |
| Doctor List | http://localhost:8080/hospital/doctor/list |
| Add Doctor | http://localhost:8080/hospital/doctor/add |
| Appointments | http://localhost:8080/hospital/appointment/list |
| Schedule Appointment | http://localhost:8080/hospital/appointment/add |

## 🛠️ Development Tips

### Enable Debug Mode
In `application.properties`:
```properties
logging.level.root=DEBUG
logging.level.com.hospital=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Hot Reload Changes
The application includes Spring Boot DevTools for hot reload:
```
Just save file → automatically reloads changes
```

### Database Debugging
Check active connections:
```sql
SHOW PROCESSLIST;
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Hibernate Documentation](https://hibernate.org/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Java 17 Documentation](https://docs.oracle.com/javase/17/)

## 🎓 Next Steps

1. **Explore the codebase** - Understand the project structure
2. **Add authentication** - Implement Spring Security
3. **Enhance UI** - Add Bootstrap or Material Design
4. **Add testing** - Write JUnit and integration tests
5. **Deploy** - Deploy to cloud (AWS, Azure, Google Cloud)

---

**Congratulations! Your Hospital Management System is ready to use! 🎉**

For issues or questions, refer to the main README.md file.
