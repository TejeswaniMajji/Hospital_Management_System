# Hospital Management System

A comprehensive Hospital Management System built using **Java Spring Boot**, **Hibernate ORM**, **MySQL**, **JSP**, **HTML5**, **CSS3**, and **JavaScript**.

## Features

### 🏥 Core Functionality
- **Patient Management**: Register, view, update, and delete patient records
- **Doctor Management**: Manage doctor profiles with specializations and departments
- **Appointment Scheduling**: Schedule, reschedule, and cancel appointments
- **Staff Management**: Manage hospital staff records
- **Dashboard**: Real-time statistics and quick access to key features
- **Search & Filter**: Advanced search capabilities across all modules
- **Responsive UI**: Mobile-friendly interface

### 🔧 Technical Stack
- **Backend**: Java 17, Spring Boot 3.1.5
- **ORM**: Hibernate 6.2.11
- **Database**: MySQL 8.0
- **Frontend**: JSP, HTML5, CSS3, JavaScript
- **Build Tool**: Maven
- **Template Engine**: JSP (Java Server Pages)
- **Authentication**: Spring Security (can be added)

## Project Structure

```
Hospital-Management-System/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/com/hospital/
│   │   │   ├── HospitalManagementApplication.java
│   │   │   ├── model/
│   │   │   │   ├── Patient.java
│   │   │   │   ├── Doctor.java
│   │   │   │   ├── Appointment.java
│   │   │   │   └── HospitalStaff.java
│   │   │   ├── repository/
│   │   │   │   ├── PatientRepository.java
│   │   │   │   ├── DoctorRepository.java
│   │   │   │   ├── AppointmentRepository.java
│   │   │   │   └── HospitalStaffRepository.java
│   │   │   ├── service/
│   │   │   │   ├── PatientService.java
│   │   │   │   ├── DoctorService.java
│   │   │   │   ├── AppointmentService.java
│   │   │   │   └── HospitalStaffService.java
│   │   │   └── controller/
│   │   │       ├── DashboardController.java
│   │   │       ├── PatientController.java
│   │   │       ├── DoctorController.java
│   │   │       └── AppointmentController.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/
│   │       ├── WEB-INF/jsp/
│   │       │   ├── index.jsp
│   │       │   ├── patient/
│   │       │   ├── doctor/
│   │       │   └── appointment/
│   │       ├── css/
│   │       │   └── style.css
│   │       └── js/
│   │           └── script.js
│   └── test/
└── README.md
```

## Prerequisites

### Required Software
- **Java Development Kit (JDK)**: Version 17 or higher
- **MySQL Server**: Version 8.0 or higher
- **Apache Maven**: Version 3.6 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java extensions

### Required MySQL Database
Ensure MySQL is running and create a database for the application.

## Installation & Setup

### 1. Clone or Extract the Project
```bash
# Extract the project files to your desired location
cd Hospital-Management-System
```

### 2. Create MySQL Database
```sql
CREATE DATABASE hospital_db;
USE hospital_db;
```

