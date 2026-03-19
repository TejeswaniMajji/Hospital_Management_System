<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient List</title>
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
                    <h2>Patient Management</h2>
                    <a href="${pageContext.request.contextPath}/patient/add" class="btn btn-primary">Add New Patient</a>
                </div>

                <!-- Search Form -->
                <div class="search-form">
                    <h3>Search Patients</h3>
                    <form action="${pageContext.request.contextPath}/patient/search" method="POST" class="form-inline">
                        <select name="searchType" class="form-control">
                            <option value="firstName">First Name</option>
                            <option value="lastName">Last Name</option>
                            <option value="email">Email</option>
                            <option value="phone">Phone</option>
                        </select>
                        <input type="text" name="searchValue" placeholder="Enter search value" class="form-control" required>
                        <button type="submit" class="btn btn-secondary">Search</button>
                    </form>
                </div>

                <!-- Patient Table -->
                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty patients}">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Date of Birth</th>
                                        <th>Blood Group</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="patient" items="${patients}">
                                        <tr>
                                            <td>${patient.patientId}</td>
                                            <td>${patient.firstName}</td>
                                            <td>${patient.lastName}</td>
                                            <td>${patient.email}</td>
                                            <td>${patient.phoneNumber}</td>
                                            <td>${patient.dateOfBirth}</td>
                                            <td>${patient.bloodGroup}</td>
                                            <td><span class="status-badge status-${patient.status}">${patient.status}</span></td>
                                            <td class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/patient/view/${patient.patientId}" class="btn-link btn-view">View</a>
                                                <a href="${pageContext.request.contextPath}/patient/edit/${patient.patientId}" class="btn-link btn-edit">Edit</a>
                                                <a href="${pageContext.request.contextPath}/patient/delete/${patient.patientId}" class="btn-link btn-delete" onclick="return confirm('Are you sure?')">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="no-data">No patients found. <a href="${pageContext.request.contextPath}/patient/add">Add a new patient</a></p>
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
