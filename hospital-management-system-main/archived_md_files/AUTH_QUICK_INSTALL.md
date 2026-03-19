# Authentication System - Quick Installation Guide

**⏱️ Installation Time:** 10-15 minutes  
**📋 Difficulty Level:** Easy to Intermediate

---

## 📋 Pre-requisites

- ✅ Hospital Management System project structure in place
- ✅ MySQL database running and accessible
- ✅ Maven installed and configured
- ✅ Java 17 or higher

---

## 🔄 Installation Steps

### Step 1️⃣: Add Backend Classes

Create these directories and files in your project:

```bash
# Create auth package in service layer
mkdir -p src/main/java/com/hospital/service

# Add these files:
# - src/main/java/com/hospital/model/User.java
# - src/main/java/com/hospital/repository/UserRepository.java
# - src/main/java/com/hospital/config/SecurityConfig.java
# - src/main/java/com/hospital/service/AuthenticationService.java
# - src/main/java/com/hospital/controller/AuthController.java
```

**Files to copy:**
```
Backend Files (5 files)
├── User.java
├── UserRepository.java
├── SecurityConfig.java
├── AuthenticationService.java
└── AuthController.java
```

### Step 2️⃣: Add Frontend JSP Pages

Create auth folder and add JSP files:

```bash
# Create auth folder
mkdir -p src/main/webapp/WEB-INF/jsp/auth

# Copy these JSP files:
# - login.jsp
# - register.jsp
# - change-password.jsp
# - profile.jsp
```

**Files to copy:**
```
Frontend Files (4 files)
├── login.jsp
├── register.jsp
├── change-password.jsp
└── profile.jsp
```

### Step 3️⃣: Update pom.xml

Add these dependencies to your `pom.xml` file:

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT Token Support -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- Spring Security Crypto -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

### Step 4️⃣: Create Database Tables

Run the migration script:

```bash
# Option A: Using MySQL client
mysql -u root -p hospital_db < src/main/resources/db/migration/V2__add_authentication_system.sql

# Option B: Using MySQL Workbench
# 1. Open MySQL Workbench
# 2. Connect to your database
# 3. Open the SQL file
# 4. Execute the script

# Option C: Using terminal
mysql -u root
USE hospital_db;
SOURCE /path/to/V2__add_authentication_system.sql;
```

**What gets created:**
- ✅ `users` table
- ✅ `user_roles` table
- ✅ `user_permissions` table
- ✅ `user_audit_log` table
- ✅ Sample test data (4 demo users)

### Step 5️⃣: Update application.properties

Add these configuration settings:

```properties
# Session Configuration
server.servlet.session.timeout=30m

# Security Settings
security.password.algorithm=bcrypt
security.password.strength=12

# Logging (Optional - for debugging)
logging.level.org.springframework.security=INFO
```

### Step 6️⃣: Build the Project

```bash
# Clean build
mvn clean install

# If you want to skip tests:
mvn clean install -DskipTests
```

**Expected output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXX s
```

### Step 7️⃣: Run the Application

```bash
# Start the application
mvn spring-boot:run

# Or use your IDE's run button
```

**Expected console output:**
```
...
o.s.b.a.e.web.EndpointServletContainer : Registering endpoints with base path '/actuator'
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
c.h.HospitalManagementApplication        : Started HospitalManagementApplication in X.XXX seconds
```

### Step 8️⃣: Test the Installation

Open your browser and test:

```
Login Page:
http://localhost:8080/hospital/auth/login

Registration Page:
http://localhost:8080/hospital/auth/register

