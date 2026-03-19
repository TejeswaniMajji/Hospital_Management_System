package com.hospital.controller;

import com.hospital.repository.PatientRepository;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/features")
public class FeaturesController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/patients")
    public String patientManagement(Model model) {
        model.addAttribute("title", "Patient Management");
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("patientCount", patientRepository.count());
        model.addAttribute("appointmentCount", appointmentRepository.count());
        return "features/patients";
    }

    @GetMapping("/doctors")
    public String doctorManagement(Model model) {
        model.addAttribute("title", "Doctor Management");
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("doctorCount", doctorRepository.count());
        return "features/doctors";
    }

    @GetMapping("/appointments")
    public String appointmentScheduling(Model model) {
        model.addAttribute("title", "Appointment Scheduling");
        return "features/appointments";
    }

    @GetMapping("/staff")
    public String staffManagement(Model model) {
        model.addAttribute("title", "Staff Management");
        return "features/staff";
    }

    @GetMapping("/security")
    public String security(Model model) {
        model.addAttribute("title", "Security & Access Control");
        return "features/security";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("title", "Analytics & Reports");
        return "features/analytics";
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {
        model.addAttribute("title", "Appointment Calendar");
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "features/calendar";
    }

    @GetMapping("/billing")
    public String billing(Model model) {
        model.addAttribute("title", "Billing & Payments");
        return "features/billing";
    }

    @GetMapping("/laboratory")
    public String laboratory(Model model) {
        model.addAttribute("title", "Laboratory Management");
        return "features/laboratory";
    }

    @GetMapping("/pharmacy")
    public String pharmacy(Model model) {
        model.addAttribute("title", "Pharmacy Management");
        return "features/pharmacy";
    }

    @GetMapping("/notifications")
    public String notifications(Model model) {
        model.addAttribute("title", "Notification Center");
        return "features/notifications";
    }
}
