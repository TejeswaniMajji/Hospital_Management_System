<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Doctor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Navigation -->
        <header class="header">
            <div class="header-content">
                <h1 class="logo">Hospital Management System</h1>
                <nav class="navbar">
                    <ul class="nav-list">
                        <li><a href="${pageContext.request.contextPath}/" class="nav-link">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/patient/list" class="nav-link">Patients</a></li>
                        <li><a href="${pageContext.request.contextPath}/doctor/list" class="nav-link active">Doctors</a></li>
                        <li><a href="${pageContext.request.contextPath}/appointment/list" class="nav-link">Appointments</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <main class="main-content">
            <section class="content-section">
                <div class="section-header">
                    <h2>Doctor Details</h2>
                </div>

                <div class="view-details">
                    <div class="detail-row">
                        <span class="detail-label">Doctor ID:</span>
                        <span class="detail-value">${doctor.doctorId}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">First Name:</span>
                        <span class="detail-value">${doctor.firstName}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Last Name:</span>
                        <span class="detail-value">${doctor.lastName}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">License Number:</span>
                        <span class="detail-value">${doctor.licenseNumber}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Specialization:</span>
                        <span class="detail-value">${doctor.specialization}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Department:</span>
                        <span class="detail-value">${doctor.department}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value">${doctor.email}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Phone Number:</span>
                        <span class="detail-value">${doctor.phoneNumber}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Years of Experience:</span>
                        <span class="detail-value">${doctor.yearsOfExperience}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Address:</span>
                        <span class="detail-value">${doctor.address}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">City:</span>
                        <span class="detail-value">${doctor.city}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">State:</span>
                        <span class="detail-value">${doctor.state}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Zip Code:</span>
                        <span class="detail-value">${doctor.zipCode}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value"><span class="status-badge status-${doctor.status}">${doctor.status}</span></span>
                    </div>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/doctor/edit/${doctor.doctorId}" class="btn btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/doctor/list" class="btn btn-secondary">Back</a>
                </div>
            </section>
        </main>

        <footer class="footer">
            <p>&copy; 2026 Hospital Management System. All rights reserved.</p>
        </footer>
    </div>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
