<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Appointment</title>
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
                    <h2>Edit Appointment</h2>
                </div>

                <form action="${pageContext.request.contextPath}/appointment/update" method="POST" class="form-horizontal">
                    <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">

                    <div class="form-group">
                        <label for="patientId">Patient:</label>
                        <select id="patientId" name="patient.patientId" class="form-control" required>
                            <option value="">Select Patient</option>
                            <c:forEach var="patient" items="${patients}">
                                <option value="${patient.patientId}" ${patient.patientId == appointment.patient.patientId ? 'selected' : ''}>${patient.firstName} ${patient.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="doctorId">Doctor:</label>
                        <select id="doctorId" name="doctor.doctorId" class="form-control" required>
                            <option value="">Select Doctor</option>
                            <c:forEach var="doctor" items="${doctors}">
                                <option value="${doctor.doctorId}" ${doctor.doctorId == appointment.doctor.doctorId ? 'selected' : ''}>${doctor.firstName} ${doctor.lastName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="appointmentDateTime">Appointment Date & Time:</label>
                        <input type="datetime-local" id="appointmentDateTime" name="appointmentDateTime" class="form-control" value="${appointment.appointmentDateTime}" required>
                    </div>

                    <div class="form-group">
                        <label for="reason">Reason for Appointment:</label>
                        <textarea id="reason" name="reason" class="form-control" rows="3" required>${appointment.reason}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="status">Status:</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">Select Status</option>
                            <option value="Scheduled" ${appointment.status == 'Scheduled' ? 'selected' : ''}>Scheduled</option>
                            <option value="Completed" ${appointment.status == 'Completed' ? 'selected' : ''}>Completed</option>
                            <option value="Cancelled" ${appointment.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                            <option value="Rescheduled" ${appointment.status == 'Rescheduled' ? 'selected' : ''}>Rescheduled</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="priority">Priority:</label>
                        <select id="priority" name="priority" class="form-control">
                            <option value="">Select Priority</option>
                            <option value="Low" ${appointment.priority == 'Low' ? 'selected' : ''}>Low</option>
                            <option value="Medium" ${appointment.priority == 'Medium' ? 'selected' : ''}>Medium</option>
                            <option value="High" ${appointment.priority == 'High' ? 'selected' : ''}>High</option>
                            <option value="Urgent" ${appointment.priority == 'Urgent' ? 'selected' : ''}>Urgent</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="notes">Notes:</label>
                        <textarea id="notes" name="notes" class="form-control" rows="3">${appointment.notes}</textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Update Appointment</button>
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
