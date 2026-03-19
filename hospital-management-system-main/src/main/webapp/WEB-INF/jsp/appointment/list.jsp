<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment List</title>
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
                    <h2>Appointment Management</h2>
                    <a href="${pageContext.request.contextPath}/appointment/add" class="btn btn-primary">Schedule New Appointment</a>
                </div>

                <!-- Search Form -->
                <div class="search-form">
                    <h3>Search Appointments</h3>
                    <form action="${pageContext.request.contextPath}/appointment/search" method="POST" class="form-inline">
                        <select name="searchType" class="form-control">
                            <option value="status">Status</option>
                            <option value="priority">Priority</option>
                        </select>
                        <input type="text" name="searchValue" placeholder="Enter search value" class="form-control" required>
                        <button type="submit" class="btn btn-secondary">Search</button>
                    </form>
                </div>

                <!-- Appointment Table -->
                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty appointments}">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Patient</th>
                                        <th>Doctor</th>
                                        <th>Date & Time</th>
                                        <th>Reason</th>
                                        <th>Priority</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="appointment" items="${appointments}">
                                        <tr>
                                            <td>${appointment.appointmentId}</td>
                                            <td>${appointment.patient.firstName} ${appointment.patient.lastName}</td>
                                            <td>${appointment.doctor.firstName} ${appointment.doctor.lastName}</td>
                                            <td>${appointment.appointmentDateTime}</td>
                                            <td>${appointment.reason}</td>
                                            <td><span class="priority-badge priority-${appointment.priority}">${appointment.priority}</span></td>
                                            <td><span class="status-badge status-${appointment.status}">${appointment.status}</span></td>
                                            <td class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/appointment/view/${appointment.appointmentId}" class="btn-link btn-view">View</a>
                                                <a href="${pageContext.request.contextPath}/appointment/edit/${appointment.appointmentId}" class="btn-link btn-edit">Edit</a>
                                                <a href="${pageContext.request.contextPath}/appointment/delete/${appointment.appointmentId}" class="btn-link btn-delete" onclick="return confirm('Are you sure?')">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="no-data">No appointments found. <a href="${pageContext.request.contextPath}/appointment/add">Schedule a new appointment</a></p>
                        </c:otherwise>
                    </c:choose>
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
