-- Hospital Management System - User Authentication Tables
-- Created: 2026-03-08
-- Purpose: Add user authentication and authorization system

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL COMMENT 'BCrypt encrypted password',
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL COMMENT 'ADMIN, DOCTOR, NURSE, PATIENT',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE, INACTIVE, SUSPENDED',
    phone_number VARCHAR(20),
    doctor_id BIGINT,
    patient_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    last_password_change TIMESTAMP,
    failed_login_attempts INT DEFAULT 0,
    account_locked_until TIMESTAMP,
    CONSTRAINT fk_user_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
    CONSTRAINT fk_user_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create user roles reference table
CREATE TABLE IF NOT EXISTS user_roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default roles
INSERT INTO user_roles (role_name, description) VALUES
('ADMIN', 'System Administrator with full access'),
('DOCTOR', 'Doctor with patient management and prescription abilities'),
('NURSE', 'Nurse with patient observation and appointment management'),
('PATIENT', 'Patient with limited access to own records');

-- Create user audit log table for tracking logins and changes
CREATE TABLE IF NOT EXISTS user_audit_log (
    audit_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL COMMENT 'LOGIN, LOGOUT, PASSWORD_CHANGE, PROFILE_UPDATE',
    ip_address VARCHAR(45),
    user_agent VARCHAR(255),
    status VARCHAR(20) COMMENT 'SUCCESS, FAILED',
    details VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_user_audit (user_id),
    INDEX idx_action (action),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create user permissions table for fine-grained access control
CREATE TABLE IF NOT EXISTS user_permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT,
    role_id INT NOT NULL,
    permission_name VARCHAR(100) NOT NULL COMMENT 'e.g., VIEW_PATIENTS, EDIT_PATIENTS, DELETE_PATIENTS',
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_role_permission (role_id, permission_name),
    FOREIGN KEY (role_id) REFERENCES user_roles(role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default permissions for ADMIN role
INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(1, 'VIEW_ALL_PATIENTS', 'Can view all patient records'),
(1, 'EDIT_ALL_PATIENTS', 'Can edit all patient records'),
(1, 'DELETE_PATIENTS', 'Can delete patient records'),
(1, 'MANAGE_USERS', 'Can manage user accounts'),
(1, 'VIEW_REPORTS', 'Can view all reports'),
(1, 'SYSTEM_SETTINGS', 'Can access system settings');

-- Insert default permissions for DOCTOR role
INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(2, 'VIEW_ASSIGNED_PATIENTS', 'Can view assigned patient records'),
(2, 'EDIT_ASSIGNED_PATIENTS', 'Can edit assigned patient records'),
(2, 'WRITE_PRESCRIPTIONS', 'Can write prescriptions'),
(2, 'VIEW_APPOINTMENTS', 'Can view appointments'),
(2, 'MANAGE_APPOINTMENTS', 'Can manage appointments');

-- Insert default permissions for NURSE role
INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(3, 'VIEW_ASSIGNED_PATIENTS', 'Can view assigned patient records'),
(3, 'UPDATE_PATIENT_VITALS', 'Can update patient vital signs'),
(3, 'VIEW_APPOINTMENTS', 'Can view appointments'),
(3, 'MANAGE_APPOINTMENTS', 'Can manage appointments');

-- Insert default permissions for PATIENT role
INSERT INTO user_permissions (role_id, permission_name, description) VALUES
(4, 'VIEW_OWN_RECORDS', 'Can view own patient records'),
(4, 'BOOK_APPOINTMENTS', 'Can book appointments'),
(4, 'VIEW_OWN_APPOINTMENTS', 'Can view own appointments'),
(4, 'VIEW_PRESCRIPTIONS', 'Can view own prescriptions');

-- Sample data for testing (passwords are BCrypt hashes of "Password123")
-- You can generate BCrypt hashes using the application's password encoder
INSERT INTO users (username, email, password, first_name, last_name, role, status, phone_number) VALUES
('admin', 'admin@hospital.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5YQEw.y6bW0z6', 'Admin', 'User', 'ADMIN', 'ACTIVE', '+1 (555) 000-0001'),
('doctor1', 'doctor1@hospital.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5YQEw.y6bW0z6', 'Dr.', 'Smith', 'DOCTOR', 'ACTIVE', '+1 (555) 000-0002'),
('nurse1', 'nurse1@hospital.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5YQEw.y6bW0z6', 'Jane', 'Nurse', 'NURSE', 'ACTIVE', '+1 (555) 000-0003'),
('patient1', 'patient1@hospital.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5YQEw.y6bW0z6', 'John', 'Patient', 'PATIENT', 'ACTIVE', '+1 (555) 000-0004');

-- Create index for faster lookups
CREATE INDEX idx_user_role_status ON users(role, status);
CREATE INDEX idx_user_created_at ON users(created_at);
CREATE INDEX idx_user_last_login ON users(last_login);

-- Add comments to table
ALTER TABLE users COMMENT = 'Stores user account information with authentication credentials';
ALTER TABLE user_roles COMMENT = 'Defines available user roles in the system';
ALTER TABLE user_audit_log COMMENT = 'Audit trail for user actions and login attempts';
ALTER TABLE user_permissions COMMENT = 'Maps permissions to roles for fine-grained access control';

-- Display confirmation
SELECT 'User authentication system tables created successfully!' as status;

-- Verify tables
SHOW TABLES LIKE 'user%';

-- Show users table structure
DESCRIBE users;
