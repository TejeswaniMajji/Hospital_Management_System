<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Hospital Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 40px 20px;
        }

        .register-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
        }

        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .register-header h1 {
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 10px;
        }

        .register-header p {
            color: #666;
            font-size: 0.95rem;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-row .form-group {
            margin-bottom: 0;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 600;
            font-size: 0.95rem;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
            font-family: inherit;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #667eea;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-group input::placeholder {
            color: #999;
        }

        .password-requirements {
            background-color: #f0f4ff;
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.9rem;
            color: #2c3e50;
        }

        .password-requirements h4 {
            margin-bottom: 10px;
            color: #667eea;
        }

        .password-requirements ul {
            list-style: none;
            padding-left: 0;
        }

        .password-requirements li {
            padding: 5px 0;
            padding-left: 25px;
            position: relative;
        }

        .password-requirements li:before {
            content: "✓";
            position: absolute;
            left: 0;
            color: #27ae60;
            font-weight: bold;
        }

        .alert {
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }

        .alert-error {
            background-color: #fee;
            color: #c33;
            border-left: 4px solid #c33;
        }

        .btn-register {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn-register:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-register:active {
            transform: translateY(0);
        }

        .register-footer {
            margin-top: 20px;
            text-align: center;
            color: #666;
            font-size: 0.95rem;
        }

        .register-footer a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }

        .register-footer a:hover {
            text-decoration: underline;
        }

        .terms-checkbox {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            font-size: 0.9rem;
            color: #666;
        }

        .terms-checkbox input {
            width: auto;
            margin-right: 10px;
            cursor: pointer;
        }

        .terms-checkbox a {
            color: #667eea;
            text-decoration: none;
        }

        .terms-checkbox a:hover {
            text-decoration: underline;
        }

        @media (max-width: 600px) {
            .register-container {
                padding: 20px;
            }

            .form-row {
                grid-template-columns: 1fr;
                gap: 0;
            }

            .register-header h1 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="register-container">
        <!-- Header -->
        <div class="register-header">
            <h1>🏥 Create Account</h1>
            <p>Join the Hospital Management System</p>
        </div>

        <!-- Error Messages -->
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <strong>⚠️ Error:</strong> ${error}
            </div>
        </c:if>

        <!-- Password Requirements -->
        <div class="password-requirements">
            <h4>Password Requirements:</h4>
            <ul>
                <li>Minimum 8 characters</li>
                <li>At least one uppercase letter (A-Z)</li>
                <li>At least one lowercase letter (a-z)</li>
                <li>At least one number (0-9)</li>
            </ul>
        </div>

        <!-- Registration Form -->
        <form action="${pageContext.request.contextPath}/auth/register" method="POST" id="registerForm">
            <!-- Name Fields -->
            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First Name *</label>
                    <input 
                        type="text" 
                        id="firstName" 
                        name="firstName" 
                        value="${user.firstName}"
                        placeholder="John"
                        required>
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name *</label>
                    <input 
                        type="text" 
                        id="lastName" 
                        name="lastName" 
                        value="${user.lastName}"
                        placeholder="Doe"
                        required>
                </div>
            </div>

            <!-- Username and Email -->
            <div class="form-row">
                <div class="form-group">
                    <label for="username">Username *</label>
                    <input 
                        type="text" 
                        id="username" 
                        name="username" 
                        value="${user.username}"
                        placeholder="johndoe123"
                        pattern="^[a-zA-Z0-9_]{3,}$"
                        title="Username must be 3+ characters, letters, numbers, and underscores only"
                        required>
                </div>
                <div class="form-group">
                    <label for="email">Email *</label>
                    <input 
                        type="email" 
                        id="email" 
                        name="email" 
                        value="${user.email}"
                        placeholder="john@example.com"
                        required>
                </div>
            </div>

            <!-- Phone and Account Type -->
            <div class="form-row">
                <div class="form-group">
                    <label for="phoneNumber">Phone Number (Optional)</label>
                    <input 
                        type="tel" 
                        id="phoneNumber" 
                        name="phoneNumber" 
                        value="${user.phoneNumber}"
                        placeholder="+1 (555) 123-4567"
                        pattern="^[+]?[0-9\s\-()]*$"
                        title="Please enter a valid phone number">
                </div>
                <div class="form-group">
                    <label for="role">Account Type *</label>
                    <select id="role" name="role" required>
                        <option value="">Select Account Type</option>
                        <option value="PATIENT" ${user.role == 'PATIENT' ? 'selected' : ''}>Patient</option>
                        <option value="DOCTOR" ${user.role == 'DOCTOR' ? 'selected' : ''}>Doctor</option>
                        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Administrator</option>
                    </select>
                </div>
            </div>

            <!-- Password Fields -->
            <div class="form-row">
                <div class="form-group">
                    <label for="password">Password *</label>
                    <input 
                        type="password" 
                        id="password" 
                        name="password" 
                        placeholder="Enter secure password"
                        required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm Password *</label>
                    <input 
                        type="password" 
                        id="confirmPassword" 
                        name="confirmPassword" 
                        placeholder="Re-enter password"
                        required>
                </div>
            </div>

            <!-- Terms Checkbox -->
            <div class="terms-checkbox">
                <input 
                    type="checkbox" 
                    id="terms" 
                    name="terms" 
                    value="true"
                    required>
                <label for="terms" style="margin-bottom: 0;">
                    I agree to the <a href="#" onclick="return false;">Terms & Conditions</a> and <a href="#" onclick="return false;">Privacy Policy</a>
                </label>
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn-register">Create Account</button>
        </form>

        <!-- Footer -->
        <div class="register-footer">
            <p>Already have an account? 
                <a href="${pageContext.request.contextPath}/auth/login">Sign in here</a>
            </p>
        </div>
    </div>

    <script>
        const form = document.getElementById('registerForm');
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        form.addEventListener('submit', function(e) {
            // Validate passwords match
            if (password.value !== confirmPassword.value) {
                e.preventDefault();
                alert('Passwords do not match!');
                confirmPassword.focus();
                return;
            }

            // Validate password strength
            if (!validatePasswordStrength(password.value)) {
                e.preventDefault();
                alert('Password does not meet security requirements:\n- Minimum 8 characters\n- Include uppercase letter\n- Include lowercase letter\n- Include number');
                password.focus();
                return;
            }

            // Validate username format
            const username = document.getElementById('username').value;
            if (!/^[a-zA-Z0-9_]{3,}$/.test(username)) {
                e.preventDefault();
                alert('Username must be 3+ characters with only letters, numbers, and underscores');
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
            if (password.value && confirmPassword.value) {
                if (password.value === confirmPassword.value) {
                    confirmPassword.style.borderColor = '#27ae60';
                } else {
                    confirmPassword.style.borderColor = '#e74c3c';
                }
            }
        });
    </script>
</body>
</html>
