<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Patient</title>
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
                    <h2>Add New Patient</h2>
                </div>

                <form action="${pageContext.request.contextPath}/patient/save" method="POST" class="form-horizontal">
                    <div class="form-group">
                        <label for="firstName">First Name:</label>
                        <input type="text" id="firstName" name="firstName" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name:</label>
                        <input type="text" id="lastName" name="lastName" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone Number:</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="dateOfBirth">Date of Birth:</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="gender">Gender:</label>
                        <select id="gender" name="gender" class="form-control">
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" class="form-control" rows="3"></textarea>
                    </div>

                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" id="city" name="city" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="state">State:</label>
                        <input type="text" id="state" name="state" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="zipCode">Zip Code:</label>
                        <input type="text" id="zipCode" name="zipCode" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="bloodGroup">Blood Group:</label>
                        <select id="bloodGroup" name="bloodGroup" class="form-control">
                            <option value="">Select Blood Group</option>
                            <option value="O+">O+</option>
                            <option value="O-">O-</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="medicalHistory">Medical History:</label>
                        <textarea id="medicalHistory" name="medicalHistory" class="form-control" rows="4"></textarea>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save Patient</button>
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
