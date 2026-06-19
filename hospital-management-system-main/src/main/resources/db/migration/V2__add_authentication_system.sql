-- Hospital Management System - User Authentication Tables
-- Purpose: Add user authentication and authorization system
-- (Rewritten for H2: removed MySQL-only syntax such as ENGINE/CHARSET/COLLATE/inline
-- COMMENT clauses, and removed sample user inserts since DataInitializerService
-- already owns seeding and wipes/recreates the users table on every startup.)

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    phone_number VARCHAR(20),
    doctor_id BIGINT,
    patient_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    last_password_change TIMESTAMP,
    failed_login_attempts INT DEFAULT 0,
    account_locked_until TIMESTAMP,
    CONSTRAINT fk_user_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    CONSTRAINT fk_user_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_role ON users(role);
CREATE INDEX idx_status ON users(status);
CREATE INDEX idx_user_role_status ON users(role, status);
CREATE INDEX idx_user_created_at ON users(created_at);
CREATE INDEX idx_user_last_login ON users(last_login);

-- Reference table for available roles
CREATE TABLE IF NOT EXISTS user_roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user_roles (role_name, description) VALUES
('ADMIN', 'System Administrator with full access'),
('DOCTOR', 'Doctor with patient management and prescription abilities'),
('NURSE', 'Nurse with patient observation and appointment management'),
('PATIENT', 'Patient with limited access to own records');

-- Audit trail for login/logout and account changes (not yet written to by the app;
-- schema exists ahead of the FUTURE_ENHANCEMENTS.md "audit trail" item)
CREATE TABLE IF NOT EXISTS user_audit_log (
    audit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    ip_address VARCHAR(45),
    user_agent VARCHAR(255),
    status VARCHAR(20),
    details VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_user_audit ON user_audit_log(user_id);
CREATE INDEX idx_action ON user_audit_log(action);
CREATE INDEX idx_audit_created_at ON user_audit_log(created_at);

-- Fine-grained permission mapping per role
CREATE TABLE IF NOT EXISTS user_permissions (
    permission_id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    permission_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_role_permission UNIQUE (role_id, permission_name),
    CONSTRAINT fk_permission_role FOREIGN KEY (role_id) REFERENCES user_roles(role_id)
);

INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(1, 'VIEW_ALL_PATIENTS', 'Can view all patient records'),
(1, 'EDIT_ALL_PATIENTS', 'Can edit all patient records'),
(1, 'DELETE_PATIENTS', 'Can delete patient records'),
(1, 'MANAGE_USERS', 'Can manage user accounts'),
(1, 'VIEW_REPORTS', 'Can view all reports'),
(1, 'SYSTEM_SETTINGS', 'Can access system settings');

INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(2, 'VIEW_ASSIGNED_PATIENTS', 'Can view assigned patient records'),
(2, 'EDIT_ASSIGNED_PATIENTS', 'Can edit assigned patient records'),
(2, 'WRITE_PRESCRIPTIONS', 'Can write prescriptions'),
(2, 'VIEW_APPOINTMENTS', 'Can view appointments'),
(2, 'MANAGE_APPOINTMENTS', 'Can manage appointments');

INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(3, 'VIEW_ASSIGNED_PATIENTS', 'Can view assigned patient records'),
(3, 'UPDATE_PATIENT_VITALS', 'Can update patient vital signs'),
(3, 'VIEW_APPOINTMENTS', 'Can view appointments'),
(3, 'MANAGE_APPOINTMENTS', 'Can manage appointments');

INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(4, 'VIEW_OWN_RECORDS', 'Can view own patient records'),
(4, 'BOOK_APPOINTMENTS', 'Can book appointments'),
(4, 'VIEW_OWN_APPOINTMENTS', 'Can view own appointments'),
(4, 'VIEW_PRESCRIPTIONS', 'Can view own prescriptions');
