# Hospital Management System - Run & Setup Guide

**Date:** March 8, 2026  
**Status:** Ready to Run (Setup Required)

---

## ⚠️ Prerequisites Check

Your system is currently missing:
- ❌ **Java 17+** - Required to run Spring Boot application
- ❌ **Maven 3.6+** - Required to build the project
- ✅ **MySQL 8.0+** - Already installed and running!
- ✅ **Project Files** - All 19 authentication files created

---

## 🔧 Setup Instructions (macOS)

### Step 1: Install Java 17+

**Option A: Using Homebrew (Recommended)**
```bash
# Install Java 17
brew install java@17

# Create symlink for java command
sudo ln -sfn $(brew --prefix)/opt/java@17/bin/java /usr/local/bin/java

# Verify installation
java -version
```

**Option B: Manual Download**
1. Visit: https://www.oracle.com/java/technologies/downloads/
2. Download JDK 17 for macOS
3. Run the installer
4. Verify: `java -version`

**Option C: Using SDKMAN** (Alternative)
```bash
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash

# Install Java 17
sdk install java 17.0.9-oracle

# Verify
java -version
```

---

### Step 2: Install Maven 3.6+

**Option A: Using Homebrew (Recommended)**
```bash
# Install Maven
brew install maven

# Verify installation
mvn -version
```

**Option B: Manual Download**
1. Visit: https://maven.apache.org/download.cgi
2. Download Apache Maven 3.9.x
3. Extract to: `/usr/local/apache-maven-3.9.x`
4. Add to PATH:
```bash
# Edit ~/.zshrc or ~/.bash_profile
export PATH=/usr/local/apache-maven-3.9.x/bin:$PATH

# Apply changes
source ~/.zshrc
```

---

### Step 3: Verify MySQL Database

**Check if MySQL is running:**
```bash
# Check MySQL process
ps aux | grep mysqld

# Try connecting (no password required based on config)
mysql -u root
```

**If MySQL is not set up with hospital_db:**
```bash
# Connect to MySQL
mysql -u root

# Run these commands:
CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

# Exit
EXIT;
```

---

## 🚀 Run the Project (3 Steps)

### Step 1: Navigate to Project Directory
```bash
cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System"
```

### Step 2: Build the Project
```bash
# Clean build (skips tests for faster compilation)
mvn clean install -DskipTests

# Or with full build (includes tests):
mvn clean install

# This should output: BUILD SUCCESS
```

**Build Output Should Look Like:**
```
[INFO] Building Hospital Management System 1.0.0
[INFO] ────────────────────────────────────────────────
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXXs
```

### Step 3: Run the Application
```bash
# Start the Spring Boot application
mvn spring-boot:run
```

**Or directly run the JAR:**
```bash
# Build creates a JAR file
java -jar target/hospital-management-system-1.0.0.war

# Or using Spring Boot Maven plugin
mvn spring-boot:run
```

---

## 🌐 Access the Application

Once the application starts, you'll see:
```
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
c.h.HospitalManagementApplication        : Started in XX.XXX seconds
```

**Then open your browser and go to:**

### Login Page (No Authentication Yet)
```
http://localhost:8080/hospital/auth/login
```

### Registration Page
```
http://localhost:8080/hospital/auth/register
```

### Dashboard (After Login)
```
http://localhost:8080/hospital/dashboard
```

---

## 🧪 Test With Sample Accounts

After the application starts, test with these credentials:

**Admin Account:**
- Username: `admin`
- Password: `Password123`
- Role: ADMIN (Full access)

**Doctor Account:**
- Username: `doctor1`
- Password: `Password123`
- Role: DOCTOR

**Patient Account:**
- Username: `patient1`
- Password: `Password123`
- Role: PATIENT

---

## 🐛 Troubleshooting

### Issue: "Java not found" or "java: command not found"

**Solution:**
```bash
# Check if Java is installed
which java

# If not found, install it:
brew install java@17

# Or add to PATH if installed but not in path:
export PATH=$(/usr/libexec/java_home -v 17)/bin:$PATH

# Make permanent by adding to ~/.zshrc:
echo 'export PATH=$(/usr/libexec/java_home -v 17)/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

### Issue: "Maven is not recognized"

**Solution:**
```bash
# Install Maven
brew install maven

# Verify
mvn -version

# If still not found, add to PATH:
export PATH=/usr/local/opt/maven/bin:$PATH
echo 'export PATH=/usr/local/opt/maven/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

### Issue: "mvn clean install" takes very long (First time)

**Solution:**
- Maven is downloading dependencies for the first time (Normal!)
- This can take 5-10 minutes
- Subsequent builds will be faster
- Grab a coffee ☕ and wait

### Issue: "Connection refused" to MySQL

**Solution:**
```bash
# Start MySQL manually if not running
brew services start mysql

# Or using the safe method
/usr/local/opt/mysql/bin/mysqld_safe --datadir=/usr/local/var/mysql

# Or start via Homebrew
mysql.server start
```

### Issue: "BUILD FAILURE" during Maven build

**Common Causes and Solutions:**

1. **Java version mismatch:**
   ```bash
   # Check current Java version
   java -version
   
   # Should be Java 17 or higher
   # If lower, reinstall Java 17
   ```