### 3. Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=root
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/hospital`

## Database Schema

### Tables Created Automatically
The following tables will be created by Hibernate (DDL):

- **patients**: Stores patient information
- **doctors**: Stores doctor details
- **appointments**: Manages appointments between patients and doctors
- **hospital_staff**: Stores staff member information

## API Endpoints

### Patient Management
- `GET /hospital/patient/list` - View all patients
- `GET /hospital/patient/add` - Patient registration form
- `POST /hospital/patient/save` - Save new patient
- `GET /hospital/patient/edit/{id}` - Edit patient form
- `POST /hospital/patient/update` - Update patient
- `GET /hospital/patient/delete/{id}` - Delete patient
- `GET /hospital/patient/view/{id}` - View patient details
- `POST /hospital/patient/search` - Search patients

### Doctor Management
- `GET /hospital/doctor/list` - View all doctors
- `GET /hospital/doctor/add` - Doctor registration form
- `POST /hospital/doctor/save` - Save new doctor
- `GET /hospital/doctor/edit/{id}` - Edit doctor form
- `POST /hospital/doctor/update` - Update doctor
- `GET /hospital/doctor/delete/{id}` - Delete doctor
- `GET /hospital/doctor/view/{id}` - View doctor details
- `POST /hospital/doctor/search` - Search doctors

### Appointment Management
- `GET /hospital/appointment/list` - View all appointments
- `GET /hospital/appointment/add` - Appointment scheduling form
- `POST /hospital/appointment/save` - Schedule appointment
- `GET /hospital/appointment/edit/{id}` - Edit appointment form
- `POST /hospital/appointment/update` - Update appointment
- `GET /hospital/appointment/delete/{id}` - Delete appointment
- `GET /hospital/appointment/view/{id}` - View appointment details
- `POST /hospital/appointment/search` - Search appointments

### Dashboard
- `GET /hospital/` - Main dashboard
- `GET /hospital/home` - Home page

## User Guide

### Adding a Patient
1. Navigate to **Patients** → **Add New Patient**
2. Fill in all required information
3. Click **Save Patient**

### Scheduling an Appointment
1. Go to **Appointments** → **Schedule New Appointment**
2. Select patient and doctor
3. Choose appointment date and time
4. Enter reason and priority
5. Click **Schedule Appointment**

### Managing Doctors
1. Navigate to **Doctors** → **Add New Doctor**
2. Enter doctor details including specialization
3. Click **Save Doctor**

### Searching Records
1. Use the search forms on respective pages
2. Select search type (Name, Email, etc.)
3. Enter search value
4. Click **Search**

## Features Explained

### Dashboard
- Displays total count of patients, doctors, and staff
- Quick action buttons for common tasks
- Real-time statistics

### Patient Management
- Complete patient profile with medical history
- Blood group tracking
- Contact and address information
- Patient status management

### Doctor Management
- License number validation
- Specialization and department tracking
- Years of experience
- Availability status

### Appointment Scheduling
- Schedule appointments with auto-generated status
- Priority levels (Low, Medium, High, Urgent)
- Reason tracking and notes
- Appointment status updates

## Security Considerations

The current version includes:
- ✅ Data validation on forms
- ✅ Input sanitization
- ✅ CSRF protection (Spring Boot default)

For production deployment, add:
- 🔐 Authentication & Authorization
- 🔒 Encryption for sensitive data
- 🛡️ Role-based access control
- 📋 Audit logging
- 🔑 API key management

## Performance Tips

1. **Database Indexing**: Add indexes on frequently searched fields
   ```sql
   CREATE INDEX idx_patient_email ON patients(email);
   CREATE INDEX idx_doctor_license ON doctors(licenseNumber);
   ```

2. **Connection Pooling**: Configured in application.properties

3. **Caching**: Implement caching for read-heavy operations

4. **Pagination**: Add pagination for large datasets

## Troubleshooting

### Issue: Application won't start
- Check if MySQL is running
- Verify database connection credentials
- Ensure port 8080 is available

### Issue: Database tables not created
- Check `spring.jpa.hibernate.ddl-auto=update` in application.properties
- Review console for Hibernate errors

### Issue: JSP pages not rendering
- Ensure JSP files are in `src/main/webapp/WEB-INF/jsp/`
- Check file paths in controllers

## Future Enhancements

- 🔐 User authentication and role-based access
- 📧 Email notifications for appointments
- 📊 Advanced analytics and reporting
- 💾 Backup and restore functionality
- 🌐 RESTful API endpoints
- 📱 Mobile app support
- 🔔 SMS notifications
- 📄 Medical records and prescriptions

## Contributing

Feel free to fork this project and submit pull requests with improvements.

## License

This project is open source and available under the MIT License.

## Support

For issues or questions:
1. Check existing documentation
2. Review the source code comments
3. Create an issue with detailed description

## Author

Hospital Management System v1.0.0
Created: March 2026

---

**Happy coding! 🚀**
