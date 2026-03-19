# Hospital Management System - Quick Start Guide

## ⚡ Quick Setup (5 Minutes)

### Prerequisites Installed?
- ✅ Java 17+
- ✅ MySQL 8.0+
- ✅ Maven 3.6+

### Step 1: Create Database (30 seconds)
```bash
mysql -u root -p
```
```sql
CREATE DATABASE hospital_db;
EXIT;
```

### Step 2: Update Configuration (1 minute)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=root  # Change if needed
```

### Step 3: Build Project (2 minutes)
```bash
mvn clean install
```

### Step 4: Run Application (1 minute)
```bash
mvn spring-boot:run
```

### Step 5: Access Application (30 seconds)
Open browser: **http://localhost:8080/hospital**

## 🎯 First Steps

1. **Go to Dashboard** - See statistics
2. **Add a Patient** - Click "Add Patient"
3. **Add a Doctor** - Click "Add Doctor"
4. **Schedule Appointment** - Book first appointment
5. **Test Search** - Search for records

## 📝 Sample Test Data

### Patient to Add
- Name: John Doe
- Email: john@example.com
- Phone: 555-0001
- DOB: 1990-01-15
- Blood: O+

### Doctor to Add
- Name: Dr. Smith
- License: DOC12345
- Specialization: Cardiology
- Phone: 555-1001
- Email: smith@hospital.com

## ❌ Common Issues

| Issue | Solution |
|-------|----------|
| Port 8080 in use | Change `server.port=8081` in properties |
| MySQL connection error | Check password in properties file |
| Blank pages | Clear browser cache (Ctrl+F5) |
| Database tables missing | Restart application with `ddl-auto=update` |

## 📞 Need Help?

1. **Check logs** - See error messages in console
2. **Read SETUP_GUIDE.md** - Detailed setup instructions
3. **Check README.md** - Full documentation

## 🎉 You're All Set!

Your Hospital Management System is running! Start using it now.

---

### Next: Explore Features
- ➕ Add more patients and doctors
- 📅 Schedule multiple appointments
- 🔍 Try advanced search filters
- ✏️ Edit and update records