2. **Corrupted Maven cache:**
   ```bash
   # Clear Maven cache
   rm -rf ~/.m2/repository
   
   # Rebuild
   mvn clean install -DskipTests
   ```

3. **Missing dependencies:**
   ```bash
   # Update Maven
   brew upgrade maven
   
   # Force update dependencies
   mvn clean install -U
   ```

---

## 📋 Complete Setup Checklist

- [ ] **Java 17+** installed (`java -version`)
- [ ] **Maven 3.6+** installed (`mvn -version`)
- [ ] **MySQL 8.0+** running and accessible
- [ ] **hospital_db** database exists
- [ ] Project directory: `/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System`
- [ ] All 19 authentication files present
- [ ] `pom.xml` has all dependencies
- [ ] `application.properties` configured
- [ ] No port conflicts on 8080

---

## 🚀 Quick Setup Script

Save this as `setup.sh` and run with `bash setup.sh`:

```bash
#!/bin/bash

echo "🏥 Hospital Management System - Setup Script"
echo "============================================="

# Check Java
echo "📌 Checking Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Installing..."
    brew install java@17
    sudo ln -sfn $(brew --prefix)/opt/java@17/bin/java /usr/local/bin/java
else
    echo "✅ Java found: $(java -version 2>&1 | head -1)"
fi

# Check Maven
echo "📌 Checking Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found. Installing..."
    brew install maven
else
    echo "✅ Maven found: $(mvn -version | head -1)"
fi

# Check MySQL
echo "📌 Checking MySQL..."
if pgrep -x mysqld > /dev/null; then
    echo "✅ MySQL is running"
else
    echo "⚠️  MySQL not running. Starting..."
    brew services start mysql
fi

# Navigate to project
cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System"

# Build project
echo "📌 Building project..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Starting application..."
    mvn spring-boot:run
else
    echo "❌ Build failed. Check error messages above."
    exit 1
fi
```

---

## 📱 Application URLs After Launch

| Feature | URL | Login Required |
|---------|-----|---|
| **Login** | http://localhost:8080/hospital/auth/login | ❌ No |
| **Register** | http://localhost:8080/hospital/auth/register | ❌ No |
| **Dashboard** | http://localhost:8080/hospital/dashboard | ✅ Yes |
| **Patients** | http://localhost:8080/hospital/patient/list | ✅ Yes |
| **Doctors** | http://localhost:8080/hospital/doctor/list | ✅ Yes |
| **Appointments** | http://localhost:8080/hospital/appointment/list | ✅ Yes |
| **Profile** | http://localhost:8080/hospital/auth/profile | ✅ Yes |
| **Logout** | http://localhost:8080/hospital/auth/logout | ✅ Yes |

---

## 💡 Pro Tips

1. **Skip Tests for Faster Build:**
   ```bash
   mvn clean install -DskipTests
   ```

2. **View Maven Build Progress:**
   ```bash
   mvn clean install -DskipTests --show-errors
   ```

3. **Update Dependencies:**
   ```bash
   mvn clean install -U
   ```

4. **Run with Custom Port:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
   ```

5. **Run in Background:**
   ```bash
   nohup mvn spring-boot:run > app.log 2>&1 &
   ```

6. **View Live Logs:**
   ```bash
   tail -f app.log
   ```

---

## 📝 Port & Configuration

**Current Configuration (application.properties):**
- Server Port: `8080`
- Context Path: `/hospital`
- Database: `hospital_db` on `localhost:3306`
- Database User: `root`
- Database Password: `root`

**To change these, edit:**
```
src/main/resources/application.properties
```

---

## 🎯 Expected Console Output

After running `mvn spring-boot:run`, you should see:

```
.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_|\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::               (v3.1.5)

2026-03-08T06:15:00.000+00:00  INFO 12345 --- [main] c.h.HospitalManagementApplication
: Starting HospitalManagementApplication

...

2026-03-08T06:15:15.000+00:00  INFO 12345 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer
: Tomcat started on port(s): 8080 (http) with context path '/hospital'

2026-03-08T06:15:16.000+00:00  INFO 12345 --- [main] c.h.HospitalManagementApplication
: Started HospitalManagementApplication in 12.345 seconds
```

---

## ✅ Success Indicators

✅ Maven build completes with "BUILD SUCCESS"  
✅ No errors in console output  
✅ Tomcat starts on port 8080  
✅ Can access http://localhost:8080/hospital/auth/login  
✅ Database tables created automatically  
✅ Can login with provided credentials  

---

## 🔗 Useful Resources

- **Java Downloads:** https://www.oracle.com/java/technologies/downloads/
- **Maven Downloads:** https://maven.apache.org/download.cgi
- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **MySQL Docs:** https://dev.mysql.com/doc/

---

## 📞 Need Help?

If you encounter issues:

1. Check the **Troubleshooting** section above
2. Review the console output for error messages
3. Verify all prerequisites are installed
4. Try clearing Maven cache: `rm -rf ~/.m2/repository`
5. Restart MySQL: `brew services restart mysql`

---

**Ready to run? Follow these 3 steps:**

1. Install Java 17+
2. Install Maven 3.6+
3. Run: `cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System" && mvn spring-boot:run`

Then go to: **http://localhost:8080/hospital/**

Let's get your Hospital Management System running! 🏥🚀
