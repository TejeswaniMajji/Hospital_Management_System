<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Appointment</title>
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
                    <h2>Schedule New Appointment</h2>
                </div>

                <form action="${pageContext.request.contextPath}/appointment/save" method="POST" class="form-horizontal">
                    <div class="form-group">
                        <label for="patientId">Patient:</label>
                        <select id="patientId" name="patient.patientId" class="form-control" required>
                            <option value="">Select Patient</option>
                            <c:forEach var="patient" items="${patients}">
                                <option value="${patient.patientId}">${patient.firstName} ${patient.lastName} (${patient.email})</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="doctorId">Doctor:</label>
                        <select id="doctorId" name="doctor.doctorId" class="form-control" required>
                            <option value="">Select Doctor</option>
                            <c:forEach var="doctor" items="${doctors}">
                                <option value="${doctor.doctorId}">${doctor.firstName} ${doctor.lastName} (${doctor.specialization})</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="appointmentDateTime">Appointment Date & Time:</label>
                        <input type="datetime-local" id="appointmentDateTime" name="appointmentDateTime" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="reason">Reason for Appointment:</label>
                        <textarea id="reason" name="reason" class="form-control" rows="3" required></textarea>
                    </div>

                    <div class="form-group">
                        <label for="priority">Priority:</label>
                        <select id="priority" name="priority" class="form-control">
                            <option value="">Select Priority</option>
                            <option value="Low">Low</option>
                            <option value="Medium">Medium</option>
                            <option value="High">High</option>
                            <option value="Urgent">Urgent</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="notes">Notes:</label>
                        <textarea id="notes" name="notes" class="form-control" rows="3"></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Schedule Appointment</button>
                        <a href="${pageContext.request.contextPath}/appointment/list" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </section>
        </main>

        <footer class="footer">
            <p>&copy; 2026 Hospital Management System. All rights reserved.</p>
        </footer>
    </div>

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
