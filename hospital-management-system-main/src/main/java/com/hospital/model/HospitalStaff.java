package com.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Hospital Staff Entity - Represents hospital staff members
 */
@Entity
@Table(name = "hospital_staff")
public class HospitalStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 500)
    private String address;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(length = 50)
    private String status;

    @Column(length = 20)
    private String salary;

    public HospitalStaff() {}

    public HospitalStaff(Long staffId, String firstName, String lastName, String position, String department, String phoneNumber, String email, String address, LocalDateTime joinDate, String status, String salary) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.joinDate = joinDate;
        this.status = status;
        this.salary = salary;
    }

    // Getters and setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public LocalDateTime getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDateTime joinDate) { this.joinDate = joinDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
}
