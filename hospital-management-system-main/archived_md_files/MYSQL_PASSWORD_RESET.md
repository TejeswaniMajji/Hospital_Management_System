# MySQL Password Reset Guide

## Problem
The MySQL root user has a password set, but we don't know what it is. The application is configured to connect to MySQL but fails with:
```
Access denied for user 'root'@'localhost' (using password: YES/NO)
```

## Solution Options

### Option 1: Reset MySQL Root Password (Recommended)

#### Step 1: Stop MySQL
```bash
brew services stop mysql
```

#### Step 2: Start MySQL in Safe Mode (skip-grant-tables)
```bash
mysqld_safe --skip-grant-tables &
```

#### Step 3: Connect Without Password
```bash
mysql -u root
```

#### Step 4: Set New Password
```sql
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'HospitalDB@2024';
EXIT;
```

#### Step 5: Kill Safe Mode and Restart
```bash
pkill -f mysqld_safe
pkill -f mysqld
sleep 2
brew services start mysql
```

#### Step 6: Verify and Create Database
```bash
mysql -u root -p'HospitalDB@2024' -e "
  CREATE DATABASE IF NOT EXISTS hospital_db;
  SHOW DATABASES LIKE 'hospital_db';
"
```

### Option 2: Use a Different MySQL User

If you prefer not to change root, create a dedicated user:

```bash
mysql -u root -p  # Enter current password when prompted
```

Then execute:
```sql
CREATE USER IF NOT EXISTS 'hospital'@'localhost' IDENTIFIED BY 'hospital123';
GRANT ALL PRIVILEGES ON hospital_db.* TO 'hospital'@'localhost';
CREATE DATABASE IF NOT EXISTS hospital_db;
FLUSH PRIVILEGES;
EXIT;
```

Then update `application.properties`:
```properties
spring.datasource.username=hospital
spring.datasource.password=hospital123
```

### Option 3: Reinstall MySQL

If you want a completely fresh start:

```bash
brew uninstall mysql
rm -rf /usr/local/var/mysql
brew install mysql
mysql_secure_installation  # Follow prompts to set root password
```

## Next Steps After Password Reset

1. Update `src/main/resources/application.properties` with correct credentials
2. Rebuild the project: `mvn clean package -DskipTests`
3. Run the application: `mvn spring-boot:run`
4. Access at: `http://localhost:8080/hospital`

## Automatic Database Initialization

The application is configured with `spring.jpa.hibernate.ddl-auto=update`, which means:
- Tables will be automatically created on first run
- Existing data will be preserved
- Schema changes will be applied

No manual database schema setup is needed beyond creating the empty `hospital_db` database.
