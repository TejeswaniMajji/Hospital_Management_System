<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Patient</title>
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
                    <h2>Edit Patient</h2>
                </div>

                <form action="${pageContext.request.contextPath}/patient/update" method="POST" class="form-horizontal">
                    <input type="hidden" name="patientId" value="${patient.patientId}">

                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" id="firstName" name="firstName" class="form-control" value="${patient.firstName}" required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" id="lastName" name="lastName" class="form-control" value="${patient.lastName}" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" class="form-control" value="${patient.email}" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone Number:</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${patient.phoneNumber}" required>
                    </div>

                    <div class="form-group">
                        <label for="dateOfBirth">Date of Birth:</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="${patient.dateOfBirth}" required>
                    </div>

                    <div class="form-group">
                        <label for="gender">Gender:</label>
                        <select id="gender" name="gender" class="form-control">
                            <option value="">Select Gender</option>
                            <option value="Male" ${patient.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${patient.gender == 'Female' ? 'selected' : ''}>Female</option>
                            <option value="Other" ${patient.gender == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" class="form-control" rows="3">${patient.address}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" id="city" name="city" class="form-control" value="${patient.city}">
                    </div>

                    <div class="form-group">
                        <label for="state">State:</label>
                        <input type="text" id="state" name="state" class="form-control" value="${patient.state}">
                    </div>

                    <div class="form-group">
                        <label for="zipCode">Zip Code:</label>
                        <input type="text" id="zipCode" name="zipCode" class="form-control" value="${patient.zipCode}">
                    </div>

                    <div class="form-group">
                        <label for="bloodGroup">Blood Group:</label>
                        <select id="bloodGroup" name="bloodGroup" class="form-control">
                            <option value="">Select Blood Group</option>
                            <option value="O+" ${patient.bloodGroup == 'O+' ? 'selected' : ''}>O+</option>
                            <option value="O-" ${patient.bloodGroup == 'O-' ? 'selected' : ''}>O-</option>
                            <option value="A+" ${patient.bloodGroup == 'A+' ? 'selected' : ''}>A+</option>
                            <option value="A-" ${patient.bloodGroup == 'A-' ? 'selected' : ''}>A-</option>
                            <option value="B+" ${patient.bloodGroup == 'B+' ? 'selected' : ''}>B+</option>
                            <option value="B-" ${patient.bloodGroup == 'B-' ? 'selected' : ''}>B-</option>
                            <option value="AB+" ${patient.bloodGroup == 'AB+' ? 'selected' : ''}>AB+</option>
                            <option value="AB-" ${patient.bloodGroup == 'AB-' ? 'selected' : ''}>AB-</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="medicalHistory">Medical History:</label>
                        <textarea id="medicalHistory" name="medicalHistory" class="form-control" rows="4">${patient.medicalHistory}</textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Update Patient</button>
                        <a href="${pageContext.request.contextPath}/patient/list" class="btn btn-secondary">Cancel</a>
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
