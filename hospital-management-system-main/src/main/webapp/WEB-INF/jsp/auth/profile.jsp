<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile - Hospital Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .profile-container {
            max-width: 800px;
            margin: 30px auto;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        .profile-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }

        .profile-header h1 {
            font-size: 2rem;
            margin-bottom: 5px;
        }

        .profile-header .role-badge {
            display: inline-block;
            background-color: rgba(255, 255, 255, 0.2);
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9rem;
            margin-top: 10px;
        }

        .profile-content {
            padding: 30px;
        }

        .profile-section {
            margin-bottom: 30px;
        }

        .profile-section h3 {
            color: #2c3e50;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #ecf0f1;
            font-size: 1.2rem;
        }

        .profile-info {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
        }

        .info-card {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            border-left: 4px solid #667eea;
        }

        .info-card label {
            display: block;
            font-weight: 600;
            color: #667eea;
            font-size: 0.85rem;
            text-transform: uppercase;
            margin-bottom: 5px;
        }

        .info-card .value {
            font-size: 1.1rem;
            color: #2c3e50;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 600;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            font-size: 1rem;
            font-family: inherit;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-row .form-group {
            margin-bottom: 0;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }

        .alert-success {
            background-color: #e6ffe6;
            color: #27ae60;
            border-left: 4px solid #27ae60;
        }

        .alert-error {
            background-color: #ffe6e6;
            color: #e74c3c;
            border-left: 4px solid #e74c3c;
        }

        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
            flex-wrap: wrap;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .btn-primary {
            background-color: #667eea;
            color: white;
            flex: 1;
            min-width: 150px;
        }

        .btn-primary:hover {
            background-color: #764ba2;
        }

        .btn-secondary {
            background-color: #95a5a6;
            color: white;
            flex: 1;
            min-width: 150px;
        }

        .btn-secondary:hover {
            background-color: #7f8c8d;
        }

        .btn-link {
            background-color: transparent;
            color: #667eea;
            text-decoration: none;
            padding: 0;
            border: none;
            cursor: pointer;
            font-weight: 600;
        }

        .btn-link:hover {
            text-decoration: underline;
        }

        .divider {
            height: 1px;
            background-color: #ecf0f1;
            margin: 30px 0;
        }

        @media (max-width: 600px) {
            .profile-header {
                padding: 20px;
            }

            .profile-header h1 {
                font-size: 1.5rem;
            }

            .profile-content {
                padding: 20px;
            }

            .form-row {
                grid-template-columns: 1fr;
                gap: 0;
            }

            .profile-info {
                grid-template-columns: 1fr;
                gap: 0;
            }

            .button-group {
                flex-direction: column;
            }

            .btn {
                min-width: auto;
            }
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <!-- Profile Header -->
        <div class="profile-header">
            <h1>👤 ${user.getFullName()}</h1>
            <div class="role-badge">${user.role}</div>
        </div>

        <!-- Profile Content -->
        <div class="profile-content">
            <!-- Success Messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    <strong>✓ Success:</strong> ${success}
                </div>
            </c:if>

            <!-- Error Messages -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <strong>⚠️ Error:</strong> ${error}
                </div>
            </c:if>

            <!-- Account Information Section -->
            <div class="profile-section">
                <h3>Account Information</h3>
                <div class="profile-info">
                    <div class="info-card">
                        <label>Username</label>
                        <div class="value">${user.username}</div>
                    </div>
                    <div class="info-card">
                        <label>Email</label>
                        <div class="value">${user.email}</div>
                    </div>
                    <div class="info-card">
                        <label>Account Status</label>
                        <div class="value">
                            <c:choose>
                                <c:when test="${user.status == 'ACTIVE'}">
                                    <span style="background-color: #27ae60; color: white; padding: 5px 10px; border-radius: 3px;">
                                        ${user.status}
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span style="background-color: #e74c3c; color: white; padding: 5px 10px; border-radius: 3px;">
                                        ${user.status}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="info-card">
                        <label>Member Since</label>
                        <div class="value">
                            <c:if test="${user.createdAt != null}">
                                ${user.createdAt}
                            </c:if>
                            <c:if test="${user.createdAt == null}">
                                N/A
                            </c:if>
                        </div>
                    </div>
                    <div class="info-card">
                        <label>Last Login</label>
                        <div class="value">
                            <c:if test="${user.lastLogin != null}">
                                ${user.lastLogin}
                            </c:if>
                            <c:if test="${user.lastLogin == null}">
                                Never
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Profile Section -->
            <div class="profile-section">
                <h3>Edit Profile Information</h3>
                <form action="${pageContext.request.contextPath}/auth/profile/update" method="POST">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input 
                                type="text" 
                                id="firstName" 
                                name="firstName" 
                                value="${user.firstName}"
                                required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input 
                                type="text" 
                                id="lastName" 
                                name="lastName" 
                                value="${user.lastName}"
                                required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <input 
                            type="tel" 
                            id="phoneNumber" 
                            name="phoneNumber" 
                            value="${user.phoneNumber}"
                            placeholder="+1 (555) 123-4567">
                    </div>

                    <div class="button-group">
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Back to Dashboard</a>
                    </div>
                </form>
            </div>

            <!-- Security Section -->
            <div class="divider"></div>
            <div class="profile-section">
                <h3>Security & Privacy</h3>
                <p style="color: #666; margin-bottom: 15px;">
                    Manage your password and account security settings
                </p>
                <div class="button-group">
                    <a href="${pageContext.request.contextPath}/auth/change-password" class="btn btn-primary">Change Password</a>
                    <button type="button" class="btn btn-secondary" onclick="alert('Contact system administrator to manage security settings')">
                        Security Settings
                    </button>
                </div>
            </div>

            <!-- Danger Zone -->
            <div class="divider" style="border-top: 2px solid #e74c3c; border-bottom: none; margin-top: 40px;"></div>
            <div class="profile-section">
                <h3 style="color: #e74c3c;">Danger Zone</h3>
                <p style="color: #666; margin-bottom: 15px;">
                    Actions in this section cannot be undone
                </p>
                <div class="button-group">
                    <button type="button" class="btn btn-secondary" style="background-color: #e74c3c;" onclick="if(confirm('Are you sure you want to sign out from all devices?')) { window.location.href='${pageContext.request.contextPath}/auth/logout'; }">
                        Sign Out from All Devices
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
