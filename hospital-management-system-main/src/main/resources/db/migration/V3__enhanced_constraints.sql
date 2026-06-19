-- Enhanced constraints/indexes supporting existing repository query methods
-- (AppointmentRepository.findByStatus, findByAppointmentDateTimeBetween)

CREATE INDEX IF NOT EXISTS idx_appointments_status ON appointments(status);
CREATE INDEX IF NOT EXISTS idx_appointments_datetime ON appointments(appointment_date_time);
