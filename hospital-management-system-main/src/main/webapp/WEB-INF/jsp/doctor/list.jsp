<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor List</title>
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
                    <h2>Doctor Management</h2>
                    <a href="${pageContext.request.contextPath}/doctor/add" class="btn btn-primary">Add New Doctor</a>
                </div>

                <!-- Search Form -->
                <div class="search-form">
                    <h3>Search Doctors</h3>
                    <form action="${pageContext.request.contextPath}/doctor/search" method="POST" class="form-inline">
                        <select name="searchType" class="form-control">
                            <option value="firstName">First Name</option>
                            <option value="specialization">Specialization</option>
                            <option value="department">Department</option>
                            <option value="licenseNumber">License Number</option>
                        </select>
                        <input type="text" name="searchValue" placeholder="Enter search value" class="form-control" required>
                        <button type="submit" class="btn btn-secondary">Search</button>
                    </form>
                </div>

                <!-- Doctor Table -->
                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty doctors}">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>License Number</th>
                                        <th>Specialization</th>
                                        <th>Department</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="doctor" items="${doctors}">
                                        <tr>
                                            <td>${doctor.doctorId}</td>
                                            <td>${doctor.firstName}</td>
                                            <td>${doctor.lastName}</td>
                                            <td>${doctor.licenseNumber}</td>
                                            <td>${doctor.specialization}</td>
                                            <td>${doctor.department}</td>
                                            <td>${doctor.email}</td>
                                            <td>${doctor.phoneNumber}</td>
                                            <td><span class="status-badge status-${doctor.status}">${doctor.status}</span></td>
                                            <td class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/doctor/view/${doctor.doctorId}" class="btn-link btn-view">View</a>
                                                <a href="${pageContext.request.contextPath}/doctor/edit/${doctor.doctorId}" class="btn-link btn-edit">Edit</a>
                                                <a href="${pageContext.request.contextPath}/doctor/delete/${doctor.doctorId}" class="btn-link btn-delete" onclick="return confirm('Are you sure?')">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="no-data">No doctors found. <a href="${pageContext.request.contextPath}/doctor/add">Add a new doctor</a></p>
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
