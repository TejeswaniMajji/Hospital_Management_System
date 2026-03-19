<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Doctor</title>
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
                    <h2>Edit Doctor</h2>
                </div>

                <form action="${pageContext.request.contextPath}/doctor/update" method="POST" class="form-horizontal">
                    <input type="hidden" name="doctorId" value="${doctor.doctorId}">

                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" id="firstName" name="firstName" class="form-control" value="${doctor.firstName}" required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" id="lastName" name="lastName" class="form-control" value="${doctor.lastName}" required>
                    </div>

                    <div class="form-group">
                        <label for="licenseNumber">License Number:</label>
                        <input type="text" id="licenseNumber" name="licenseNumber" class="form-control" value="${doctor.licenseNumber}" required>
                    </div>

                    <div class="form-group">
                        <label for="specialization">Specialization:</label>
                        <input type="text" id="specialization" name="specialization" class="form-control" value="${doctor.specialization}" required>
                    </div>

                    <div class="form-group">
                        <label for="department">Department:</label>
                        <input type="text" id="department" name="department" class="form-control" value="${doctor.department}" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone Number:</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" value="${doctor.phoneNumber}" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" class="form-control" value="${doctor.email}" required>
                    </div>

                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" class="form-control" rows="3">${doctor.address}</textarea>
                    </div>

                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" id="city" name="city" class="form-control" value="${doctor.city}">
                    </div>

                    <div class="form-group">
                        <label for="state">State:</label>
                        <input type="text" id="state" name="state" class="form-control" value="${doctor.state}">
                    </div>

                    <div class="form-group">
                        <label for="zipCode">Zip Code:</label>
                        <input type="text" id="zipCode" name="zipCode" class="form-control" value="${doctor.zipCode}">
                    </div>

                    <div class="form-group">
                        <label for="yearsOfExperience">Years of Experience:</label>
                        <input type="number" id="yearsOfExperience" name="yearsOfExperience" class="form-control" value="${doctor.yearsOfExperience}">
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Update Doctor</button>
                        <a href="${pageContext.request.contextPath}/doctor/list" class="btn btn-secondary">Cancel</a>
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
