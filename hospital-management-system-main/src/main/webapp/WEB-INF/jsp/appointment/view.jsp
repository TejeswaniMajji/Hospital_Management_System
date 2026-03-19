<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Appointment</title>
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
                        <li><a href="${pageContext.request.contextPath}/doctor/list" class="nav-link">Doctors</a></li>
                        <li><a href="${pageContext.request.contextPath}/appointment/list" class="nav-link active">Appointments</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <main class="main-content">
            <section class="content-section">
                <div class="section-header">
                    <h2>Appointment Details</h2>
                </div>

                <div class="view-details">
                    <div class="detail-row">
                        <span class="detail-label">Appointment ID:</span>
                        <span class="detail-value">${appointment.appointmentId}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Patient:</span>
                        <span class="detail-value">${appointment.patient.firstName} ${appointment.patient.lastName}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Doctor:</span>
                        <span class="detail-value">${appointment.doctor.firstName} ${appointment.doctor.lastName} (${appointment.doctor.specialization})</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Appointment Date & Time:</span>
                        <span class="detail-value">${appointment.appointmentDateTime}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Reason:</span>
                        <span class="detail-value">${appointment.reason}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Priority:</span>
                        <span class="detail-value"><span class="priority-badge priority-${appointment.priority}">${appointment.priority}</span></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Status:</span>
                        <span class="detail-value"><span class="status-badge status-${appointment.status}">${appointment.status}</span></span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Notes:</span>
                        <span class="detail-value">${appointment.notes}</span>
                    </div>
                    <div class="detail-row">
                        <span class="detail-label">Created At:</span>
                        <span class="detail-value">${appointment.createdAt}</span>
                    </div>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/appointment/edit/${appointment.appointmentId}" class="btn btn-primary">Edit</a>
                    <a href="${pageContext.request.contextPath}/appointment/list" class="btn btn-secondary">Back</a>
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
