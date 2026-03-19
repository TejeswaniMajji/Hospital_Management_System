// Hospital Management System - JavaScript Functions

document.addEventListener('DOMContentLoaded', function() {
    console.log('Hospital Management System Loaded');
    initializeEventListeners();
});

// Initialize all event listeners
function initializeEventListeners() {
    // Add click handlers for buttons
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(btn => {
        btn.addEventListener('click', function(e) {
            if (!confirm('Are you sure you want to delete this record?')) {
                e.preventDefault();
            }
        });
    });

    // Form validation
    const forms = document.querySelectorAll('.form-horizontal');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
            }
        });
    });
}

// Form validation function
function validateForm(form) {
    const requiredFields = form.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;

    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            field.style.borderColor = '#e74c3c';
            isValid = false;
            showError(field, 'This field is required');
        } else {
            field.style.borderColor = '#bdc3c7';
            clearError(field);
        }
    });

    return isValid;
}

// Show error message
function showError(field, message) {
    const errorClass = 'field-error';
    
    // Remove existing error if present
    const existingError = field.nextElementSibling;
    if (existingError && existingError.classList.contains(errorClass)) {
        existingError.remove();
    }

    // Create and insert error message
    const errorMsg = document.createElement('div');
    errorMsg.className = 'error-message';
    errorMsg.textContent = message;
    errorMsg.style.color = '#e74c3c';
    errorMsg.style.fontSize = '0.85rem';
    errorMsg.style.marginTop = '0.25rem';
    field.parentNode.insertBefore(errorMsg, field.nextSibling);
}

// Clear error message
function clearError(field) {
    const errorMsg = field.nextElementSibling;
    if (errorMsg && errorMsg.className === 'error-message') {
        errorMsg.remove();
    }
}

// Format date for display
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}

// Format time for display
function formatTime(timeString) {
    const date = new Date(timeString);
    return date.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    });
}

// Search functionality
function performSearch(searchType, searchValue) {
    if (!searchValue.trim()) {
        alert('Please enter a search value');
        return false;
    }
    return true;
}

// Export table to CSV
function exportTableToCSV(fileName) {
    const table = document.querySelector('.data-table');
    if (!table) {
        alert('No table found to export');
        return;
    }

    let csv = [];
    const rows = table.querySelectorAll('tr');

    rows.forEach(row => {
        let csvRow = [];
        const cells = row.querySelectorAll('th, td');
        cells.forEach(cell => {
            csvRow.push('"' + cell.textContent.trim() + '"');
        });
        csv.push(csvRow.join(','));
    });

    downloadCSV(csv.join('\n'), fileName);
}

// Download CSV file
function downloadCSV(csv, fileName) {
    const csvFile = new Blob([csv], { type: 'text/csv' });
    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(csvFile);
    downloadLink.download = fileName || 'export.csv';
    downloadLink.click();
}

// Print functionality
function printPage() {
    window.print();
}

// Confirm action
function confirmAction(message) {
    return confirm(message);
}

// Show loading spinner
function showLoader() {
    const loader = document.createElement('div');
    loader.className = 'loader';
    loader.innerHTML = '<div class="spinner"></div><p>Loading...</p>';
    loader.style.cssText = `
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        z-index: 9999;
        text-align: center;
    `;
    document.body.appendChild(loader);
    return loader;
}

// Hide loading spinner
function hideLoader() {
    const loader = document.querySelector('.loader');
    if (loader) {
        loader.remove();
    }
}

// Show notification
function showNotification(message, type = 'info', duration = 3000) {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 1.5rem;
        border-radius: 4px;
        color: white;
        font-weight: 500;
        z-index: 10000;
        animation: slideIn 0.3s ease;
    `;

    // Set background color based on type
    const colors = {
        success: '#27ae60',
        error: '#e74c3c',
        warning: '#f39c12',
        info: '#3498db'
    };
    notification.style.backgroundColor = colors[type] || colors.info;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, duration);
}

// Phone number validation
function validatePhoneNumber(phoneNumber) {
    const phoneRegex = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/;
    return phoneRegex.test(phoneNumber.replace(/\s/g, ''));
}

// Email validation
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Password strength checker
function checkPasswordStrength(password) {
    let strength = 0;
    if (password.length >= 8) strength++;
    if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
    if (/[0-9]/.test(password)) strength++;
    if (/[!@#$%^&*]/.test(password)) strength++;

    const strengthLevels = ['Very Weak', 'Weak', 'Fair', 'Good', 'Strong'];
    return strengthLevels[strength] || 'Very Weak';
}

// Calculate age from date of birth
function calculateAge(dob) {
    const today = new Date();
    const birthDate = new Date(dob);
    let age = today.getFullYear() - birthDate.getFullYear();
    const month = today.getMonth() - birthDate.getMonth();
    
    if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    
    return age;
}

// Sort table by column
function sortTable(columnIndex) {
    const table = document.querySelector('.data-table');
    if (!table) return;

    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));

    rows.sort((a, b) => {
        const aValue = a.children[columnIndex].textContent.trim();
        const bValue = b.children[columnIndex].textContent.trim();

        // Try to parse as numbers
        const aNum = parseFloat(aValue);
        const bNum = parseFloat(bValue);

        if (!isNaN(aNum) && !isNaN(bNum)) {
            return aNum - bNum;
        }

        // String comparison
        return aValue.localeCompare(bValue);
    });

    rows.forEach(row => tbody.appendChild(row));
}

// Filter table by search term
function filterTable(searchTerm, columnIndex = null) {
    const table = document.querySelector('.data-table');
    if (!table) return;

    const rows = table.querySelectorAll('tbody tr');
    const term = searchTerm.toLowerCase();

    rows.forEach(row => {
        if (columnIndex !== null) {
            const cell = row.children[columnIndex];
            row.style.display = cell && cell.textContent.toLowerCase().includes(term) ? '' : 'none';
        } else {
            let found = false;
            for (let i = 0; i < row.children.length; i++) {
                if (row.children[i].textContent.toLowerCase().includes(term)) {
                    found = true;
                    break;
                }
            }
            row.style.display = found ? '' : 'none';
        }
    });
}
