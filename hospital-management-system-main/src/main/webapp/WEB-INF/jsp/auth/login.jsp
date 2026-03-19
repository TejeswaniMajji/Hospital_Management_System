<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Hospital Management System</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-wrapper {
            display: flex;
            width: 100%;
            max-width: 1000px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            margin: 20px;
        }

        .login-left {
            flex: 1;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            min-height: 500px;
        }

        .login-left h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
        }

        .login-left p {
            font-size: 1.1rem;
            line-height: 1.6;
            opacity: 0.9;
        }

        .login-right {
            flex: 1;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-right h2 {
            color: #2c3e50;
            margin-bottom: 30px;
            font-size: 1.8rem;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 600;
            font-size: 0.95rem;
        }

        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 6px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
        }

        .form-group input:focus {
            outline: none;
            border-color: #667eea;
            background-color: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-group input::placeholder {
            color: #999;
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

        .alert-success {
            background-color: #efe;
            color: #3c3;
            border-left: 4px solid #3c3;
        }

        .btn-login {
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
            margin-top: 20px;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-login:active {
            transform: translateY(0);
        }

        .login-footer {
            margin-top: 20px;
            text-align: center;
            color: #666;
            font-size: 0.95rem;
        }

        .login-footer a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
        }

        .login-footer a:hover {
            color: #764ba2;
            text-decoration: underline;
        }

        .forgot-password {
            text-align: right;
            margin-bottom: 15px;
        }

        .forgot-password a {
            color: #667eea;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
        }

        .forgot-password a:hover {
            text-decoration: underline;
        }

        @media (max-width: 768px) {
            .login-wrapper {
                flex-direction: column;
            }

            .login-left {
                padding: 30px;
                min-height: auto;
            }

            .login-left h1 {
                font-size: 2rem;
            }

            .login-right {
                padding: 30px;
            }

            .login-right h2 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <!-- Left Panel -->
        <div class="login-left">
            <div>
                <h1>🏥</h1>
                <h1>HMS</h1>
                <p>Hospital Management System</p>
                <p style="margin-top: 20px; font-size: 0.95rem; opacity: 0.8;">
                    Streamlined healthcare management with secure access for doctors, nurses, and patients.
                </p>
            </div>
        </div>

        <!-- Right Panel -->
        <div class="login-right">
            <h2>Welcome Back</h2>

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

            <!-- Login Form -->
            <form action="${pageContext.request.contextPath}/auth/login" method="POST">
                <div class="form-group">
                    <label for="username">Username or Email</label>
                    <input 
                        type="text" 
                        id="username" 
                        name="username" 
                        value="${username}"
                        placeholder="Enter your username or email"
                        required 
                        autofocus>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input 
                        type="password" 
                        id="password" 
                        name="password" 
                        placeholder="Enter your password"
                        required>
                </div>

                <div class="forgot-password">
                    <a href="${pageContext.request.contextPath}/auth/forgot-password">Forgot password?</a>
                </div>

                <button type="submit" class="btn-login">Sign In</button>
            </form>

            <!-- Footer -->
            <div class="login-footer">
                <p>Don't have an account? 
                    <a href="${pageContext.request.contextPath}/auth/register">Create one here</a>
                </p>
                <p style="margin-top: 15px; font-size: 0.85rem; color: #999;">
                    Account will be locked after 5 failed login attempts
                </p>
            </div>
        </div>
    </div>

    <script>
        // Add some interactivity
        document.getElementById('username').addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
        });

        document.getElementById('password').addEventListener('focus', function() {
            this.style.borderColor = '#667eea';
        });

        // Prevent form submission if fields are empty
        document.querySelector('form').addEventListener('submit', function(e) {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();

            if (!username || !password) {
                e.preventDefault();
                alert('Please fill in all fields');
            }
        });
    </script>
</body>
</html>
