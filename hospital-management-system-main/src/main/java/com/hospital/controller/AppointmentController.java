package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Appointment Controller - Handles appointment-related HTTP requests
 */
@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public String listAppointments(Model model) {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patients);
        model.addAttribute("doctors", doctors);
        return "appointment/add";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setStatus("Scheduled");
        appointmentService.createAppointment(appointment);
        return "redirect:/appointment/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            List<Patient> patients = patientService.getAllPatients();
            List<Doctor> doctors = doctorService.getAllDoctors();
            model.addAttribute("appointment", appointment.get());
            model.addAttribute("patients", patients);
            model.addAttribute("doctors", doctors);
            return "appointment/edit";
        }
        return "redirect:/appointment/list";
    }

    @PostMapping("/update")
    public String updateAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.updateAppointment(appointment);
        return "redirect:/appointment/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointment/list";
    }

    @GetMapping("/view/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            model.addAttribute("appointment", appointment.get());
            return "appointment/view";
        }
        return "redirect:/appointment/list";
    }

    @PostMapping("/search")
    public String searchAppointment(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        List<Appointment> results = null;

        switch (searchType) {
            case "status":
                results = appointmentService.getAppointmentsByStatus(searchValue);
                break;
            case "priority":
                // Filter by priority
                results = appointmentService.getAllAppointments().stream()
                    .filter(a -> a.getPriority() != null && a.getPriority().equalsIgnoreCase(searchValue))
                    .toList();
                break;
        }

        model.addAttribute("appointments", results != null ? results : List.of());
        return "appointment/list";
    }
}
