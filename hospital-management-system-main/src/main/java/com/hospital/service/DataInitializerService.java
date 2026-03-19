package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.HospitalStaff;
import com.hospital.model.Patient;
import com.hospital.model.Prescription;
import com.hospital.model.Supplier;
import com.hospital.model.User;
import com.hospital.model.Inventory;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.HospitalStaffRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.PrescriptionRepository;
import com.hospital.repository.SupplierRepository;
import com.hospital.repository.UserRepository;
import com.hospital.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Service to initialize database with sample data on application startup
 */
@Service
public class DataInitializerService implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private HospitalStaffRepository hospitalStaffRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false)
    private InventoryRepository inventoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Clear and reload users to ensure passwords are correct
        initializeUsers();
        
        // Only initialize others if database is empty
        if (patientRepository.count() == 0) {
            initializePatients();
            initializeDoctors();
            initializeAppointments();
            initializeStaff();
            initializeUsers();
            initializePrescriptions();
            initializeInventory();
            initializeSuppliers();
            System.out.println("✓ Database initialized with sample data");
        }
    }

    private void initializeInventory() {
        // Create a few inventory items
    if (inventoryRepository == null) return;

    Inventory i1 = new Inventory();
    i1.setMedicineName("Paracetamol 500mg");
    i1.setCategory("Analgesic");
    i1.setStockLevel(120);
    i1.setMinThreshold(20);
    i1.setUnitPrice(0.05);
    i1.setManufacturer("Acme Pharma");
    i1.setLocation("Shelf A1");
    i1.setLastRestocked(LocalDateTime.now().minusDays(3));
    inventoryRepository.save(i1);

    Inventory i2 = new Inventory();
    i2.setMedicineName("Amoxicillin 250mg");
    i2.setCategory("Antibiotic");
    i2.setStockLevel(40);
    i2.setMinThreshold(30);
    i2.setUnitPrice(0.12);
    i2.setManufacturer("HealthCorp");
    i2.setLocation("Shelf B2");
    i2.setLastRestocked(LocalDateTime.now().minusDays(10));
    inventoryRepository.save(i2);

    Inventory i3 = new Inventory();
    i3.setMedicineName("Insulin 10ml");
    i3.setCategory("Endocrine");
    i3.setStockLevel(8);
    i3.setMinThreshold(10);
    i3.setUnitPrice(15.0);
    i3.setManufacturer("DiabeCare");
    i3.setLocation("Fridge-1");
    i3.setLastRestocked(LocalDateTime.now().minusDays(5));
    inventoryRepository.save(i3);
    }

    private void initializeUsers() {
        // Clear existing users to ensure fresh start with new passwords/roles
        userRepository.deleteAll();

        // Ensure we save for both current and new passwords to be safe
        // Admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEmail("admin@hospital.com");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole("ADMIN");
        admin.setStatus("ACTIVE");
        admin.setCreatedAt(LocalDateTime.now());
        userRepository.save(admin);
        
        System.out.println("→ Admin user initialized with username 'admin' and password 'admin123'");

        // Doctor user
        User doctor = new User();
        doctor.setUsername("doctor1");
        doctor.setPassword(passwordEncoder.encode("doctor123"));
        doctor.setEmail("doctor1@hospital.com");
        doctor.setFirstName("James");
        doctor.setLastName("Wilson");
        doctor.setRole("DOCTOR");
        doctor.setStatus("ACTIVE");
        doctor.setCreatedAt(LocalDateTime.now());
        userRepository.save(doctor);

        // Nurse user
        User nurse = new User();
        nurse.setUsername("nurse1");
        nurse.setPassword(passwordEncoder.encode("nurse123"));
        nurse.setEmail("nurse1@hospital.com");
        nurse.setFirstName("Maria");
        nurse.setLastName("Garcia");
        nurse.setRole("NURSE");
        nurse.setStatus("ACTIVE");
        nurse.setCreatedAt(LocalDateTime.now());
        userRepository.save(nurse);

        // Patient user
        User patient = new User();
        patient.setUsername("patient1");
        patient.setPassword(passwordEncoder.encode("patient123"));
        patient.setEmail("patient1@hospital.com");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setRole("PATIENT");
        patient.setStatus("ACTIVE");
        patient.setCreatedAt(LocalDateTime.now());
        userRepository.save(patient);
    }

    private void initializePatients() {
        Patient p1 = new Patient();
        p1.setFirstName("John");
        p1.setLastName("Doe");
        p1.setPhoneNumber("555-0101");
        p1.setEmail("john.doe@email.com");
        p1.setDateOfBirth(LocalDate.of(1985, 5, 15));
        p1.setGender("Male");
        p1.setAddress("123 Main St");
        p1.setCity("Springfield");
        p1.setState("IL");
        p1.setZipCode("62701");
        p1.setMedicalHistory("Hypertension, Diabetes Type 2");
        p1.setRegistrationDate(LocalDate.now());
        p1.setBloodGroup("O+");
        p1.setStatus("Active");
        patientRepository.save(p1);

        Patient p2 = new Patient();
        p2.setFirstName("Jane");
        p2.setLastName("Smith");
        p2.setPhoneNumber("555-0102");
        p2.setEmail("jane.smith@email.com");
        p2.setDateOfBirth(LocalDate.of(1990, 8, 22));
        p2.setGender("Female");
        p2.setAddress("456 Oak Ave");
        p2.setCity("Springfield");
        p2.setState("IL");
        p2.setZipCode("62702");
        p2.setMedicalHistory("Asthma");
        p2.setRegistrationDate(LocalDate.now());
        p2.setBloodGroup("A+");
        p2.setStatus("Active");
        patientRepository.save(p2);

        Patient p3 = new Patient();
        p3.setFirstName("Michael");
        p3.setLastName("Johnson");
        p3.setPhoneNumber("555-0103");
        p3.setEmail("michael.j@email.com");
        p3.setDateOfBirth(LocalDate.of(1978, 12, 30));
        p3.setGender("Male");
        p3.setAddress("789 Pine Rd");
        p3.setCity("Springfield");
        p3.setState("IL");
        p3.setZipCode("62703");
        p3.setMedicalHistory("None");
        p3.setRegistrationDate(LocalDate.now());
        p3.setBloodGroup("B+");
        p3.setStatus("Active");
        patientRepository.save(p3);

        Patient p4 = new Patient();
        p4.setFirstName("Sarah");
        p4.setLastName("Williams");
        p4.setPhoneNumber("555-0104");
        p4.setEmail("sarah.w@email.com");
        p4.setDateOfBirth(LocalDate.of(1988, 3, 18));
        p4.setGender("Female");
        p4.setAddress("321 Elm St");
        p4.setCity("Springfield");
        p4.setState("IL");
        p4.setZipCode("62704");
        p4.setMedicalHistory("Allergies, Migraine");
        p4.setRegistrationDate(LocalDate.now());
        p4.setBloodGroup("O-");
        p4.setStatus("Active");
        patientRepository.save(p4);

        Patient p5 = new Patient();
        p5.setFirstName("Robert");
        p5.setLastName("Brown");
        p5.setPhoneNumber("555-0105");
        p5.setEmail("robert.brown@email.com");
        p5.setDateOfBirth(LocalDate.of(1975, 7, 10));
        p5.setGender("Male");
        p5.setAddress("654 Maple Dr");
        p5.setCity("Springfield");
        p5.setState("IL");
        p5.setZipCode("62705");
        p5.setMedicalHistory("Heart Disease");
        p5.setRegistrationDate(LocalDate.now());
        p5.setBloodGroup("AB+");
        p5.setStatus("Active");
        patientRepository.save(p5);

        Patient p6 = new Patient();
        p6.setFirstName("Jessica");
        p6.setLastName("Garcia");
        p6.setPhoneNumber("555-0106");
        p6.setEmail("jessica.garcia@email.com");
        p6.setDateOfBirth(LocalDate.of(1992, 4, 5));
        p6.setGender("Female");
        p6.setAddress("987 Cedar Ln");
        p6.setCity("Springfield");
        p6.setState("IL");
        p6.setZipCode("62706");
        p6.setMedicalHistory("Thyroid disorder");
        p6.setRegistrationDate(LocalDate.now());
        p6.setBloodGroup("B-");
        p6.setStatus("Active");
        patientRepository.save(p6);

        Patient p7 = new Patient();
        p7.setFirstName("Christopher");
        p7.setLastName("Taylor");
        p7.setPhoneNumber("555-0107");
        p7.setEmail("chris.taylor@email.com");
        p7.setDateOfBirth(LocalDate.of(1980, 11, 28));
        p7.setGender("Male");
        p7.setAddress("246 Birch St");
        p7.setCity("Springfield");
        p7.setState("IL");
        p7.setZipCode("62707");
        p7.setMedicalHistory("Arthritis");
        p7.setRegistrationDate(LocalDate.now());
        p7.setBloodGroup("AB-");
        p7.setStatus("Active");
        patientRepository.save(p7);

        Patient p8 = new Patient();
        p8.setFirstName("Amanda");
        p8.setLastName("Martinez");
        p8.setPhoneNumber("555-0108");
        p8.setEmail("amanda.martinez@email.com");
        p8.setDateOfBirth(LocalDate.of(1995, 6, 12));
        p8.setGender("Female");
        p8.setAddress("579 Hickory Ln");
        p8.setCity("Springfield");
        p8.setState("IL");
        p8.setZipCode("62708");
        p8.setMedicalHistory("Anxiety disorder");
        p8.setRegistrationDate(LocalDate.now());
        p8.setBloodGroup("A-");
        p8.setStatus("Active");
        patientRepository.save(p8);

        Patient p9 = new Patient();
        p9.setFirstName("Daniel");
        p9.setLastName("Rodriguez");
        p9.setPhoneNumber("555-0109");
        p9.setEmail("daniel.rodriguez@email.com");
        p9.setDateOfBirth(LocalDate.of(1987, 9, 20));
        p9.setGender("Male");
        p9.setAddress("812 Willow Rd");
        p9.setCity("Springfield");
        p9.setState("IL");
        p9.setZipCode("62709");
        p9.setMedicalHistory("High cholesterol");
        p9.setRegistrationDate(LocalDate.now());
        p9.setBloodGroup("O+");
        p9.setStatus("Active");
        patientRepository.save(p9);

        Patient p10 = new Patient();
        p10.setFirstName("Nicole");
        p10.setLastName("Anderson");
        p10.setPhoneNumber("555-0110");
        p10.setEmail("nicole.anderson@email.com");
        p10.setDateOfBirth(LocalDate.of(1993, 2, 14));
        p10.setGender("Female");
        p10.setAddress("345 Sycamore Ave");
        p10.setCity("Springfield");
        p10.setState("IL");
        p10.setZipCode("62710");
        p10.setMedicalHistory("Depression");
        p10.setRegistrationDate(LocalDate.now());
        p10.setBloodGroup("B+");
        p10.setStatus("Active");
        patientRepository.save(p10);

        // Additional Patients
        Patient p11 = new Patient();
        p11.setFirstName("David");
        p11.setLastName("Thompson");
        p11.setPhoneNumber("555-0111");
        p11.setEmail("david.thompson@email.com");
        p11.setDateOfBirth(LocalDate.of(1982, 4, 25));
        p11.setGender("Male");
        p11.setAddress("456 Redwood St");
        p11.setCity("Springfield");
        p11.setState("IL");
        p11.setZipCode("62711");
        p11.setMedicalHistory("Back pain, Sciatica");
        p11.setRegistrationDate(LocalDate.now());
        p11.setBloodGroup("A+");
        p11.setStatus("Active");
        patientRepository.save(p11);

        Patient p12 = new Patient();
        p12.setFirstName("Emily");
        p12.setLastName("Clark");
        p12.setPhoneNumber("555-0112");
        p12.setEmail("emily.clark@email.com");
        p12.setDateOfBirth(LocalDate.of(1996, 7, 8));
        p12.setGender("Female");
        p12.setAddress("789 Sequoia Ave");
        p12.setCity("Springfield");
        p12.setState("IL");
        p12.setZipCode("62712");
        p12.setMedicalHistory("PCOS");
        p12.setRegistrationDate(LocalDate.now());
        p12.setBloodGroup("O-");
        p12.setStatus("Active");
        patientRepository.save(p12);

        Patient p13 = new Patient();
        p13.setFirstName("William");
        p13.setLastName("Harris");
        p13.setPhoneNumber("555-0113");
        p13.setEmail("william.harris@email.com");
        p13.setDateOfBirth(LocalDate.of(1970, 11, 30));
        p13.setGender("Male");
        p13.setAddress("123 Magnolia Dr");
        p13.setCity("Springfield");
        p13.setState("IL");
        p13.setZipCode("62713");
        p13.setMedicalHistory("COPD, Former smoker");
        p13.setRegistrationDate(LocalDate.now());
        p13.setBloodGroup("AB+");
        p13.setStatus("Active");
        patientRepository.save(p13);

        Patient p14 = new Patient();
        p14.setFirstName("Sophia");
        p14.setLastName("Lewis");
        p14.setPhoneNumber("555-0114");
        p14.setEmail("sophia.lewis@email.com");
        p14.setDateOfBirth(LocalDate.of(1989, 1, 17));
        p14.setGender("Female");
        p14.setAddress("456 Dogwood Ln");
        p14.setCity("Springfield");
        p14.setState("IL");
        p14.setZipCode("62714");
        p14.setMedicalHistory("Endometriosis");
        p14.setRegistrationDate(LocalDate.now());
        p14.setBloodGroup("B-");
        p14.setStatus("Active");
        patientRepository.save(p14);

        Patient p15 = new Patient();
        p15.setFirstName("James");
        p15.setLastName("Walker");
        p15.setPhoneNumber("555-0115");
        p15.setEmail("james.walker@email.com");
        p15.setDateOfBirth(LocalDate.of(1965, 8, 22));
        p15.setGender("Male");
        p15.setAddress("789 Chestnut Rd");
        p15.setCity("Springfield");
        p15.setState("IL");
        p15.setZipCode("62715");
        p15.setMedicalHistory("Prostate issues, Diabetes Type 2");
        p15.setRegistrationDate(LocalDate.now());
        p15.setBloodGroup("O+");
        p15.setStatus("Active");
        patientRepository.save(p15);

        Patient p16 = new Patient();
        p16.setFirstName("Olivia");
        p16.setLastName("Young");
        p16.setPhoneNumber("555-0116");
        p16.setEmail("olivia.young@email.com");
        p16.setDateOfBirth(LocalDate.of(1998, 3, 5));
        p16.setGender("Female");
        p16.setAddress("234 Poplar St");
        p16.setCity("Springfield");
        p16.setState("IL");
        p16.setZipCode("62716");
        p16.setMedicalHistory("Acne, Vitamin D deficiency");
        p16.setRegistrationDate(LocalDate.now());
        p16.setBloodGroup("A-");
        p16.setStatus("Active");
        patientRepository.save(p16);

        Patient p17 = new Patient();
        p17.setFirstName("Benjamin");
        p17.setLastName("King");
        p17.setPhoneNumber("555-0117");
        p17.setEmail("benjamin.king@email.com");
        p17.setDateOfBirth(LocalDate.of(1977, 6, 18));
        p17.setGender("Male");
        p17.setAddress("567 Ash Avenue");
        p17.setCity("Springfield");
        p17.setState("IL");
        p17.setZipCode("62717");
        p17.setMedicalHistory("Gout, High uric acid");
        p17.setRegistrationDate(LocalDate.now());
        p17.setBloodGroup("B+");
        p17.setStatus("Active");
        patientRepository.save(p17);

        Patient p18 = new Patient();
        p18.setFirstName("Isabella");
        p18.setLastName("Scott");
        p18.setPhoneNumber("555-0118");
        p18.setEmail("isabella.scott@email.com");
        p18.setDateOfBirth(LocalDate.of(1991, 10, 12));
        p18.setGender("Female");
        p18.setAddress("890 Juniper Way");
        p18.setCity("Springfield");
        p18.setState("IL");
        p18.setZipCode("62718");
        p18.setMedicalHistory("Fibromyalgia");
        p18.setRegistrationDate(LocalDate.now());
        p18.setBloodGroup("AB-");
        p18.setStatus("Active");
        patientRepository.save(p18);

        Patient p19 = new Patient();
        p19.setFirstName("Alexander");
        p19.setLastName("Green");
        p19.setPhoneNumber("555-0119");
        p19.setEmail("alexander.green@email.com");
        p19.setDateOfBirth(LocalDate.of(1984, 12, 28));
        p19.setGender("Male");
        p19.setAddress("123 Cypress Ct");
        p19.setCity("Springfield");
        p19.setState("IL");
        p19.setZipCode("62719");
        p19.setMedicalHistory("Sleep apnea");
        p19.setRegistrationDate(LocalDate.now());
        p19.setBloodGroup("O+");
        p19.setStatus("Active");
        patientRepository.save(p19);

        Patient p20 = new Patient();
        p20.setFirstName("Mia");
        p20.setLastName("Adams");
        p20.setPhoneNumber("555-0120");
        p20.setEmail("mia.adams@email.com");
        p20.setDateOfBirth(LocalDate.of(1994, 5, 3));
        p20.setGender("Female");
        p20.setAddress("456 Palm Street");
        p20.setCity("Springfield");
        p20.setState("IL");
        p20.setZipCode("62720");
        p20.setMedicalHistory("IBS, Food allergies");
        p20.setRegistrationDate(LocalDate.now());
        p20.setBloodGroup("A+");
        p20.setStatus("Active");
        patientRepository.save(p20);

        Patient p21 = new Patient();
        p21.setFirstName("Ethan");
        p21.setLastName("Nelson");
        p21.setPhoneNumber("555-0121");
        p21.setEmail("ethan.nelson@email.com");
        p21.setDateOfBirth(LocalDate.of(1979, 9, 14));
        p21.setGender("Male");
        p21.setAddress("789 Olive Lane");
        p21.setCity("Springfield");
        p21.setState("IL");
        p21.setZipCode("62721");
        p21.setMedicalHistory("Kidney stones history");
        p21.setRegistrationDate(LocalDate.now());
        p21.setBloodGroup("B-");
        p21.setStatus("Active");
        patientRepository.save(p21);

        Patient p22 = new Patient();
        p22.setFirstName("Charlotte");
        p22.setLastName("Hill");
        p22.setPhoneNumber("555-0122");
        p22.setEmail("charlotte.hill@email.com");
        p22.setDateOfBirth(LocalDate.of(1986, 2, 20));
        p22.setGender("Female");
        p22.setAddress("234 Spruce Road");
        p22.setCity("Springfield");
        p22.setState("IL");
        p22.setZipCode("62722");
        p22.setMedicalHistory("Rheumatoid arthritis");
        p22.setRegistrationDate(LocalDate.now());
        p22.setBloodGroup("O-");
        p22.setStatus("Active");
        patientRepository.save(p22);

        Patient p23 = new Patient();
        p23.setFirstName("Henry");
        p23.setLastName("Baker");
        p23.setPhoneNumber("555-0123");
        p23.setEmail("henry.baker@email.com");
        p23.setDateOfBirth(LocalDate.of(1972, 7, 7));
        p23.setGender("Male");
        p23.setAddress("567 Fir Street");
        p23.setCity("Springfield");
        p23.setState("IL");
        p23.setZipCode("62723");
        p23.setMedicalHistory("Atrial fibrillation");
        p23.setRegistrationDate(LocalDate.now());
        p23.setBloodGroup("AB+");
        p23.setStatus("Active");
        patientRepository.save(p23);

        Patient p24 = new Patient();
        p24.setFirstName("Amelia");
        p24.setLastName("Rivera");
        p24.setPhoneNumber("555-0124");
        p24.setEmail("amelia.rivera@email.com");
        p24.setDateOfBirth(LocalDate.of(1990, 4, 15));
        p24.setGender("Female");
        p24.setAddress("890 Laurel Ave");
        p24.setCity("Springfield");
        p24.setState("IL");
        p24.setZipCode("62724");
        p24.setMedicalHistory("Hypothyroidism");
        p24.setRegistrationDate(LocalDate.now());
        p24.setBloodGroup("A-");
        p24.setStatus("Active");
        patientRepository.save(p24);

        Patient p25 = new Patient();
        p25.setFirstName("Lucas");
        p25.setLastName("Carter");
        p25.setPhoneNumber("555-0125");
        p25.setEmail("lucas.carter@email.com");
        p25.setDateOfBirth(LocalDate.of(1968, 11, 9));
        p25.setGender("Male");
        p25.setAddress("123 Beech Boulevard");
        p25.setCity("Springfield");
        p25.setState("IL");
        p25.setZipCode("62725");
        p25.setMedicalHistory("Glaucoma, Cataracts");
        p25.setRegistrationDate(LocalDate.now());
        p25.setBloodGroup("B+");
        p25.setStatus("Active");
        patientRepository.save(p25);

        // Additional 40 Patients (p26-p65)
        Patient p26 = new Patient();
        p26.setFirstName("Mason");
        p26.setLastName("Mitchell");
        p26.setPhoneNumber("555-0126");
        p26.setEmail("mason.mitchell@email.com");
        p26.setDateOfBirth(LocalDate.of(1983, 3, 12));
        p26.setGender("Male");
        p26.setAddress("234 Walnut Street");
        p26.setCity("Chicago");
        p26.setState("IL");
        p26.setZipCode("60601");
        p26.setMedicalHistory("Chronic fatigue syndrome");
        p26.setRegistrationDate(LocalDate.now());
        p26.setBloodGroup("O+");
        p26.setStatus("Active");
        patientRepository.save(p26);

        Patient p27 = new Patient();
        p27.setFirstName("Ava");
        p27.setLastName("Perez");
        p27.setPhoneNumber("555-0127");
        p27.setEmail("ava.perez@email.com");
        p27.setDateOfBirth(LocalDate.of(1997, 8, 25));
        p27.setGender("Female");
        p27.setAddress("567 Cherry Lane");
        p27.setCity("Chicago");
        p27.setState("IL");
        p27.setZipCode("60602");
        p27.setMedicalHistory("Anemia");
        p27.setRegistrationDate(LocalDate.now());
        p27.setBloodGroup("A+");
        p27.setStatus("Active");
        patientRepository.save(p27);

        Patient p28 = new Patient();
        p28.setFirstName("Logan");
        p28.setLastName("Roberts");
        p28.setPhoneNumber("555-0128");
        p28.setEmail("logan.roberts@email.com");
        p28.setDateOfBirth(LocalDate.of(1976, 1, 30));
        p28.setGender("Male");
        p28.setAddress("890 Hawthorn Rd");
        p28.setCity("Chicago");
        p28.setState("IL");
        p28.setZipCode("60603");
        p28.setMedicalHistory("Peripheral neuropathy");
        p28.setRegistrationDate(LocalDate.now());
        p28.setBloodGroup("B+");
        p28.setStatus("Active");
        patientRepository.save(p28);

        Patient p29 = new Patient();
        p29.setFirstName("Harper");
        p29.setLastName("Turner");
        p29.setPhoneNumber("555-0129");
        p29.setEmail("harper.turner@email.com");
        p29.setDateOfBirth(LocalDate.of(1992, 6, 14));
        p29.setGender("Female");
        p29.setAddress("123 Mulberry Ave");
        p29.setCity("Chicago");
        p29.setState("IL");
        p29.setZipCode("60604");
        p29.setMedicalHistory("Eczema, Psoriasis");
        p29.setRegistrationDate(LocalDate.now());
        p29.setBloodGroup("O-");
        p29.setStatus("Active");
        patientRepository.save(p29);

        Patient p30 = new Patient();
        p30.setFirstName("Jackson");
        p30.setLastName("Phillips");
        p30.setPhoneNumber("555-0130");
        p30.setEmail("jackson.phillips@email.com");
        p30.setDateOfBirth(LocalDate.of(1981, 10, 8));
        p30.setGender("Male");
        p30.setAddress("456 Acacia Dr");
        p30.setCity("Chicago");
        p30.setState("IL");
        p30.setZipCode("60605");
        p30.setMedicalHistory("Herniated disc");
        p30.setRegistrationDate(LocalDate.now());
        p30.setBloodGroup("AB+");
        p30.setStatus("Active");
        patientRepository.save(p30);

        Patient p31 = new Patient();
        p31.setFirstName("Evelyn");
        p31.setLastName("Campbell");
        p31.setPhoneNumber("555-0131");
        p31.setEmail("evelyn.campbell@email.com");
        p31.setDateOfBirth(LocalDate.of(1988, 4, 22));
        p31.setGender("Female");
        p31.setAddress("789 Linden St");
        p31.setCity("Naperville");
        p31.setState("IL");
        p31.setZipCode("60540");
        p31.setMedicalHistory("Vertigo, Meniere's disease");
        p31.setRegistrationDate(LocalDate.now());
        p31.setBloodGroup("A-");
        p31.setStatus("Active");
        patientRepository.save(p31);

        Patient p32 = new Patient();
        p32.setFirstName("Sebastian");
        p32.setLastName("Parker");
        p32.setPhoneNumber("555-0132");
        p32.setEmail("sebastian.parker@email.com");
        p32.setDateOfBirth(LocalDate.of(1974, 12, 5));
        p32.setGender("Male");
        p32.setAddress("234 Hemlock Way");
        p32.setCity("Naperville");
        p32.setState("IL");
        p32.setZipCode("60541");
        p32.setMedicalHistory("Type 1 Diabetes");
        p32.setRegistrationDate(LocalDate.now());
        p32.setBloodGroup("B-");
        p32.setStatus("Active");
        patientRepository.save(p32);

        Patient p33 = new Patient();
        p33.setFirstName("Scarlett");
        p33.setLastName("Evans");
        p33.setPhoneNumber("555-0133");
        p33.setEmail("scarlett.evans@email.com");
        p33.setDateOfBirth(LocalDate.of(1995, 7, 18));
        p33.setGender("Female");
        p33.setAddress("567 Aspen Court");
        p33.setCity("Naperville");
        p33.setState("IL");
        p33.setZipCode("60542");
        p33.setMedicalHistory("Celiac disease");
        p33.setRegistrationDate(LocalDate.now());
        p33.setBloodGroup("AB-");
        p33.setStatus("Active");
        patientRepository.save(p33);

        Patient p34 = new Patient();
        p34.setFirstName("Aiden");
        p34.setLastName("Edwards");
        p34.setPhoneNumber("555-0134");
        p34.setEmail("aiden.edwards@email.com");
        p34.setDateOfBirth(LocalDate.of(1986, 2, 28));
        p34.setGender("Male");
        p34.setAddress("890 Basswood Ln");
        p34.setCity("Naperville");
        p34.setState("IL");
        p34.setZipCode("60543");
        p34.setMedicalHistory("ADHD");
        p34.setRegistrationDate(LocalDate.now());
        p34.setBloodGroup("O+");
        p34.setStatus("Active");
        patientRepository.save(p34);

        Patient p35 = new Patient();
        p35.setFirstName("Luna");
        p35.setLastName("Collins");
        p35.setPhoneNumber("555-0135");
        p35.setEmail("luna.collins@email.com");
        p35.setDateOfBirth(LocalDate.of(1999, 9, 10));
        p35.setGender("Female");
        p35.setAddress("123 Cottonwood Rd");
        p35.setCity("Aurora");
        p35.setState("IL");
        p35.setZipCode("60502");
        p35.setMedicalHistory("Seasonal allergies");
        p35.setRegistrationDate(LocalDate.now());
        p35.setBloodGroup("A+");
        p35.setStatus("Active");
        patientRepository.save(p35);

        Patient p36 = new Patient();
        p36.setFirstName("Matthew");
        p36.setLastName("Stewart");
        p36.setPhoneNumber("555-0136");
        p36.setEmail("matthew.stewart@email.com");
        p36.setDateOfBirth(LocalDate.of(1971, 5, 15));
        p36.setGender("Male");
        p36.setAddress("456 Dogwood Court");
        p36.setCity("Aurora");
        p36.setState("IL");
        p36.setZipCode("60503");
        p36.setMedicalHistory("Osteoporosis");
        p36.setRegistrationDate(LocalDate.now());
        p36.setBloodGroup("B+");
        p36.setStatus("Active");
        patientRepository.save(p36);

        Patient p37 = new Patient();
        p37.setFirstName("Chloe");
        p37.setLastName("Sanchez");
        p37.setPhoneNumber("555-0137");
        p37.setEmail("chloe.sanchez@email.com");
        p37.setDateOfBirth(LocalDate.of(1993, 11, 3));
        p37.setGender("Female");
        p37.setAddress("789 Elderberry Ave");
        p37.setCity("Aurora");
        p37.setState("IL");
        p37.setZipCode("60504");
        p37.setMedicalHistory("Migraines, Light sensitivity");
        p37.setRegistrationDate(LocalDate.now());
        p37.setBloodGroup("O-");
        p37.setStatus("Active");
        patientRepository.save(p37);

        Patient p38 = new Patient();
        p38.setFirstName("Owen");
        p38.setLastName("Morris");
        p38.setPhoneNumber("555-0138");
        p38.setEmail("owen.morris@email.com");
        p38.setDateOfBirth(LocalDate.of(1980, 8, 20));
        p38.setGender("Male");
        p38.setAddress("234 Foxglove St");
        p38.setCity("Aurora");
        p38.setState("IL");
        p38.setZipCode("60505");
        p38.setMedicalHistory("Carpal tunnel syndrome");
        p38.setRegistrationDate(LocalDate.now());
        p38.setBloodGroup("AB+");
        p38.setStatus("Active");
        patientRepository.save(p38);

        Patient p39 = new Patient();
        p39.setFirstName("Penelope");
        p39.setLastName("Rogers");
        p39.setPhoneNumber("555-0139");
        p39.setEmail("penelope.rogers@email.com");
        p39.setDateOfBirth(LocalDate.of(1987, 3, 7));
        p39.setGender("Female");
        p39.setAddress("567 Ginkgo Lane");
        p39.setCity("Evanston");
        p39.setState("IL");
        p39.setZipCode("60201");
        p39.setMedicalHistory("Lupus");
        p39.setRegistrationDate(LocalDate.now());
        p39.setBloodGroup("A-");
        p39.setStatus("Active");
        patientRepository.save(p39);

        Patient p40 = new Patient();
        p40.setFirstName("Levi");
        p40.setLastName("Reed");
        p40.setPhoneNumber("555-0140");
        p40.setEmail("levi.reed@email.com");
        p40.setDateOfBirth(LocalDate.of(1978, 6, 25));
        p40.setGender("Male");
        p40.setAddress("890 Holly Road");
        p40.setCity("Evanston");
        p40.setState("IL");
        p40.setZipCode("60202");
        p40.setMedicalHistory("Hepatitis B (recovered)");
        p40.setRegistrationDate(LocalDate.now());
        p40.setBloodGroup("B-");
        p40.setStatus("Active");
        patientRepository.save(p40);

        Patient p41 = new Patient();
        p41.setFirstName("Layla");
        p41.setLastName("Cook");
        p41.setPhoneNumber("555-0141");
        p41.setEmail("layla.cook@email.com");
        p41.setDateOfBirth(LocalDate.of(1996, 1, 12));
        p41.setGender("Female");
        p41.setAddress("123 Ivy Street");
        p41.setCity("Evanston");
        p41.setState("IL");
        p41.setZipCode("60203");
        p41.setMedicalHistory("Polycystic kidney disease");
        p41.setRegistrationDate(LocalDate.now());
        p41.setBloodGroup("O+");
        p41.setStatus("Active");
        patientRepository.save(p41);

        Patient p42 = new Patient();
        p42.setFirstName("Jack");
        p42.setLastName("Morgan");
        p42.setPhoneNumber("555-0142");
        p42.setEmail("jack.morgan@email.com");
        p42.setDateOfBirth(LocalDate.of(1969, 10, 30));
        p42.setGender("Male");
        p42.setAddress("456 Jasmine Way");
        p42.setCity("Evanston");
        p42.setState("IL");
        p42.setZipCode("60204");
        p42.setMedicalHistory("Parkinson's disease (early stage)");
        p42.setRegistrationDate(LocalDate.now());
        p42.setBloodGroup("AB-");
        p42.setStatus("Active");
        patientRepository.save(p42);

        Patient p43 = new Patient();
        p43.setFirstName("Riley");
        p43.setLastName("Bell");
        p43.setPhoneNumber("555-0143");
        p43.setEmail("riley.bell@email.com");
        p43.setDateOfBirth(LocalDate.of(1991, 7, 4));
        p43.setGender("Female");
        p43.setAddress("789 Katsura Court");
        p43.setCity("Rockford");
        p43.setState("IL");
        p43.setZipCode("61101");
        p43.setMedicalHistory("Crohn's disease");
        p43.setRegistrationDate(LocalDate.now());
        p43.setBloodGroup("A+");
        p43.setStatus("Active");
        patientRepository.save(p43);

        Patient p44 = new Patient();
        p44.setFirstName("Wyatt");
        p44.setLastName("Murphy");
        p44.setPhoneNumber("555-0144");
        p44.setEmail("wyatt.murphy@email.com");
        p44.setDateOfBirth(LocalDate.of(1984, 4, 18));
        p44.setGender("Male");
        p44.setAddress("234 Locust Avenue");
        p44.setCity("Rockford");
        p44.setState("IL");
        p44.setZipCode("61102");
        p44.setMedicalHistory("Psoriatic arthritis");
        p44.setRegistrationDate(LocalDate.now());
        p44.setBloodGroup("B+");
        p44.setStatus("Active");
        patientRepository.save(p44);

        Patient p45 = new Patient();
        p45.setFirstName("Zoey");
        p45.setLastName("Bailey");
        p45.setPhoneNumber("555-0145");
        p45.setEmail("zoey.bailey@email.com");
        p45.setDateOfBirth(LocalDate.of(1998, 12, 22));
        p45.setGender("Female");
        p45.setAddress("567 Mimosa Drive");
        p45.setCity("Rockford");
        p45.setState("IL");
        p45.setZipCode("61103");
        p45.setMedicalHistory("Anxiety, Panic disorder");
        p45.setRegistrationDate(LocalDate.now());
        p45.setBloodGroup("O-");
        p45.setStatus("Active");
        patientRepository.save(p45);

        Patient p46 = new Patient();
        p46.setFirstName("Gabriel");
        p46.setLastName("Rivera");
        p46.setPhoneNumber("555-0146");
        p46.setEmail("gabriel.rivera@email.com");
        p46.setDateOfBirth(LocalDate.of(1975, 9, 5));
        p46.setGender("Male");
        p46.setAddress("890 Nectarine Lane");
        p46.setCity("Rockford");
        p46.setState("IL");
        p46.setZipCode("61104");
        p46.setMedicalHistory("Chronic bronchitis");
        p46.setRegistrationDate(LocalDate.now());
        p46.setBloodGroup("AB+");
        p46.setStatus("Active");
        patientRepository.save(p46);

        Patient p47 = new Patient();
        p47.setFirstName("Nora");
        p47.setLastName("Cooper");
        p47.setPhoneNumber("555-0147");
        p47.setEmail("nora.cooper@email.com");
        p47.setDateOfBirth(LocalDate.of(1989, 2, 14));
        p47.setGender("Female");
        p47.setAddress("123 Oak Hill Road");
        p47.setCity("Joliet");
        p47.setState("IL");
        p47.setZipCode("60431");
        p47.setMedicalHistory("Scoliosis");
        p47.setRegistrationDate(LocalDate.now());
        p47.setBloodGroup("A-");
        p47.setStatus("Active");
        patientRepository.save(p47);

        Patient p48 = new Patient();
        p48.setFirstName("Isaac");
        p48.setLastName("Richardson");
        p48.setPhoneNumber("555-0148");
        p48.setEmail("isaac.richardson@email.com");
        p48.setDateOfBirth(LocalDate.of(1982, 11, 28));
        p48.setGender("Male");
        p48.setAddress("456 Peach Tree St");
        p48.setCity("Joliet");
        p48.setState("IL");
        p48.setZipCode("60432");
        p48.setMedicalHistory("Epilepsy");
        p48.setRegistrationDate(LocalDate.now());
        p48.setBloodGroup("B-");
        p48.setStatus("Active");
        patientRepository.save(p48);

        Patient p49 = new Patient();
        p49.setFirstName("Lily");
        p49.setLastName("Cox");
        p49.setPhoneNumber("555-0149");
        p49.setEmail("lily.cox@email.com");
        p49.setDateOfBirth(LocalDate.of(1994, 5, 10));
        p49.setGender("Female");
        p49.setAddress("789 Quince Avenue");
        p49.setCity("Joliet");
        p49.setState("IL");
        p49.setZipCode("60433");
        p49.setMedicalHistory("Hashimoto's thyroiditis");
        p49.setRegistrationDate(LocalDate.now());
        p49.setBloodGroup("O+");
        p49.setStatus("Active");
        patientRepository.save(p49);

        Patient p50 = new Patient();
        p50.setFirstName("Elijah");
        p50.setLastName("Howard");
        p50.setPhoneNumber("555-0150");
        p50.setEmail("elijah.howard@email.com");
        p50.setDateOfBirth(LocalDate.of(1973, 8, 16));
        p50.setGender("Male");
        p50.setAddress("234 Redbud Way");
        p50.setCity("Joliet");
        p50.setState("IL");
        p50.setZipCode("60434");
        p50.setMedicalHistory("Benign prostatic hyperplasia");
        p50.setRegistrationDate(LocalDate.now());
        p50.setBloodGroup("AB-");
        p50.setStatus("Active");
        patientRepository.save(p50);

        Patient p51 = new Patient();
        p51.setFirstName("Grace");
        p51.setLastName("Ward");
        p51.setPhoneNumber("555-0151");
        p51.setEmail("grace.ward@email.com");
        p51.setDateOfBirth(LocalDate.of(1990, 3, 25));
        p51.setGender("Female");
        p51.setAddress("567 Sassafras Street");
        p51.setCity("Peoria");
        p51.setState("IL");
        p51.setZipCode("61602");
        p51.setMedicalHistory("Interstitial cystitis");
        p51.setRegistrationDate(LocalDate.now());
        p51.setBloodGroup("A+");
        p51.setStatus("Active");
        patientRepository.save(p51);

        Patient p52 = new Patient();
        p52.setFirstName("Caleb");
        p52.setLastName("Torres");
        p52.setPhoneNumber("555-0152");
        p52.setEmail("caleb.torres@email.com");
        p52.setDateOfBirth(LocalDate.of(1985, 6, 8));
        p52.setGender("Male");
        p52.setAddress("890 Tulip Lane");
        p52.setCity("Peoria");
        p52.setState("IL");
        p52.setZipCode("61603");
        p52.setMedicalHistory("Tendinitis");
        p52.setRegistrationDate(LocalDate.now());
        p52.setBloodGroup("B+");
        p52.setStatus("Active");
        patientRepository.save(p52);

        Patient p53 = new Patient();
        p53.setFirstName("Victoria");
        p53.setLastName("Peterson");
        p53.setPhoneNumber("555-0153");
        p53.setEmail("victoria.peterson@email.com");
        p53.setDateOfBirth(LocalDate.of(1979, 10, 12));
        p53.setGender("Female");
        p53.setAddress("123 Umbrella Tree Rd");
        p53.setCity("Peoria");
        p53.setState("IL");
        p53.setZipCode("61604");
        p53.setMedicalHistory("Multiple sclerosis");
        p53.setRegistrationDate(LocalDate.now());
        p53.setBloodGroup("O-");
        p53.setStatus("Active");
        patientRepository.save(p53);

        Patient p54 = new Patient();
        p54.setFirstName("Nathan");
        p54.setLastName("Gray");
        p54.setPhoneNumber("555-0154");
        p54.setEmail("nathan.gray@email.com");
        p54.setDateOfBirth(LocalDate.of(1967, 1, 20));
        p54.setGender("Male");
        p54.setAddress("456 Viburnum Court");
        p54.setCity("Peoria");
        p54.setState("IL");
        p54.setZipCode("61605");
        p54.setMedicalHistory("Macular degeneration");
        p54.setRegistrationDate(LocalDate.now());
        p54.setBloodGroup("AB+");
        p54.setStatus("Active");
        patientRepository.save(p54);

        Patient p55 = new Patient();
        p55.setFirstName("Hannah");
        p55.setLastName("Ramirez");
        p55.setPhoneNumber("555-0155");
        p55.setEmail("hannah.ramirez@email.com");
        p55.setDateOfBirth(LocalDate.of(1997, 4, 3));
        p55.setGender("Female");
        p55.setAddress("789 Wisteria Way");
        p55.setCity("Champaign");
        p55.setState("IL");
        p55.setZipCode("61820");
        p55.setMedicalHistory("Iron deficiency anemia");
        p55.setRegistrationDate(LocalDate.now());
        p55.setBloodGroup("A-");
        p55.setStatus("Active");
        patientRepository.save(p55);

        Patient p56 = new Patient();
        p56.setFirstName("Aaron");
        p56.setLastName("James");
        p56.setPhoneNumber("555-0156");
        p56.setEmail("aaron.james@email.com");
        p56.setDateOfBirth(LocalDate.of(1981, 7, 28));
        p56.setGender("Male");
        p56.setAddress("234 Xanadu Street");
        p56.setCity("Champaign");
        p56.setState("IL");
        p56.setZipCode("61821");
        p56.setMedicalHistory("Tinnitus");
        p56.setRegistrationDate(LocalDate.now());
        p56.setBloodGroup("B-");
        p56.setStatus("Active");
        patientRepository.save(p56);

        Patient p57 = new Patient();
        p57.setFirstName("Addison");
        p57.setLastName("Watson");
        p57.setPhoneNumber("555-0157");
        p57.setEmail("addison.watson@email.com");
        p57.setDateOfBirth(LocalDate.of(1993, 12, 15));
        p57.setGender("Female");
        p57.setAddress("567 Yellowwood Ave");
        p57.setCity("Champaign");
        p57.setState("IL");
        p57.setZipCode("61822");
        p57.setMedicalHistory("Endometriosis");
        p57.setRegistrationDate(LocalDate.now());
        p57.setBloodGroup("O+");
        p57.setStatus("Active");
        patientRepository.save(p57);

        Patient p58 = new Patient();
        p58.setFirstName("Dylan");
        p58.setLastName("Brooks");
        p58.setPhoneNumber("555-0158");
        p58.setEmail("dylan.brooks@email.com");
        p58.setDateOfBirth(LocalDate.of(1988, 9, 7));
        p58.setGender("Male");
        p58.setAddress("890 Zelkova Road");
        p58.setCity("Champaign");
        p58.setState("IL");
        p58.setZipCode("61823");
        p58.setMedicalHistory("Cluster headaches");
        p58.setRegistrationDate(LocalDate.now());
        p58.setBloodGroup("AB-");
        p58.setStatus("Active");
        patientRepository.save(p58);

        Patient p59 = new Patient();
        p59.setFirstName("Aubrey");
        p59.setLastName("Kelly");
        p59.setPhoneNumber("555-0159");
        p59.setEmail("aubrey.kelly@email.com");
        p59.setDateOfBirth(LocalDate.of(1996, 2, 22));
        p59.setGender("Female");
        p59.setAddress("123 Apple Blossom Lane");
        p59.setCity("Bloomington");
        p59.setState("IL");
        p59.setZipCode("61701");
        p59.setMedicalHistory("Chronic sinusitis");
        p59.setRegistrationDate(LocalDate.now());
        p59.setBloodGroup("A+");
        p59.setStatus("Active");
        patientRepository.save(p59);

        Patient p60 = new Patient();
        p60.setFirstName("Luke");
        p60.setLastName("Sanders");
        p60.setPhoneNumber("555-0160");
        p60.setEmail("luke.sanders@email.com");
        p60.setDateOfBirth(LocalDate.of(1972, 5, 18));
        p60.setGender("Male");
        p60.setAddress("456 Bluebell Court");
        p60.setCity("Bloomington");
        p60.setState("IL");
        p60.setZipCode("61702");
        p60.setMedicalHistory("Diverticulitis");
        p60.setRegistrationDate(LocalDate.now());
        p60.setBloodGroup("B+");
        p60.setStatus("Active");
        patientRepository.save(p60);

        Patient p61 = new Patient();
        p61.setFirstName("Stella");
        p61.setLastName("Price");
        p61.setPhoneNumber("555-0161");
        p61.setEmail("stella.price@email.com");
        p61.setDateOfBirth(LocalDate.of(1991, 8, 30));
        p61.setGender("Female");
        p61.setAddress("789 Carnation Drive");
        p61.setCity("Bloomington");
        p61.setState("IL");
        p61.setZipCode("61703");
        p61.setMedicalHistory("Ovarian cysts");
        p61.setRegistrationDate(LocalDate.now());
        p61.setBloodGroup("O-");
        p61.setStatus("Active");
        patientRepository.save(p61);

        Patient p62 = new Patient();
        p62.setFirstName("Julian");
        p62.setLastName("Bennett");
        p62.setPhoneNumber("555-0162");
        p62.setEmail("julian.bennett@email.com");
        p62.setDateOfBirth(LocalDate.of(1983, 11, 11));
        p62.setGender("Male");
        p62.setAddress("234 Daffodil Street");
        p62.setCity("Bloomington");
        p62.setState("IL");
        p62.setZipCode("61704");
        p62.setMedicalHistory("Gastritis");
        p62.setRegistrationDate(LocalDate.now());
        p62.setBloodGroup("AB+");
        p62.setStatus("Active");
        patientRepository.save(p62);

        Patient p63 = new Patient();
        p63.setFirstName("Savannah");
        p63.setLastName("Wood");
        p63.setPhoneNumber("555-0163");
        p63.setEmail("savannah.wood@email.com");
        p63.setDateOfBirth(LocalDate.of(1986, 4, 5));
        p63.setGender("Female");
        p63.setAddress("567 Eucalyptus Way");
        p63.setCity("Decatur");
        p63.setState("IL");
        p63.setZipCode("62521");
        p63.setMedicalHistory("Chronic fatigue");
        p63.setRegistrationDate(LocalDate.now());
        p63.setBloodGroup("A-");
        p63.setStatus("Active");
        patientRepository.save(p63);

        Patient p64 = new Patient();
        p64.setFirstName("Leo");
        p64.setLastName("Barnes");
        p64.setPhoneNumber("555-0164");
        p64.setEmail("leo.barnes@email.com");
        p64.setDateOfBirth(LocalDate.of(1977, 7, 22));
        p64.setGender("Male");
        p64.setAddress("890 Freesia Lane");
        p64.setCity("Decatur");
        p64.setState("IL");
        p64.setZipCode("62522");
        p64.setMedicalHistory("Varicose veins");
        p64.setRegistrationDate(LocalDate.now());
        p64.setBloodGroup("B-");
        p64.setStatus("Active");
        patientRepository.save(p64);

        Patient p65 = new Patient();
        p65.setFirstName("Brooklyn");
        p65.setLastName("Ross");
        p65.setPhoneNumber("555-0165");
        p65.setEmail("brooklyn.ross@email.com");
        p65.setDateOfBirth(LocalDate.of(1999, 10, 8));
        p65.setGender("Female");
        p65.setAddress("123 Gardenia Road");
        p65.setCity("Decatur");
        p65.setState("IL");
        p65.setZipCode("62523");
        p65.setMedicalHistory("Sports injuries, ACL repair");
        p65.setRegistrationDate(LocalDate.now());
        p65.setBloodGroup("O+");
        p65.setStatus("Active");
        patientRepository.save(p65);

        // Additional 10 Patients (p66-p75)
        Patient p66 = new Patient();
        p66.setFirstName("Carter");
        p66.setLastName("Hughes");
        p66.setPhoneNumber("555-0166");
        p66.setEmail("carter.hughes@email.com");
        p66.setDateOfBirth(LocalDate.of(1980, 6, 15));
        p66.setGender("Male");
        p66.setAddress("234 Hibiscus Street");
        p66.setCity("Springfield");
        p66.setState("IL");
        p66.setZipCode("62726");
        p66.setMedicalHistory("Lower back pain, Sciatica");
        p66.setRegistrationDate(LocalDate.now());
        p66.setBloodGroup("A+");
        p66.setStatus("Active");
        patientRepository.save(p66);

        Patient p67 = new Patient();
        p67.setFirstName("Aria");
        p67.setLastName("Flores");
        p67.setPhoneNumber("555-0167");
        p67.setEmail("aria.flores@email.com");
        p67.setDateOfBirth(LocalDate.of(1995, 3, 22));
        p67.setGender("Female");
        p67.setAddress("567 Iris Lane");
        p67.setCity("Springfield");
        p67.setState("IL");
        p67.setZipCode("62727");
        p67.setMedicalHistory("Asthma, Bronchitis");
        p67.setRegistrationDate(LocalDate.now());
        p67.setBloodGroup("B+");
        p67.setStatus("Active");
        patientRepository.save(p67);

        Patient p68 = new Patient();
        p68.setFirstName("Grayson");
        p68.setLastName("Washington");
        p68.setPhoneNumber("555-0168");
        p68.setEmail("grayson.washington@email.com");
        p68.setDateOfBirth(LocalDate.of(1962, 11, 8));
        p68.setGender("Male");
        p68.setAddress("890 Jasmine Court");
        p68.setCity("Chicago");
        p68.setState("IL");
        p68.setZipCode("60606");
        p68.setMedicalHistory("Coronary artery disease");
        p68.setRegistrationDate(LocalDate.now());
        p68.setBloodGroup("O-");
        p68.setStatus("Active");
        patientRepository.save(p68);

        Patient p69 = new Patient();
        p69.setFirstName("Eleanor");
        p69.setLastName("Butler");
        p69.setPhoneNumber("555-0169");
        p69.setEmail("eleanor.butler@email.com");
        p69.setDateOfBirth(LocalDate.of(1988, 8, 30));
        p69.setGender("Female");
        p69.setAddress("123 Kumquat Road");
        p69.setCity("Chicago");
        p69.setState("IL");
        p69.setZipCode("60607");
        p69.setMedicalHistory("Rheumatoid arthritis");
        p69.setRegistrationDate(LocalDate.now());
        p69.setBloodGroup("AB+");
        p69.setStatus("Active");
        patientRepository.save(p69);

        Patient p70 = new Patient();
        p70.setFirstName("Lincoln");
        p70.setLastName("Simmons");
        p70.setPhoneNumber("555-0170");
        p70.setEmail("lincoln.simmons@email.com");
        p70.setDateOfBirth(LocalDate.of(1975, 4, 12));
        p70.setGender("Male");
        p70.setAddress("456 Lavender Way");
        p70.setCity("Naperville");
        p70.setState("IL");
        p70.setZipCode("60544");
        p70.setMedicalHistory("Type 2 Diabetes, Hypertension");
        p70.setRegistrationDate(LocalDate.now());
        p70.setBloodGroup("A-");
        p70.setStatus("Active");
        patientRepository.save(p70);

        Patient p71 = new Patient();
        p71.setFirstName("Hazel");
        p71.setLastName("Foster");
        p71.setPhoneNumber("555-0171");
        p71.setEmail("hazel.foster@email.com");
        p71.setDateOfBirth(LocalDate.of(1992, 1, 25));
        p71.setGender("Female");
        p71.setAddress("789 Magnolia Drive");
        p71.setCity("Naperville");
        p71.setState("IL");
        p71.setZipCode("60545");
        p71.setMedicalHistory("Anxiety, Depression");
        p71.setRegistrationDate(LocalDate.now());
        p71.setBloodGroup("B-");
        p71.setStatus("Active");
        patientRepository.save(p71);

        Patient p72 = new Patient();
        p72.setFirstName("Hudson");
        p72.setLastName("Gonzales");
        p72.setPhoneNumber("555-0172");
        p72.setEmail("hudson.gonzales@email.com");
        p72.setDateOfBirth(LocalDate.of(1983, 9, 18));
        p72.setGender("Male");
        p72.setAddress("234 Narcissus Street");
        p72.setCity("Aurora");
        p72.setState("IL");
        p72.setZipCode("60506");
        p72.setMedicalHistory("Kidney stones");
        p72.setRegistrationDate(LocalDate.now());
        p72.setBloodGroup("O+");
        p72.setStatus("Active");
        patientRepository.save(p72);

        Patient p73 = new Patient();
        p73.setFirstName("Violet");
        p73.setLastName("Bryant");
        p73.setPhoneNumber("555-0173");
        p73.setEmail("violet.bryant@email.com");
        p73.setDateOfBirth(LocalDate.of(1998, 7, 5));
        p73.setGender("Female");
        p73.setAddress("567 Orchid Lane");
        p73.setCity("Aurora");
        p73.setState("IL");
        p73.setZipCode("60507");
        p73.setMedicalHistory("Irregular menstrual cycle");
        p73.setRegistrationDate(LocalDate.now());
        p73.setBloodGroup("AB-");
        p73.setStatus("Active");
        patientRepository.save(p73);

        Patient p74 = new Patient();
        p74.setFirstName("Ezra");
        p74.setLastName("Alexander");
        p74.setPhoneNumber("555-0174");
        p74.setEmail("ezra.alexander@email.com");
        p74.setDateOfBirth(LocalDate.of(1970, 12, 3));
        p74.setGender("Male");
        p74.setAddress("890 Peony Court");
        p74.setCity("Rockford");
        p74.setState("IL");
        p74.setZipCode("61105");
        p74.setMedicalHistory("GERD, Hiatal hernia");
        p74.setRegistrationDate(LocalDate.now());
        p74.setBloodGroup("A+");
        p74.setStatus("Active");
        patientRepository.save(p74);

        Patient p75 = new Patient();
        p75.setFirstName("Nova");
        p75.setLastName("Russell");
        p75.setPhoneNumber("555-0175");
        p75.setEmail("nova.russell@email.com");
        p75.setDateOfBirth(LocalDate.of(1987, 5, 20));
        p75.setGender("Female");
        p75.setAddress("123 Quince Blossom Ave");
        p75.setCity("Rockford");
        p75.setState("IL");
        p75.setZipCode("61106");
        p75.setMedicalHistory("Fibromyalgia, Chronic pain");
        p75.setRegistrationDate(LocalDate.now());
        p75.setBloodGroup("B+");
        p75.setStatus("Active");
        patientRepository.save(p75);
    }

    private void initializeDoctors() {
        Doctor d1 = new Doctor();
        d1.setFirstName("Dr. James");
        d1.setLastName("Wilson");
        d1.setLicenseNumber("LIC001");
        d1.setSpecialization("Cardiology");
        d1.setPhoneNumber("555-1001");
        d1.setEmail("james.wilson@hospital.com");
        d1.setAddress("1111 Medical Plaza");
        d1.setCity("Springfield");
        d1.setState("IL");
        d1.setZipCode("62701");
        d1.setYearsOfExperience("15");
        d1.setStatus("Active");
        d1.setDepartment("Cardiology");
        doctorRepository.save(d1);

        Doctor d2 = new Doctor();
        d2.setFirstName("Dr. Emily");
        d2.setLastName("Davis");
        d2.setLicenseNumber("LIC002");
        d2.setSpecialization("Pediatrics");
        d2.setPhoneNumber("555-1002");
        d2.setEmail("emily.davis@hospital.com");
        d2.setAddress("1111 Medical Plaza");
        d2.setCity("Springfield");
        d2.setState("IL");
        d2.setZipCode("62701");
        d2.setYearsOfExperience("10");
        d2.setStatus("Active");
        d2.setDepartment("Pediatrics");
        doctorRepository.save(d2);

        Doctor d3 = new Doctor();
        d3.setFirstName("Dr. David");
        d3.setLastName("Miller");
        d3.setLicenseNumber("LIC003");
        d3.setSpecialization("Neurology");
        d3.setPhoneNumber("555-1003");
        d3.setEmail("david.miller@hospital.com");
        d3.setAddress("1111 Medical Plaza");
        d3.setCity("Springfield");
        d3.setState("IL");
        d3.setZipCode("62701");
        d3.setYearsOfExperience("12");
        d3.setStatus("Active");
        d3.setDepartment("Neurology");
        doctorRepository.save(d3);

        Doctor d4 = new Doctor();
        d4.setFirstName("Dr. Lisa");
        d4.setLastName("Anderson");
        d4.setLicenseNumber("LIC004");
        d4.setSpecialization("Orthopedics");
        d4.setPhoneNumber("555-1004");
        d4.setEmail("lisa.anderson@hospital.com");
        d4.setAddress("1111 Medical Plaza");
        d4.setCity("Springfield");
        d4.setState("IL");
        d4.setZipCode("62701");
        d4.setYearsOfExperience("8");
        d4.setStatus("Active");
        d4.setDepartment("Orthopedics");
        doctorRepository.save(d4);

        Doctor d5 = new Doctor();
        d5.setFirstName("Dr. Thomas");
        d5.setLastName("Martinez");
        d5.setLicenseNumber("LIC005");
        d5.setSpecialization("General Practice");
        d5.setPhoneNumber("555-1005");
        d5.setEmail("thomas.martinez@hospital.com");
        d5.setAddress("1111 Medical Plaza");
        d5.setCity("Springfield");
        d5.setState("IL");
        d5.setZipCode("62701");
        d5.setYearsOfExperience("20");
        d5.setStatus("Active");
        d5.setDepartment("General");
        doctorRepository.save(d5);

        Doctor d6 = new Doctor();
        d6.setFirstName("Dr. Patricia");
        d6.setLastName("Jackson");
        d6.setLicenseNumber("LIC006");
        d6.setSpecialization("Dermatology");
        d6.setPhoneNumber("555-1006");
        d6.setEmail("patricia.jackson@hospital.com");
        d6.setAddress("1111 Medical Plaza");
        d6.setCity("Springfield");
        d6.setState("IL");
        d6.setZipCode("62701");
        d6.setYearsOfExperience("14");
        d6.setStatus("Active");
        d6.setDepartment("Dermatology");
        doctorRepository.save(d6);

        Doctor d7 = new Doctor();
        d7.setFirstName("Dr. Christopher");
        d7.setLastName("White");
        d7.setLicenseNumber("LIC007");
        d7.setSpecialization("Gastroenterology");
        d7.setPhoneNumber("555-1007");
        d7.setEmail("christopher.white@hospital.com");
        d7.setAddress("1111 Medical Plaza");
        d7.setCity("Springfield");
        d7.setState("IL");
        d7.setZipCode("62701");
        d7.setYearsOfExperience("16");
        d7.setStatus("Active");
        d7.setDepartment("Gastroenterology");
        doctorRepository.save(d7);

        Doctor d8 = new Doctor();
        d8.setFirstName("Dr. Margaret");
        d8.setLastName("Green");
        d8.setLicenseNumber("LIC008");
        d8.setSpecialization("Oncology");
        d8.setPhoneNumber("555-1008");
        d8.setEmail("margaret.green@hospital.com");
        d8.setAddress("1111 Medical Plaza");
        d8.setCity("Springfield");
        d8.setState("IL");
        d8.setZipCode("62701");
        d8.setYearsOfExperience("18");
        d8.setStatus("Active");
        d8.setDepartment("Oncology");
        doctorRepository.save(d8);

        Doctor d9 = new Doctor();
        d9.setFirstName("Dr. Steven");
        d9.setLastName("Harris");
        d9.setLicenseNumber("LIC009");
        d9.setSpecialization("Urology");
        d9.setPhoneNumber("555-1009");
        d9.setEmail("steven.harris@hospital.com");
        d9.setAddress("1111 Medical Plaza");
        d9.setCity("Springfield");
        d9.setState("IL");
        d9.setZipCode("62701");
        d9.setYearsOfExperience("11");
        d9.setStatus("Active");
        d9.setDepartment("Urology");
        doctorRepository.save(d9);

        Doctor d10 = new Doctor();
        d10.setFirstName("Dr. Linda");
        d10.setLastName("Martin");
        d10.setLicenseNumber("LIC010");
        d10.setSpecialization("Pulmonology");
        d10.setPhoneNumber("555-1010");
        d10.setEmail("linda.martin@hospital.com");
        d10.setAddress("1111 Medical Plaza");
        d10.setCity("Springfield");
        d10.setState("IL");
        d10.setZipCode("62701");
        d10.setYearsOfExperience("19");
        d10.setStatus("Active");
        d10.setDepartment("Pulmonology");
        doctorRepository.save(d10);
    }

    private void initializeAppointments() {
        // Fetch saved doctors and patients
        var doctors = doctorRepository.findAll();
        var patients = patientRepository.findAll();

        if (!doctors.isEmpty() && !patients.isEmpty()) {
            // Appointment reasons and notes for variety
            String[] reasons = {
                "Regular checkup", "Follow-up visit", "Consultation", "Lab results review",
                "Medication adjustment", "Annual physical", "Specialist referral", "Symptom evaluation",
                "Chronic condition management", "Preventive care screening", "Blood work", "X-ray review",
                "Vaccination", "Physical therapy", "Post-surgery follow-up"
            };
            String[] notes = {
                "Bring previous reports", "Fasting required", "Monitor vitals",
                "Discuss treatment options", "Review lab results", "Update medical history",
                "Check medication dosage", "Blood pressure monitoring", "Discuss symptoms",
                "Follow-up required", "Insurance verification needed", "Bring ID documents",
                "Arrive 15 min early", "Complete forms online", "Wear comfortable clothes"
            };
            String[] priorities = {"Low", "Medium", "High"};
            // Include Completed status for some appointments
            String[] statuses = {"Scheduled", "Confirmed", "Pending", "Completed", "Completed"};

            int doctorCount = doctors.size();
            int startDay = 10; // Starting from March 10, 2026

            // Create appointments for ALL patients (75 patients)
            for (int i = 0; i < patients.size(); i++) {
                Appointment apt = new Appointment();
                apt.setPatient(patients.get(i));
                apt.setDoctor(doctors.get(i % doctorCount)); // Rotate through doctors
                
                // Schedule appointments across different days and times
                int hour = 9 + (i % 8); // Hours from 9 AM to 4 PM
                int minute = (i % 2 == 0) ? 0 : 30;
                int daysOffset = i / 8; // 8 appointments per day
                
                // Calculate month and day (March has 31 days, then April)
                int totalDay = startDay + daysOffset;
                int month = 3; // March
                int day = totalDay;
                if (totalDay > 31) {
                    month = 4; // April
                    day = totalDay - 31;
                }
                
                // Make past appointments (before March 10, 2026) as Completed
                String status;
                if (i < 20) {
                    // First 20 patients have completed appointments (in early March)
                    apt.setAppointmentDateTime(LocalDateTime.of(2026, 3, 1 + (i % 9), hour, minute));
                    status = "Completed";
                } else {
                    apt.setAppointmentDateTime(LocalDateTime.of(2026, month, Math.min(day, 30), hour, minute));
                    status = statuses[i % statuses.length];
                }
                
                apt.setStatus(status);
                apt.setReason(reasons[i % reasons.length]);
                apt.setNotes(notes[i % notes.length]);
                apt.setCreatedAt(LocalDateTime.now());
                apt.setPriority(priorities[i % priorities.length]);
                appointmentRepository.save(apt);
            }

            // Add completed follow-up appointments for the new patients (p66-p75)
            // These are completed appointments from early March
            Appointment comp1 = new Appointment();
            comp1.setPatient(patients.get(patients.size() - 10)); // p66
            comp1.setDoctor(doctors.get(2)); // Orthopedics
            comp1.setAppointmentDateTime(LocalDateTime.of(2026, 3, 2, 9, 0));
            comp1.setStatus("Completed");
            comp1.setReason("Lower back pain evaluation");
            comp1.setNotes("X-ray completed, physical therapy recommended");
            comp1.setCreatedAt(LocalDateTime.now());
            comp1.setPriority("High");
            appointmentRepository.save(comp1);

            Appointment comp2 = new Appointment();
            comp2.setPatient(patients.get(patients.size() - 9)); // p67
            comp2.setDoctor(doctors.get(9)); // Pulmonology
            comp2.setAppointmentDateTime(LocalDateTime.of(2026, 3, 3, 10, 30));
            comp2.setStatus("Completed");
            comp2.setReason("Asthma management review");
            comp2.setNotes("Inhaler prescription renewed, peak flow test done");
            comp2.setCreatedAt(LocalDateTime.now());
            comp2.setPriority("Medium");
            appointmentRepository.save(comp2);

            Appointment comp3 = new Appointment();
            comp3.setPatient(patients.get(patients.size() - 8)); // p68
            comp3.setDoctor(doctors.get(0)); // Cardiology
            comp3.setAppointmentDateTime(LocalDateTime.of(2026, 3, 4, 11, 0));
            comp3.setStatus("Completed");
            comp3.setReason("Cardiac stress test follow-up");
            comp3.setNotes("ECG normal, medication continued");
            comp3.setCreatedAt(LocalDateTime.now());
            comp3.setPriority("High");
            appointmentRepository.save(comp3);

            Appointment comp4 = new Appointment();
            comp4.setPatient(patients.get(patients.size() - 7)); // p69
            comp4.setDoctor(doctors.get(4)); // Rheumatology
            comp4.setAppointmentDateTime(LocalDateTime.of(2026, 3, 5, 14, 0));
            comp4.setStatus("Completed");
            comp4.setReason("Rheumatoid arthritis treatment review");
            comp4.setNotes("Joint inflammation reduced, continue current medication");
            comp4.setCreatedAt(LocalDateTime.now());
            comp4.setPriority("Medium");
            appointmentRepository.save(comp4);

            Appointment comp5 = new Appointment();
                       comp5.setPatient(patients.get(patients.size() - 6)); // p70
            comp5.setDoctor(doctors.get(3)); // Internal Medicine
           
            comp5.setAppointmentDateTime(LocalDateTime.of(2026, 3,  6, 9, 30));
            comp5.setStatus("Completed");
            comp5.setReason("Diabetes and hypertension checkup");
            comp5.setNotes("HbA1c levels improved, BP under control");
            comp5.setCreatedAt(LocalDateTime.now());
            comp5.setPriority("High");
            appointmentRepository.save(comp5);

            Appointment comp6 = new Appointment();
            comp6.setPatient(patients.get(patients.size() - 5)); // p71
            comp6.setDoctor(doctors.get(5)); // Psychiatry
            comp6.setAppointmentDateTime(LocalDateTime.of(2026, 3, 7, 15, 0));
            comp6.setStatus("Completed");
            comp6.setReason("Anxiety and depression therapy session");
            comp6.setNotes("CBT session completed, follow-up in 2 weeks");
            comp6.setCreatedAt(LocalDateTime.now());
            comp6.setPriority("Medium");
            appointmentRepository.save(comp6);

            Appointment comp7 = new Appointment();
            comp7.setPatient(patients.get(patients.size() - 4)); // p72
            comp7.setDoctor(doctors.get(8)); // Urology
            comp7.setAppointmentDateTime(LocalDateTime.of(2026, 3, 8, 10, 0));
            comp7.setStatus("Completed");
            comp7.setReason("Kidney stones follow-up");
            comp7.setNotes("Ultrasound clear, dietary changes recommended");
            comp7.setCreatedAt(LocalDateTime.now());
            comp7.setPriority("Medium");
            appointmentRepository.save(comp7);

            Appointment comp8 = new Appointment();
            comp8.setPatient(patients.get(patients.size() - 3)); // p73
            comp8.setDoctor(doctors.get(1)); // Gynecology
            comp8.setAppointmentDateTime(LocalDateTime.of(2026, 3, 9, 11, 30));
            comp8.setStatus("Completed");
            comp8.setReason("Menstrual irregularity consultation");
            comp8.setNotes("Hormone panel ordered, birth control prescribed");
            comp8.setCreatedAt(LocalDateTime.now());
            comp8.setPriority("Low");
            appointmentRepository.save(comp8);

            // Additional follow-up appointments (some completed, some scheduled)
            Appointment fu1 = new Appointment();
            fu1.setPatient(patients.get(0));
            fu1.setDoctor(doctors.get(0));
            fu1.setAppointmentDateTime(LocalDateTime.of(2026, 3, 25, 10, 0));
            fu1.setStatus("Scheduled");
            fu1.setReason("Hypertension follow-up");
            fu1.setNotes("Check BP readings from home monitoring");
            fu1.setCreatedAt(LocalDateTime.now());
            fu1.setPriority("High");
            appointmentRepository.save(fu1);

            Appointment fu2 = new Appointment();
            fu2.setPatient(patients.get(4));
            fu2.setDoctor(doctors.get(0));
            fu2.setAppointmentDateTime(LocalDateTime.of(2026, 3, 26, 11, 30));
            fu2.setStatus("Confirmed");
            fu2.setReason("Cardiac checkup - ECG review");
            fu2.setNotes("Bring recent ECG results");
            fu2.setCreatedAt(LocalDateTime.now());
            fu2.setPriority("High");
            appointmentRepository.save(fu2);

            Appointment fu3 = new Appointment();
            fu3.setPatient(patients.get(12));
            fu3.setDoctor(doctors.get(7));
            fu3.setAppointmentDateTime(LocalDateTime.of(2026, 3, 27, 14, 0));
            fu3.setStatus("Scheduled");
            fu3.setReason("COPD management review");
            fu3.setNotes("Pulmonary function test scheduled");
            fu3.setCreatedAt(LocalDateTime.now());
            fu3.setPriority("Medium");
            appointmentRepository.save(fu3);

            Appointment fu4 = new Appointment();
            fu4.setPatient(patients.get(22));
            fu4.setDoctor(doctors.get(0));
            fu4.setAppointmentDateTime(LocalDateTime.of(2026, 3, 28, 9, 30));
            fu4.setStatus("Scheduled");
            fu4.setReason("Atrial fibrillation monitoring");
            fu4.setNotes("ECG and rhythm check");
            fu4.setCreatedAt(LocalDateTime.now());
            fu4.setPriority("High");
            appointmentRepository.save(fu4);

            Appointment fu5 = new Appointment();
            fu5.setPatient(patients.get(14));
            fu5.setDoctor(doctors.get(3));
            fu5.setAppointmentDateTime(LocalDateTime.of(2026, 3, 29, 15, 0));
            fu5.setStatus("Confirmed");
            fu5.setReason("Diabetes management consultation");
            fu5.setNotes("HbA1c test results review");
            fu5.setCreatedAt(LocalDateTime.now());
            fu5.setPriority("Medium");
            appointmentRepository.save(fu5);

            // Auto-accept ALL Scheduled, Pending, and Confirmed appointments
            List<Appointment> allApts = appointmentRepository.findAll();
            int acceptedCount = 0;
            for (Appointment a : allApts) {
                String st = a.getStatus();
                if ("Scheduled".equals(st) || "Pending".equals(st) || "Confirmed".equals(st)) {
                    a.setStatus("Accepted");
                    appointmentRepository.save(a);
                    acceptedCount++;
                }
            }
            System.out.println("✅ Auto-accepted " + acceptedCount + " appointments on startup");
        }
    }

    private void initializeStaff() {
        HospitalStaff s1 = new HospitalStaff();
        s1.setFirstName("Maria");
        s1.setLastName("Garcia");
        s1.setPosition("Nurse");
        s1.setDepartment("Cardiology");
        s1.setPhoneNumber("555-2001");
        s1.setEmail("maria.garcia@hospital.com");
        s1.setAddress("2222 Staff Quarters");
        s1.setJoinDate(LocalDateTime.of(2022, 1, 15, 0, 0));
        s1.setStatus("Active");
        s1.setSalary("45000");
        hospitalStaffRepository.save(s1);

        HospitalStaff s2 = new HospitalStaff();
        s2.setFirstName("Kevin");
        s2.setLastName("Lee");
        s2.setPosition("Lab Technician");
        s2.setDepartment("Laboratory");
        s2.setPhoneNumber("555-2002");
        s2.setEmail("kevin.lee@hospital.com");
        s2.setAddress("2222 Staff Quarters");
        s2.setJoinDate(LocalDateTime.of(2021, 6, 10, 0, 0));
        s2.setStatus("Active");
        s2.setSalary("38000");
        hospitalStaffRepository.save(s2);

        HospitalStaff s3 = new HospitalStaff();
        s3.setFirstName("Angela");
        s3.setLastName("Clark");
        s3.setPosition("Receptionist");
        s3.setDepartment("Administration");
        s3.setPhoneNumber("555-2003");
        s3.setEmail("angela.clark@hospital.com");
        s3.setAddress("2222 Staff Quarters");
        s3.setJoinDate(LocalDateTime.of(2023, 3, 20, 0, 0));
        s3.setStatus("Active");
        s3.setSalary("32000");
        hospitalStaffRepository.save(s3);

        HospitalStaff s4 = new HospitalStaff();
        s4.setFirstName("Richard");
        s4.setLastName("Lopez");
        s4.setPosition("Pharmacist");
        s4.setDepartment("Pharmacy");
        s4.setPhoneNumber("555-2004");
        s4.setEmail("richard.lopez@hospital.com");
        s4.setAddress("2222 Staff Quarters");
        s4.setJoinDate(LocalDateTime.of(2020, 11, 5, 0, 0));
        s4.setStatus("Active");
        s4.setSalary("52000");
        hospitalStaffRepository.save(s4);

        HospitalStaff s5 = new HospitalStaff();
        s5.setFirstName("Diana");
        s5.setLastName("Taylor");
        s5.setPosition("Nurse Supervisor");
        s5.setDepartment("Pediatrics");
        s5.setPhoneNumber("555-2005");
        s5.setEmail("diana.taylor@hospital.com");
        s5.setAddress("2222 Staff Quarters");
        s5.setJoinDate(LocalDateTime.of(2019, 8, 15, 0, 0));
        s5.setStatus("Active");
        s5.setSalary("55000");
        hospitalStaffRepository.save(s5);

        // Additional Staff Members
        HospitalStaff s6 = new HospitalStaff();
        s6.setFirstName("James");
        s6.setLastName("Wilson");
        s6.setPosition("Radiologist Technician");
        s6.setDepartment("Radiology");
        s6.setPhoneNumber("555-2006");
        s6.setEmail("james.wilson@hospital.com");
        s6.setAddress("1234 Oak Street");
        s6.setJoinDate(LocalDateTime.of(2021, 3, 10, 0, 0));
        s6.setStatus("Active");
        s6.setSalary("48000");
        hospitalStaffRepository.save(s6);

        HospitalStaff s7 = new HospitalStaff();
        s7.setFirstName("Sophia");
        s7.setLastName("Martinez");
        s7.setPosition("Nurse");
        s7.setDepartment("Emergency");
        s7.setPhoneNumber("555-2007");
        s7.setEmail("sophia.martinez@hospital.com");
        s7.setAddress("5678 Pine Avenue");
        s7.setJoinDate(LocalDateTime.of(2022, 5, 25, 0, 0));
        s7.setStatus("Active");
        s7.setSalary("46000");
        hospitalStaffRepository.save(s7);

        HospitalStaff s8 = new HospitalStaff();
        s8.setFirstName("Michael");
        s8.setLastName("Brown");
        s8.setPosition("Physical Therapist");
        s8.setDepartment("Orthopedics");
        s8.setPhoneNumber("555-2008");
        s8.setEmail("michael.brown@hospital.com");
        s8.setAddress("9012 Maple Drive");
        s8.setJoinDate(LocalDateTime.of(2020, 9, 15, 0, 0));
        s8.setStatus("Active");
        s8.setSalary("58000");
        hospitalStaffRepository.save(s8);

        HospitalStaff s9 = new HospitalStaff();
        s9.setFirstName("Emma");
        s9.setLastName("Davis");
        s9.setPosition("Medical Records Clerk");
        s9.setDepartment("Administration");
        s9.setPhoneNumber("555-2009");
        s9.setEmail("emma.davis@hospital.com");
        s9.setAddress("3456 Cedar Lane");
        s9.setJoinDate(LocalDateTime.of(2023, 1, 8, 0, 0));
        s9.setStatus("Active");
        s9.setSalary("35000");
        hospitalStaffRepository.save(s9);

        HospitalStaff s10 = new HospitalStaff();
        s10.setFirstName("Daniel");
        s10.setLastName("Anderson");
        s10.setPosition("Surgical Technician");
        s10.setDepartment("Surgery");
        s10.setPhoneNumber("555-2010");
        s10.setEmail("daniel.anderson@hospital.com");
        s10.setAddress("7890 Birch Road");
        s10.setJoinDate(LocalDateTime.of(2019, 4, 20, 0, 0));
        s10.setStatus("Active");
        s10.setSalary("52000");
        hospitalStaffRepository.save(s10);

        HospitalStaff s11 = new HospitalStaff();
        s11.setFirstName("Olivia");
        s11.setLastName("Thomas");
        s11.setPosition("Dietitian");
        s11.setDepartment("Nutrition");
        s11.setPhoneNumber("555-2011");
        s11.setEmail("olivia.thomas@hospital.com");
        s11.setAddress("2345 Elm Street");
        s11.setJoinDate(LocalDateTime.of(2021, 7, 12, 0, 0));
        s11.setStatus("Active");
        s11.setSalary("47000");
        hospitalStaffRepository.save(s11);

        HospitalStaff s12 = new HospitalStaff();
        s12.setFirstName("William");
        s12.setLastName("Jackson");
        s12.setPosition("Security Officer");
        s12.setDepartment("Security");
        s12.setPhoneNumber("555-2012");
        s12.setEmail("william.jackson@hospital.com");
        s12.setAddress("6789 Spruce Way");
        s12.setJoinDate(LocalDateTime.of(2020, 2, 28, 0, 0));
        s12.setStatus("Active");
        s12.setSalary("38000");
        hospitalStaffRepository.save(s12);

        HospitalStaff s13 = new HospitalStaff();
        s13.setFirstName("Isabella");
        s13.setLastName("White");
        s13.setPosition("Nurse");
        s13.setDepartment("ICU");
        s13.setPhoneNumber("555-2013");
        s13.setEmail("isabella.white@hospital.com");
        s13.setAddress("1357 Walnut Court");
        s13.setJoinDate(LocalDateTime.of(2022, 11, 5, 0, 0));
        s13.setStatus("Active");
        s13.setSalary("50000");
        hospitalStaffRepository.save(s13);

        HospitalStaff s14 = new HospitalStaff();
        s14.setFirstName("Alexander");
        s14.setLastName("Harris");
        s14.setPosition("Maintenance Technician");
        s14.setDepartment("Facilities");
        s14.setPhoneNumber("555-2014");
        s14.setEmail("alex.harris@hospital.com");
        s14.setAddress("2468 Aspen Boulevard");
        s14.setJoinDate(LocalDateTime.of(2018, 6, 18, 0, 0));
        s14.setStatus("Active");
        s14.setSalary("36000");
        hospitalStaffRepository.save(s14);

        HospitalStaff s15 = new HospitalStaff();
        s15.setFirstName("Charlotte");
        s15.setLastName("Robinson");
        s15.setPosition("Social Worker");
        s15.setDepartment("Patient Services");
        s15.setPhoneNumber("555-2015");
        s15.setEmail("charlotte.robinson@hospital.com");
        s15.setAddress("3690 Hickory Place");
        s15.setJoinDate(LocalDateTime.of(2021, 10, 22, 0, 0));
        s15.setStatus("Active");
        s15.setSalary("44000");
        hospitalStaffRepository.save(s15);

        HospitalStaff s16 = new HospitalStaff();
        s16.setFirstName("Benjamin");
        s16.setLastName("Lewis");
        s16.setPosition("IT Support Specialist");
        s16.setDepartment("IT");
        s16.setPhoneNumber("555-2016");
        s16.setEmail("benjamin.lewis@hospital.com");
        s16.setAddress("4812 Redwood Lane");
        s16.setJoinDate(LocalDateTime.of(2020, 8, 14, 0, 0));
        s16.setStatus("Active");
        s16.setSalary("55000");
        hospitalStaffRepository.save(s16);

        HospitalStaff s17 = new HospitalStaff();
        s17.setFirstName("Amelia");
        s17.setLastName("Walker");
        s17.setPosition("Billing Specialist");
        s17.setDepartment("Finance");
        s17.setPhoneNumber("555-2017");
        s17.setEmail("amelia.walker@hospital.com");
        s17.setAddress("5934 Cypress Street");
        s17.setJoinDate(LocalDateTime.of(2022, 4, 3, 0, 0));
        s17.setStatus("Active");
        s17.setSalary("42000");
        hospitalStaffRepository.save(s17);

        HospitalStaff s18 = new HospitalStaff();
        s18.setFirstName("Ethan");
        s18.setLastName("Hall");
        s18.setPosition("Respiratory Therapist");
        s18.setDepartment("Pulmonology");
        s18.setPhoneNumber("555-2018");
        s18.setEmail("ethan.hall@hospital.com");
        s18.setAddress("6056 Sequoia Circle");
        s18.setJoinDate(LocalDateTime.of(2019, 12, 1, 0, 0));
        s18.setStatus("Active");
        s18.setSalary("54000");
        hospitalStaffRepository.save(s18);

        HospitalStaff s19 = new HospitalStaff();
        s19.setFirstName("Mia");
        s19.setLastName("Young");
        s19.setPosition("Nurse");
        s19.setDepartment("Maternity");
        s19.setPhoneNumber("555-2019");
        s19.setEmail("mia.young@hospital.com");
        s19.setAddress("7178 Magnolia Way");
        s19.setJoinDate(LocalDateTime.of(2023, 2, 14, 0, 0));
        s19.setStatus("Active");
        s19.setSalary("47000");
        hospitalStaffRepository.save(s19);

        HospitalStaff s20 = new HospitalStaff();
        s20.setFirstName("Lucas");
        s20.setLastName("King");
        s20.setPosition("Anesthesia Technician");
        s20.setDepartment("Anesthesiology");
        s20.setPhoneNumber("555-2020");
        s20.setEmail("lucas.king@hospital.com");
        s20.setAddress("8290 Dogwood Drive");
        s20.setJoinDate(LocalDateTime.of(2021, 5, 19, 0, 0));
        s20.setStatus("Active");
        s20.setSalary("51000");
        hospitalStaffRepository.save(s20);

        HospitalStaff s21 = new HospitalStaff();
        s21.setFirstName("Harper");
        s21.setLastName("Scott");
        s21.setPosition("Patient Care Coordinator");
        s21.setDepartment("Patient Services");
        s21.setPhoneNumber("555-2021");
        s21.setEmail("harper.scott@hospital.com");
        s21.setAddress("9412 Willow Avenue");
        s21.setJoinDate(LocalDateTime.of(2020, 10, 8, 0, 0));
        s21.setStatus("Active");
        s21.setSalary("43000");
        hospitalStaffRepository.save(s21);

        HospitalStaff s22 = new HospitalStaff();
        s22.setFirstName("Henry");
        s22.setLastName("Green");
        s22.setPosition("Phlebotomist");
        s22.setDepartment("Laboratory");
        s22.setPhoneNumber("555-2022");
        s22.setEmail("henry.green@hospital.com");
        s22.setAddress("1023 Cherry Lane");
        s22.setJoinDate(LocalDateTime.of(2022, 8, 27, 0, 0));
        s22.setStatus("Active");
        s22.setSalary("37000");
        hospitalStaffRepository.save(s22);

        HospitalStaff s23 = new HospitalStaff();
        s23.setFirstName("Evelyn");
        s23.setLastName("Adams");
        s23.setPosition("Occupational Therapist");
        s23.setDepartment("Rehabilitation");
        s23.setPhoneNumber("555-2023");
        s23.setEmail("evelyn.adams@hospital.com");
        s23.setAddress("2145 Poplar Street");
        s23.setJoinDate(LocalDateTime.of(2019, 7, 11, 0, 0));
        s23.setStatus("Active");
        s23.setSalary("56000");
        hospitalStaffRepository.save(s23);

        HospitalStaff s24 = new HospitalStaff();
        s24.setFirstName("Sebastian");
        s24.setLastName("Nelson");
        s24.setPosition("Pharmacy Technician");
        s24.setDepartment("Pharmacy");
        s24.setPhoneNumber("555-2024");
        s24.setEmail("sebastian.nelson@hospital.com");
        s24.setAddress("3267 Juniper Road");
        s24.setJoinDate(LocalDateTime.of(2021, 11, 30, 0, 0));
        s24.setStatus("Active");
        s24.setSalary("40000");
        hospitalStaffRepository.save(s24);

        HospitalStaff s25 = new HospitalStaff();
        s25.setFirstName("Avery");
        s25.setLastName("Carter");
        s25.setPosition("HR Coordinator");
        s25.setDepartment("Human Resources");
        s25.setPhoneNumber("555-2025");
        s25.setEmail("avery.carter@hospital.com");
        s25.setAddress("4389 Acacia Way");
        s25.setJoinDate(LocalDateTime.of(2020, 3, 16, 0, 0));
        s25.setStatus("Active");
        s25.setSalary("48000");
        hospitalStaffRepository.save(s25);
    }

    private void initializePrescriptions() {
        String[] patients = {"John Smith", "Emily Davis", "Robert Johnson", "Maria Garcia", "David Wilson",
            "Jennifer Brown", "Michael Taylor", "Susan Anderson", "William Martinez", "Linda Thomas",
            "James Jackson", "Patricia White", "Richard Harris", "Barbara Martin", "Joseph Thompson",
            "Margaret Garcia", "Charles Robinson", "Dorothy Clark", "Thomas Lewis", "Nancy Lee",
            "Christopher Walker", "Karen Hall", "Daniel Allen", "Betty Young", "Matthew King",
            "Sandra Wright", "Anthony Scott", "Donna Green", "Mark Adams", "Carol Baker"};
        String[] doctors = {"Dr. Sarah Wilson", "Dr. Michael Chen", "Dr. Lisa Anderson", "Dr. James Brown",
            "Dr. Emily Roberts", "Dr. David Kim", "Dr. Jennifer Lopez", "Dr. Robert Martinez"};
        String[][] medicinesList = {
            {"Amoxicillin 250mg", "Paracetamol 500mg"},
            {"Metoprolol 50mg", "Gabapentin 300mg"},
            {"Lisinopril 10mg", "Amlodipine 5mg"},
            {"Omeprazole 20mg", "Cetirizine 10mg"},
            {"Azithromycin 500mg", "Ibuprofen 400mg"},
            {"Aspirin 100mg", "Ciprofloxacin 500mg"},
            {"Metformin 500mg", "Atorvastatin 20mg"},
            {"Losartan 50mg", "Hydrochlorothiazide 25mg"},
            {"Prednisone 10mg", "Pantoprazole 40mg"},
            {"Cephalexin 500mg", "Diclofenac 50mg"}
        };

        for (int i = 0; i < 56; i++) {
            int rxNum = 892 - i;
            int daysAgo = i / 8;
            LocalDate rxDate = LocalDate.of(2026, 3, 10).minusDays(daysAgo);

            int medIdx = i % medicinesList.length;
            String medicines;
            if (i % 3 == 0) {
                medicines = medicinesList[medIdx][0];
            } else {
                medicines = medicinesList[medIdx][0] + ", " + medicinesList[medIdx][1];
            }

            Prescription rx = new Prescription();
            rx.setRxNo("RX-2026-0" + rxNum);
            rx.setPatientName(patients[i % patients.length]);
            rx.setDoctorName(doctors[i % doctors.length]);
            rx.setMedicines(medicines);
            rx.setPrescriptionDate(rxDate);
            rx.setPriority(i < 12 ? "urgent" : "normal");
            rx.setStatus("Pending");
            rx.setNotes(i % 5 == 0 ? "Take after meals" : "");
            rx.setCreatedAt(rxDate.atStartOfDay().plusHours(9).plusMinutes(i % 60));

            prescriptionRepository.save(rx);
        }
        System.out.println("  → 56 prescriptions initialized");
    }

    private void initializeSuppliers() {
        String[][] supplierData = {
            {"SUP-0001", "PharmaCorp Inc.", "John Reynolds", "+1 (555) 123-4567", "john@pharmacorp.com", "123 Pharma Ave, New York, NY", "Analgesics, Antibiotics, Anti-inflammatory", "Active", "5", "Premium supplier with 10+ years partnership"},
            {"SUP-0002", "MediSupply Co.", "Sarah Mitchell", "+1 (555) 987-6543", "sarah@medisupply.com", "456 Medical Blvd, Chicago, IL", "Cardiovascular, Diabetes, Hypertension", "Active", "4", "Reliable delivery within 48 hours"},
            {"SUP-0003", "Global Pharma Ltd.", "David Chen", "+1 (555) 456-7890", "david@globalpharma.com", "789 Global Dr, San Francisco, CA", "General Medicines, Vitamins, Supplements", "Pending Order", "4", "International supplier with wide range"},
            {"SUP-0004", "BioMed Solutions", "Emily Parker", "+1 (555) 321-6540", "emily@biomed.com", "321 BioTech Park, Boston, MA", "Neurological, Psychiatric, Sleep Aids", "Active", "5", "Specializes in neurological medications"},
            {"SUP-0005", "HealthFirst Distributors", "Michael Johnson", "+1 (555) 654-3210", "michael@healthfirst.com", "654 Health Way, Houston, TX", "Respiratory, Allergy, Pulmonary", "Active", "3", "Competitive pricing on respiratory drugs"},
            {"SUP-0006", "PharmaPlus Ltd.", "Jessica Brown", "+1 (555) 789-0123", "jessica@pharmaplus.com", "987 Plus St, Miami, FL", "Dermatology, Topical, Skincare", "Active", "4", "Fast shipping for dermatology products"},
            {"SUP-0007", "MedLine International", "Robert Davis", "+1 (555) 234-5678", "robert@medline.com", "234 MedLine Rd, Seattle, WA", "Gastrointestinal, Digestive, Antacids", "Inactive", "2", "Currently under review for delayed shipments"},
            {"SUP-0008", "CarePharm Inc.", "Amanda Wilson", "+1 (555) 876-5432", "amanda@carepharm.com", "876 Care Blvd, Denver, CO", "Antibiotics, Antifungal, Antiviral", "Active", "5", "Hospital preferred for antibiotics"},
            {"SUP-0009", "NovaMed Supplies", "Christopher Lee", "+1 (555) 345-6789", "chris@novamed.com", "345 Nova Ave, Phoenix, AZ", "Oncology, Immunotherapy, Specialty", "Active", "4", "Specialty medications with cold-chain delivery"},
            {"SUP-0010", "QuickMed Logistics", "Stephanie Taylor", "+1 (555) 567-8901", "steph@quickmed.com", "567 Quick Ln, Atlanta, GA", "Emergency, Critical Care, IV Solutions", "Active", "5", "24/7 emergency supply available"},
            {"SUP-0011", "WellCare Pharma", "Daniel Martinez", "+1 (555) 432-1098", "daniel@wellcare.com", "432 WellCare Dr, Dallas, TX", "Pediatrics, Vaccines, Immunizations", "Pending Order", "3", "Specializes in pediatric formulations"},
            {"SUP-0012", "PrimeMed Holdings", "Rachel Anderson", "+1 (555) 210-9876", "rachel@primemed.com", "210 Prime Way, Portland, OR", "Orthopedic, Pain Management, Muscle Relaxants", "Active", "4", "Bulk discount available on large orders"}
        };

        int[] daysAgoLastOrder = {5, 2, 10, 3, 7, 4, 30, 1, 6, 0, 12, 8};

        for (int i = 0; i < supplierData.length; i++) {
            String[] d = supplierData[i];
            Supplier s = new Supplier();
            s.setSupplierCode(d[0]);
            s.setName(d[1]);
            s.setContactPerson(d[2]);
            s.setPhone(d[3]);
            s.setEmail(d[4]);
            s.setAddress(d[5]);
            s.setProducts(d[6]);
            s.setStatus(d[7]);
            s.setRating(d[8]);
            s.setNotes(d[9]);
            s.setLastOrderDate(LocalDate.of(2026, 3, 10).minusDays(daysAgoLastOrder[i]));
            s.setCreatedAt(LocalDateTime.of(2025, 1 + (i % 12), 15, 10, 0));
            supplierRepository.save(s);
        }
        System.out.println("  → 12 suppliers initialized");
    }
}
