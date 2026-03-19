<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password - Hospital Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .change-password-container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .change-password-container h2 {
            color: #2c3e50;
            margin-bottom: 30px;
            text-align: center;
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

        .form-group input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #bdc3c7;
            border-radius: 4px;
            font-size: 1rem;
        }

        .form-group input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
        }

        .alert {
            padding: 12px 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }

        .alert-error {
            background-color: #ffe6e6;
            color: #e74c3c;
            border-left: 4px solid #e74c3c;
        }

        .alert-success {
            background-color: #e6ffe6;
            color: #27ae60;
            border-left: 4px solid #27ae60;
        }

        .password-requirements {
            background-color: #f0f4ff;
            padding: 12px;
            border-radius: 4px;
            margin-bottom: 20px;
            font-size: 0.85rem;
            color: #2c3e50;
        }

        .password-requirements ul {
            margin: 0;
            padding-left: 20px;
        }

        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }

        .btn {
            flex: 1;
            padding: 10px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: #3498db;
            color: white;
        }

        .btn-primary:hover {
            background-color: #2980b9;
        }

        .btn-secondary {
            background-color: #95a5a6;
            color: white;
            text-decoration: none;
            text-align: center;
        }

        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="change-password-container">
        <h2>🔐 Change Password</h2>

        <!-- Error Messages -->
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>⚠️ Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Success Messages -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">
                <strong>✓ Success:</strong> ${success}
            </div>
        </c:if>

        <!-- Password Requirements -->
        <div class="password-requirements">
            <strong>Password Requirements:</strong>
            <ul>
                <li>Minimum 8 characters</li>
                <li>Include uppercase letter</li>
                <li>Include lowercase letter</li>
                <li>Include number</li>
            </ul>
        </div>

        <!-- Change Password Form -->
        <form action="${pageContext.request.contextPath}/auth/change-password" method="POST" id="changePasswordForm">
            <div class="form-group">
                <label for="oldPassword">Current Password *</label>
                <input 
                    type="password" 
                    id="oldPassword" 
                    name="oldPassword" 
                    placeholder="Enter your current password"
                    required>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password *</label>
                <input 
                    type="password" 
                    id="newPassword" 
                    name="newPassword" 
                    placeholder="Enter new password"
                    required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password *</label>
                <input 
                    type="password" 
                    id="confirmPassword" 
                    name="confirmPassword" 
                    placeholder="Confirm new password"
                    required>
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-primary">Update Password</button>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>

    <script>
        const form = document.getElementById('changePasswordForm');
        const newPassword = document.getElementById('newPassword');
        const confirmPassword = document.getElementById('confirmPassword');
        const oldPassword = document.getElementById('oldPassword');

        form.addEventListener('submit', function(e) {
            // Validate passwords match
            if (newPassword.value !== confirmPassword.value) {
                e.preventDefault();
                alert('New passwords do not match!');
                confirmPassword.focus();
                return;
            }

            // Check if new password is same as old password
            if (oldPassword.value === newPassword.value) {
                e.preventDefault();
                alert('New password must be different from current password!');
                newPassword.focus();
                return;
            }

            // Validate password strength
            if (!validatePasswordStrength(newPassword.value)) {
                e.preventDefault();
                alert('Password does not meet requirements:\n- Minimum 8 characters\n- Include uppercase letter\n- Include lowercase letter\n- Include number');
                newPassword.focus();
                return;
            }
        });

        function validatePasswordStrength(password) {
            const hasUppercase = /[A-Z]/.test(password);
            const hasLowercase = /[a-z]/.test(password);
            const hasNumbers = /\d/.test(password);
            const minLength = password.length >= 8;

            return hasUppercase && hasLowercase && hasNumbers && minLength;
        }

        // Real-time password match indicator
        confirmPassword.addEventListener('input', function() {
            if (newPassword.value && confirmPassword.value) {
                if (newPassword.value === confirmPassword.value) {
                    confirmPassword.style.borderColor = '#27ae60';
                } else {
                    confirmPassword.style.borderColor = '#e74c3c';
                }
            }
        });
    </script>
</body>
</html>
