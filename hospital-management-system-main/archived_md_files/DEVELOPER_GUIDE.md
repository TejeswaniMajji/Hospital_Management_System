# Hospital Management System - Developer Guide

## 👨‍💻 For Developers

### Project Overview
This is a **Spring Boot MVC application** with:
- **4 Core Entities**: Patient, Doctor, Appointment, HospitalStaff
- **Layered Architecture**: Controller → Service → Repository → Database
- **JSP Views**: Dynamic web pages with JSTL
- **Responsive UI**: CSS Grid, Flexbox, Mobile-friendly

---

## 🏗️ Architecture Layers

### 1. **Presentation Layer (View)**
- Location: `src/main/webapp/WEB-INF/jsp/`
- Technology: JSP + HTML5 + CSS3 + JavaScript
- Responsibility: Display data and collect user input

### 2. **Controller Layer**
- Location: `src/main/java/com/hospital/controller/`
- Technology: Spring MVC Controllers
- Responsibility: Handle HTTP requests, delegate to services

### 3. **Service Layer**
- Location: `src/main/java/com/hospital/service/`
- Technology: Spring Services
- Responsibility: Business logic, data processing

### 4. **Repository Layer**
- Location: `src/main/java/com/hospital/repository/`
- Technology: Spring Data JPA
- Responsibility: Database access, query execution

### 5. **Entity/Model Layer**
- Location: `src/main/java/com/hospital/model/`
- Technology: JPA Entities with Hibernate
- Responsibility: Data structure definition

---

## 📋 File-by-File Explanation

### Controllers

#### `DashboardController.java`
```java
Purpose: Home page and statistics
Routes:
  GET  / → Dashboard with stats
  GET  /home → Home page
```

#### `PatientController.java`
```java
Purpose: Patient CRUD operations
Routes:
  GET    /patient/list → List all patients
  GET    /patient/add → Add patient form
  POST   /patient/save → Save new patient
  GET    /patient/edit/{id} → Edit form
  POST   /patient/update → Update patient
  GET    /patient/delete/{id} → Delete patient
  GET    /patient/view/{id} → View details
  POST   /patient/search → Search patients
```

#### Similar patterns for:
- `DoctorController.java`
- `AppointmentController.java`

### Models

#### `Patient.java`
- **Annotations**: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
- **Fields**: 13 database columns
- **Relations**: One-to-Many with Appointment
- **Constraints**: Unique email/phone, Not null validations

#### Similar patterns for:
- `Doctor.java`
- `Appointment.java` (with @ManyToOne relations)
- `HospitalStaff.java`

### Repositories

```java
// Extends JpaRepository<Entity, ID>
// Provides CRUD operations automatically
// Add custom query methods as needed
```

Example:
```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    List<Patient> findByFirstNameContainingIgnoreCase(String name);
}
```

### Services

```java
// Inject repositories
// Implement business logic
// Return data to controllers
```

Example:
```java
@Service
public class PatientService {
    @Autowired
    private PatientRepository repo;
    
    public Patient createPatient(Patient p) {
        return repo.save(p);
    }
}
```

---

## 🔍 Request Flow Example

### Adding a Patient:
```
1. User clicks "Add Patient"
   ↓
2. PatientController.showAddForm() 
   → Returns "patient/add.jsp"
   ↓
3. User fills form and clicks Save
   ↓
4. Form POST to /patient/save
   ↓
5. PatientController.savePatient()
   → Calls patientService.createPatient()
   ↓
6. PatientService.createPatient()
   → Calls patientRepository.save()
   ↓
7. PatientRepository.save()
   → Executes SQL INSERT
   ↓
8. Data saved in MySQL
   ↓
9. Redirect to /patient/list
   ↓
10. User sees updated list
```

---

## 🗄️ Database Schema

### Patient Table
```sql
CREATE TABLE patients (
    patient_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    address VARCHAR(500),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    blood_group VARCHAR(50),
    medical_history VARCHAR(500),
    registration_date DATE NOT NULL,
    status VARCHAR(50)
);
```

### Appointment Table (with Foreign Keys)
```sql
CREATE TABLE appointments (
    appointment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date_time DATETIME NOT NULL,
    status VARCHAR(50),
    reason VARCHAR(500),
    notes VARCHAR(500),
    priority VARCHAR(50),
    created_at DATETIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);
```

---

## 🎨 Frontend Structure

### JSP Template Structure
```jsp
<!-- Common Header (Navigation) -->
<%@ include file="..." %>

<!-- Page-specific Content -->
<main class="main-content">
    <!-- Form or List or Details -->
</main>

<!-- Common Footer -->
<%@ include file="..." %>

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/js/script.js"></script>
```

### CSS Organization
```css
/* Variables */
:root { --primary-color: #2c3e50; }

/* Base Styles */
html, body { ... }
.container { ... }

/* Components */
.header { ... }
.btn { ... }
.form-group { ... }
.data-table { ... }

/* Utilities */
.status-badge { ... }
.priority-badge { ... }

/* Responsive */
@media (max-width: 768px) { ... }
```

### JavaScript Functions
```javascript
// Form Validation
validateForm(form) { ... }

// Utilities
formatDate(dateString) { ... }
formatTime(timeString) { ... }

// Table Operations
sortTable(columnIndex) { ... }
filterTable(searchTerm) { ... }
exportTableToCSV(fileName) { ... }

// User Feedback
showNotification(message, type) { ... }
showLoader() { ... }
hideLoader() { ... }
```