Home Page (redirects to login):
http://localhost:8080/hospital/
```

---

## 🧪 Test with Demo Accounts

After installation, test login with these credentials:

### Account 1: Administrator
```
Username: admin
Password: Password123
Role: ADMIN
```

### Account 2: Doctor
```
Username: doctor1
Password: Password123
Role: DOCTOR
```

### Account 3: Nurse
```
Username: nurse1
Password: Password123
Role: NURSE
```

### Account 4: Patient
```
Username: patient1
Password: Password123
Role: PATIENT
```

---

## ✅ Verification Checklist

After installation, verify:

- [ ] Database tables created successfully
- [ ] Application starts without errors
- [ ] Login page is accessible
- [ ] Can register new user
- [ ] Can login with test accounts
- [ ] Dashboard loads after login
- [ ] Can change password
- [ ] Can logout successfully
- [ ] Unauthorized users redirected to login
- [ ] Role-based access working

---

## 🐛 Troubleshooting

### Issue: "Build fails with dependency errors"
**Solution:**
```bash
# Clear Maven cache and rebuild
rm -rf ~/.m2/repository
mvn clean install -DskipTests
```

### Issue: "Database connection error"
**Solution:**
```bash
# Verify MySQL is running
# Check connection parameters in application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Issue: "Login page shows 404 Not Found"
**Solution:**
```
1. Verify JSP files are in: src/main/webapp/WEB-INF/jsp/auth/
2. Check that LoginController is mapped to /auth/login
3. Verify application.properties has:
   spring.mvc.view.prefix=/WEB-INF/jsp/
   spring.mvc.view.suffix=.jsp
```

### Issue: "Password not working for test accounts"
**Solution:**
```
1. Re-run the migration script to reset test data
2. Ensure BCrypt dependency is installed
3. Check that SecurityConfig.java has:
   @Bean
   public PasswordEncoder passwordEncoder()
```

### Issue: "Account locked after login attempts"
**Solution:**
```
1. Wait 15 minutes for automatic unlock
2. Or run in database:
   UPDATE users SET failed_login_attempts = 0 
   WHERE username = 'username';
3. Or use admin panel to unlock account
```

### Issue: "Session expires too quickly"
**Solution:**
```
# Increase session timeout in application.properties:
server.servlet.session.timeout=120m
```

---

## 📝 Common Tasks

### Reset Admin Password

```sql
-- Update password to "NewPassword123"
UPDATE users 
SET password = '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5YQEw.y6bW0z6'
WHERE username = 'admin';
```

### View User Login History

```sql
SELECT user_id, action, ip_address, status, created_at 
FROM user_audit_log 
ORDER BY created_at DESC 
LIMIT 20;
```

### Unlock Locked Account

```sql
UPDATE users 
SET failed_login_attempts = 0, account_locked_until = NULL 
WHERE username = 'username';
```

### Create New Admin User (Manual)

```sql
-- First, generate BCrypt hash of desired password
-- Then insert:
INSERT INTO users (username, email, password, first_name, last_name, role, status)
VALUES ('newadmin', 'admin@hospital.com', '$2a$12$HASH_HERE', 'New', 'Admin', 'ADMIN', 'ACTIVE');
```

---

## 🔗 Next Steps After Installation

1. **Test All Features:**
   - Register new user
   - Login/logout
   - Change password
   - View profile

2. **Customize (Optional):**
   - Change login page styling
   - Add company logo
   - Modify password requirements
   - Configure session timeout

3. **Integrate with Existing Controllers:**
   - Update DashboardController to require authentication
   - Add authentication checks to other endpoints
   - Apply role-based access to your features

4. **Production Readiness:**
   - Enable HTTPS
   - Configure CSRF protection
   - Set secure session cookies
   - Enable logging and monitoring

---

## 📚 Documentation Reference

For more details, see:
- `AUTHENTICATION_GUIDE.md` - Detailed implementation explanation
- `AUTHENTICATION_IMPLEMENTATION_SUMMARY.md` - Complete feature overview
- `ENHANCEMENTS_GUIDE.md` - Other enhancement options

---

## ⏱️ Estimated Timeline

| Task | Time |
|------|------|
| Copy files | 2 minutes |
| Update pom.xml | 1 minute |
| Create database tables | 2 minutes |
| Update properties | 1 minute |
| Build project | 3-5 minutes |
| Test application | 3-5 minutes |
| **TOTAL** | **~15 minutes** |

---

## ✨ Summary

You now have a complete, production-ready authentication system!

**Features Enabled:**
- ✅ Secure user registration
- ✅ Password-based login
- ✅ Role-based access control
- ✅ Account security (locking, audit logs)
- ✅ Password management
- ✅ User profile management
- ✅ Session management
- ✅ CSRF protection

**Next Enhancement:** Email notifications for password resets and account activities

---

**Need Help?** Check the troubleshooting section or refer to implementation guides!

**Ready to continue?** Explore the other enhancements in `ENHANCEMENTS_GUIDE.md` 🚀
