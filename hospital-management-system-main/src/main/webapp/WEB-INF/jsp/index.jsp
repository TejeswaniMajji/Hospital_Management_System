<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hospital Management System - Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- Header/Navigation -->
        <header class="header">
            <div class="header-content">
                <h1 class="logo">Hospital Management System</h1>
                <nav class="navbar">
                    <ul class="nav-list">
                        <li><a href="${pageContext.request.contextPath}/" class="nav-link active">Dashboard</a></li>
                        <li><a href="${pageContext.request.contextPath}/patient/list" class="nav-link">Patients</a></li>
                        <li><a href="${pageContext.request.contextPath}/doctor/list" class="nav-link">Doctors</a></li>
                        <li><a href="${pageContext.request.contextPath}/appointment/list" class="nav-link">Appointments</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <!-- Main Content -->
        <main class="main-content">
            <section class="welcome-section">
                <h2>Welcome to Hospital Management System</h2>
                <p>Manage your hospital operations efficiently</p>
            </section>

            <!-- Dashboard Statistics -->
            <section class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-icon">👥</div>
                    <div class="stat-content">
                        <h3>Total Patients</h3>
                        <p class="stat-number">${totalPatients}</p>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon">👨‍⚕️</div>
                    <div class="stat-content">
                        <h3>Total Doctors</h3>
                        <p class="stat-number">${totalDoctors}</p>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon">👔</div>
                    <div class="stat-content">
                        <h3>Total Staff</h3>
                        <p class="stat-number">${totalStaff}</p>
                    </div>
                </div>
            </section>

            <!-- Quick Actions -->
            <section class="quick-actions">
                <h3>Quick Actions</h3>
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/patient/add" class="btn btn-primary">Add Patient</a>
                    <a href="${pageContext.request.contextPath}/doctor/add" class="btn btn-primary">Add Doctor</a>
                    <a href="${pageContext.request.contextPath}/appointment/add" class="btn btn-primary">Schedule Appointment</a>
                </div>
            </section>
        </main>

        <!-- Footer -->
        <footer class="footer">
            <p>&copy; 2026 Hospital Management System. All rights reserved.</p>
        </footer>
    </div>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