---

## 🔧 Configuration Files

### `pom.xml`
```xml
<!-- Spring Boot Parent -->
<!-- Spring Data JPA -->
<!-- Hibernate -->
<!-- MySQL Driver -->
<!-- JSP Support (Tomcat Embed Jasper) -->
<!-- Lombok -->
<!-- Validation -->
<!-- Testing -->
```

### `application.properties`
```properties
# Server
server.port=8080
server.servlet.context-path=/hospital

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JSP Views
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

# Logging
logging.level.com.hospital=DEBUG
```

---

## 📝 Common Development Tasks

### Add a New Entity

1. **Create Model Class**
```java
@Entity
@Table(name = "new_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    // ... more fields
}
```

2. **Create Repository**
```java
@Repository
public interface NewRepository extends JpaRepository<NewEntity, Long> {
    // Custom query methods
}
```

3. **Create Service**
```java
@Service
public class NewService {
    @Autowired
    private NewRepository repo;
    
    public NewEntity create(NewEntity entity) {
        return repo.save(entity);
    }
}
```

4. **Create Controller**
```java
@Controller
@RequestMapping("/new")
public class NewController {
    @Autowired
    private NewService service;
    
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("items", service.getAll());
        return "new/list";
    }
}
```

5. **Create JSP Views**
- `src/main/webapp/WEB-INF/jsp/new/list.jsp`
- `src/main/webapp/WEB-INF/jsp/new/add.jsp`
- etc.

### Add a New Feature

Example: Add patient search by age

1. **Service Layer**
```java
public List<Patient> findByAge(int age) {
    LocalDate birthDate = LocalDate.now().minusYears(age);
    return patientRepository.findByDateOfBirthBefore(birthDate);
}
```

2. **Repository**
```java
List<Patient> findByDateOfBirthBefore(LocalDate date);
```

3. **Controller**
```java
@PostMapping("/search-by-age")
public String searchByAge(@RequestParam int age, Model model) {
    model.addAttribute("patients", service.findByAge(age));
    return "patient/list";
}
```

4. **JSP**
```jsp
<form method="POST" action="/patient/search-by-age">
    <input type="number" name="age" placeholder="Age">
    <button type="submit">Search</button>
</form>
```

---

## 🧪 Testing Checklist

- [ ] Create new record
- [ ] Read/View record
- [ ] Update record
- [ ] Delete record
- [ ] Search functionality
- [ ] Form validation
- [ ] Mobile responsiveness
- [ ] Browser compatibility
- [ ] Database relationships
- [ ] Error handling

---

## 🐛 Debugging Tips

### 1. Enable SQL Logging
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 2. Check Application Logs
```bash
# Console shows all DEBUG logs
# Look for error stack traces
```

### 3. Database Verification
```sql
SELECT COUNT(*) FROM patients;
SELECT * FROM patients WHERE id = 1;
DESCRIBE patients;
```

### 4. Browser DevTools
- F12 → Network tab to check HTTP requests
- Console tab for JavaScript errors
- Elements tab to inspect HTML

### 5. IDE Debugging
- Set breakpoints in code
- Debug → Debug 'Application'
- Step through code execution

---

## 📚 Best Practices

### Java/Spring
- ✅ Use meaningful variable names
- ✅ Add comments for complex logic
- ✅ Use @Transactional for multi-step operations
- ✅ Validate input in service layer
- ✅ Use Optional for nullable returns
- ✅ Implement proper error handling

### Database
- ✅ Use indexes on frequently searched columns
- ✅ Normalize database schema
- ✅ Use foreign keys for relationships
- ✅ Set appropriate data types
- ✅ Add NOT NULL constraints where needed

### Frontend
- ✅ Validate forms client-side
- ✅ Provide user feedback
- ✅ Use semantic HTML
- ✅ Make responsive design
- ✅ Optimize CSS/JS
- ✅ Accessibility considerations

### Security
- ✅ Never store passwords in plain text
- ✅ Use parameterized queries (JPA does this)
- ✅ Validate and sanitize input
- ✅ Use HTTPS in production
- ✅ Implement authentication
- ✅ Add authorization checks

---

## 🚀 Performance Optimization

### Database
```sql
-- Add indexes
CREATE INDEX idx_patient_email ON patients(email);
CREATE INDEX idx_doctor_specialty ON doctors(specialization);
```

### Spring
```properties
# Connection pooling (automatic with HikariCP)
# Lazy loading for relationships
# Second-level caching
```

### Frontend
```css
/* Minimize CSS */
/* Compress images */
/* Use CSS sprites */
```

```javascript
/* Minimize JS */
/* Defer non-critical scripts */
/* Use event delegation */
```

---

## 📖 Resource Links

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Hibernate Docs](https://hibernate.org/orm/)
- [JPA Docs](https://jakarta.ee/specifications/persistence/)
- [Maven Central](https://mvnrepository.com/)
- [MySQL Docs](https://dev.mysql.com/doc/)
- [MDN Web Docs](https://developer.mozilla.org/)

---

## 🤝 Contributing Guidelines

1. Create a feature branch
2. Write clean, well-commented code
3. Follow the existing code style
4. Test thoroughly before pushing
5. Write meaningful commit messages
6. Update documentation

---

**Happy Developing! 🚀**
