-- Hospital Management System - Core Domain Schema
-- Baseline migration: creates the core tables that were previously auto-generated
-- by Hibernate (ddl-auto=update). Written for H2 (the active datasource).

CREATE TABLE IF NOT EXISTS patients (
    patient_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    address VARCHAR(500),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    medical_history VARCHAR(500),
    registration_date DATE NOT NULL,
    blood_group VARCHAR(50),
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS doctors (
    doctor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    license_number VARCHAR(20) NOT NULL UNIQUE,
    specialization VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(500),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    years_of_experience VARCHAR(50),
    status VARCHAR(50),
    department VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS appointments (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date_time TIMESTAMP NOT NULL,
    status VARCHAR(50),
    reason VARCHAR(500),
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    priority VARCHAR(50),
    follow_up_date TIMESTAMP,
    consultation_expiry_date TIMESTAMP,
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

CREATE TABLE IF NOT EXISTS hospital_staff (
    staff_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    position VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(500),
    join_date TIMESTAMP NOT NULL,
    status VARCHAR(50),
    salary VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS prescriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rx_no VARCHAR(30) NOT NULL UNIQUE,
    patient_name VARCHAR(150) NOT NULL,
    doctor_name VARCHAR(150) NOT NULL,
    medicines VARCHAR(1000) NOT NULL,
    prescription_date DATE NOT NULL,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medicine_name VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(255) NOT NULL,
    stock_level INTEGER NOT NULL,
    min_threshold INTEGER NOT NULL,
    unit_price DOUBLE NOT NULL,
    manufacturer VARCHAR(255),
    location VARCHAR(255),
    last_restocked TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    supplier_code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    contact_person VARCHAR(150) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL,
    address VARCHAR(300),
    products VARCHAR(500) NOT NULL,
    last_order_date DATE,
    status VARCHAR(20) NOT NULL,
    rating VARCHAR(20),
    notes VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
