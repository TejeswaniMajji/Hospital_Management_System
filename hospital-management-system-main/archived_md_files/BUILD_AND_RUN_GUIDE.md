# 🚀 Build and Run Guide - Hospital Management System

## ✅ Prerequisites Installed Successfully

```
✅ Java 17 (openjdk 17.0.18)
✅ Maven 3.9.13
✅ MySQL 8.0+ (already running)
✅ All dependencies configured
```

---

## 🏗️ Building the Project

The project is currently being built. The first build takes 5-10 minutes as Maven downloads all dependencies.

### What's Happening:
- **Downloading dependencies** (~300MB of libraries)
- **Compiling Java source code** (5+ Java classes)
- **Processing configurations** (Spring Boot, Hibernate, etc.)
- **Creating WAR/JAR artifact** (deployable application)

### Expected Output:
```
[INFO] BUILD SUCCESS
Total time: X minutes
Finished at: YYYY-MM-DD
```

---

## 🏃 Running the Application

Once the build completes, run the application with:

```bash
cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System"
mvn spring-boot:run
```

### Expected Output:
```
Tomcat started on port(s): 8080 (http)
Started Application in X seconds
```

---

## 🌐 Access the Application

### Login Page:
```
http://localhost:8080/hospital/auth/login
```

### Default Credentials:

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | Password123 |
| Doctor | doctor1 | Password123 |
| Nurse | nurse1 | Password123 |
| Patient | patient1 | Password123 |

---

## 📋 Features Available

### Authentication System:
- ✅ Login with role-based access
- ✅ User registration
- ✅ Password change
- ✅ Profile management
- ✅ CSRF protection
- ✅ Session management

### Hospital Management:
- ✅ Patient management
- ✅ Doctor scheduling
- ✅ Appointment booking
- ✅ Medical records
- ✅ Department management

---

## 🐛 Troubleshooting

### Issue: "Connection refused - MySQL"
**Solution:** Verify MySQL is running
```bash
mysql -u root -p -e "SELECT 1"
```

### Issue: "Port 8080 already in use"
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Build fails with "Java not found"
**Solution:** Verify Java path:
```bash
java -version
echo $JAVA_HOME
```

### Issue: Maven command not found
**Solution:** Add Maven to PATH:
```bash
export PATH="/usr/local/opt/maven/bin:$PATH"
mvn -version
```

---

## 📊 Build Status

### Current Phase: 
🔄 **Downloading Maven dependencies** (in progress)

### Next Phases:
1. ✅ Java 17 installed
2. ✅ Maven installed
3. 🔄 Dependencies downloading
4. ⏳ Compilation
5. ⏳ Testing (skipped)
6. ⏳ Packaging
7. ⏳ Installation complete → Ready to run

---

## 📝 Project Structure

```
Hospital Database Management System/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── entity/              # JPA entities (User, Doctor, etc.)
│   │   │   ├── repository/          # Spring Data repositories
│   │   │   ├── service/             # Business logic
│   │   │   ├── controller/          # REST & MVC controllers
│   │   │   └── auth/                # Authentication classes
│   │   ├── resources/
│   │   │   ├── application.properties  # Configuration
│   │   │   ├── db/migration/           # Flyway migrations
│   │   │   └── templates/              # Thymeleaf templates
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── jsp/             # JSP pages
│   └── test/                        # Unit tests
├── target/                          # Build output (generated)
└── documentation/                   # Project guides
```

---

## ✨ First Time Setup Completed

### ✅ Completed Steps:
1. ✅ Installed Java 17 (openjdk@17)
2. ✅ Installed Maven 3.9.13
3. ✅ Added Java to PATH
4. ✅ Verified MySQL is running
5. ✅ Started Maven build process
6. ✅ Dependencies downloading

### ⏳ Waiting For:
- Build completion (5-10 minutes)
- Application to start
- MySQL migrations to run

---

## 🎯 Next Steps

1. **Wait for build to complete** (check terminal)
2. **Run application**:
   ```bash
   mvn spring-boot:run
   ```
3. **Open browser** to: `http://localhost:8080/hospital/auth/login`
4. **Login** with admin credentials
5. **Explore** the hospital management features

---

## 📚 Additional Commands

### Clean and rebuild:
```bash
mvn clean install -DskipTests
```

### Run with specific Maven profile:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### View project dependencies:
```bash
mvn dependency:tree
```

### Skip tests and build faster:
```bash
mvn install -DskipTests -q
```

### Run with debug output:
```bash
mvn clean install -X
```

---

## 🔗 Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Maven**: https://maven.apache.org/
- **Hibernate**: https://hibernate.org/
- **MySQL**: https://www.mysql.com/
- **Java 17**: https://openjdk.java.net/projects/jdk/17/

---

## 💡 Pro Tips

1. **First build is slow** - Maven downloads ~300MB of dependencies
2. **Subsequent builds are faster** - Dependencies are cached
3. **Keep MySQL running** - Required for database operations
4. **Check logs** - Watch terminal for any errors during startup
5. **Use HTTPS locally** - Configure SSL for production deployments

---

## ✅ Installation Complete!

All prerequisites are installed and configured. Your Hospital Management System is ready to build and run!

**Build Status: 🔄 IN PROGRESS**

Check terminal output for build completion message: `BUILD SUCCESS`

Once you see `BUILD SUCCESS`, run:
```bash
mvn spring-boot:run
```

Then visit: http://localhost:8080/hospital/auth/login

---

**Build Command:** `mvn clean install -DskipTests`
**Run Command:** `mvn spring-boot:run`
**Access URL:** `http://localhost:8080/hospital/auth/login`
