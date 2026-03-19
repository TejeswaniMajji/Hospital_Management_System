# Quick Start - Copy & Paste Commands

## 🚀 Install & Run (3 Commands)

### Command 1: Install Java 17
```bash
brew install java@17 && sudo ln -sfn $(brew --prefix)/opt/java@17/bin/java /usr/local/bin/java
```

### Command 2: Install Maven
```bash
brew install maven
```

### Command 3: Build & Run Project
```bash
cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System" && mvn clean install -DskipTests && mvn spring-boot:run
```

---

## ✅ Verification Commands

After each install, verify success:

```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.9+
```

---

## 🌐 Access Application

Once you see "Tomcat started on port(s): 8080":

**Open in Browser:**
```
http://localhost:8080/hospital/auth/login
```

---

## 🧪 Test Accounts

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | Password123 |
| Doctor | doctor1 | Password123 |
| Patient | patient1 | Password123 |

---

## 📱 Key URLs

```
Login:       http://localhost:8080/hospital/auth/login
Register:    http://localhost:8080/hospital/auth/register
Dashboard:   http://localhost:8080/hospital/dashboard
Profile:     http://localhost:8080/hospital/auth/profile
Patients:    http://localhost:8080/hospital/patient/list
Doctors:     http://localhost:8080/hospital/doctor/list
```

---

## 🛑 Stop Application

Press: `Ctrl + C` in the terminal

---

## ❓ Issues?

See: **RUN_PROJECT_GUIDE.md** for detailed troubleshooting
