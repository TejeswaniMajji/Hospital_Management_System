<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Patient</title>
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
                        <li><a href="${pageContext.request.contextPath}/patient/list" class="nav-link active">Patients</a></li>
                        <li><a href="${pageContext.request.contextPath}/doctor/list" class="nav-link">Doctors</a></li>
                        <li><a href="${pageContext.request.contextPath}/appointment/list" class="nav-link">Appointments</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <main class="main-content">
            <section class="content-section">
                <div class="section-header">
                    <h2>Patient Details</h2>
                </div>

                <div class="view-details">
                    <div class="detail-row">
                        <span class="detail-label">Patient ID:</span>
                        <span class="detail-value">${patient.patientId}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">First Name:</span>
                        <span class="detail-value">${patient.firstName}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Last Name:</span>
                        <span class="detail-value">${patient.lastName}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Email:</span>
                        <span class="detail-value">${patient.email}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Phone Number:</span>
                        <span class="detail-value">${patient.phoneNumber}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Date of Birth:</span>
                        <span class="detail-value">${patient.dateOfBirth}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Gender:</span>
                        <span class="detail-value">${patient.gender}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Blood Group:</span>
                        <span class="detail-value">${patient.bloodGroup}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Address:</span>
                        <span class="detail-value">${patient.address}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">City:</span>
                        <span class="detail-value">${patient.city}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">State:</span>
                        <span class="detail-value">${patient.state}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Zip Code:</span>
                        <span class="detail-value">${patient.zipCode}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Registration Date:</span>
                        <span class="detail-value">${patient.registrationDate}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value"><span class="status-badge status-${patient.status}">${patient.status}</span></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Medical History:</span>
                        <span class="detail-value">${patient.medicalHistory}</span>
                    </div>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/patient/edit/${patient.patientId}" class="btn btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/patient/list" class="btn btn-secondary">Back</a>
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
