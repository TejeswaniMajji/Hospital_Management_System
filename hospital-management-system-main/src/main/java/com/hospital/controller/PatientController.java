package com.hospital.controller;

import com.hospital.model.Patient;
import com.hospital.model.Doctor;
import com.hospital.model.Appointment;
import com.hospital.service.PatientService;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Patient Controller - Handles patient-related HTTP requests
 */
@Controller
@RequestMapping("/patient")
@SuppressWarnings("null")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/list")
    public String listPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patient.setRegistrationDate(LocalDate.now());
        patient.setStatus("Active");
        patientService.createPatient(patient);
        return "redirect:/patient/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            return "patient/edit";
        }
        return "redirect:/patient/list";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute Patient patient) {
        patientService.updatePatient(patient);
        return "redirect:/patient/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patientService.deletePatient(id);
        redirectAttributes.addFlashAttribute("success", "Patient deleted successfully!");
        return "redirect:/patient/list";
    }



    @PostMapping("/search")
    public String searchPatient(@RequestParam String searchType, @RequestParam String searchValue, Model model) {
        List<Patient> results = null;

        switch (searchType) {
            case "firstName":
                results = patientService.searchPatientByFirstName(searchValue);
                break;
            case "lastName":
                results = patientService.searchPatientByLastName(searchValue);
                break;
            case "email":
                Optional<Patient> patient = patientService.getPatientByEmail(searchValue);
                if (patient.isPresent()) {
                    results = List.of(patient.get());
                }
                break;
            case "phone":
                Optional<Patient> patientByPhone = patientService.getPatientByPhoneNumber(searchValue);
                if (patientByPhone.isPresent()) {
                    results = List.of(patientByPhone.get());
                }
                break;
        }

        model.addAttribute("patients", results != null ? results : List.of());
        return "patient/list";
    }

    /**
     * Show schedule appointment form
     */
    @GetMapping("/schedule/{id}")
    public String showScheduleForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            List<Doctor> doctors = doctorRepository.findAll();
            model.addAttribute("patient", patient.get());
            model.addAttribute("doctors", doctors);
            return "patient/schedule";
        }
        redirectAttributes.addFlashAttribute("error", "Patient not found");
        return "redirect:/patient/list";
    }

    /**
     * Process schedule appointment
     */
    @PostMapping("/schedule/{id}")
    public String processSchedule(@PathVariable Long id,
                                  @RequestParam Long doctorId,
                                  @RequestParam String appointmentDateTime,
                                  @RequestParam(required = false) String reason,
                                  @RequestParam(required = false) String notes,
                                  @RequestParam(required = false) String priority,
                                  RedirectAttributes redirectAttributes) {
        try {
            Optional<Patient> patient = patientService.getPatientById(id);
            Optional<Doctor> doctor = doctorRepository.findById(doctorId);
            
            if (patient.isPresent() && doctor.isPresent()) {
                Appointment appointment = new Appointment();
                appointment.setPatient(patient.get());
                appointment.setDoctor(doctor.get());
                appointment.setAppointmentDateTime(LocalDateTime.parse(appointmentDateTime));
                appointment.setReason(reason);
                appointment.setNotes(notes);
                appointment.setPriority(priority != null ? priority : "NORMAL");
                appointment.setStatus("SCHEDULED");
                appointment.setCreatedAt(LocalDateTime.now());
                appointmentRepository.save(appointment);
                redirectAttributes.addFlashAttribute("success", "Appointment scheduled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Patient or Doctor not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error scheduling appointment: " + e.getMessage());
        }
        return "redirect:/patient/list";
    }

    /**
     * View patient with appointments
     */
    @GetMapping("/view/{id}")
    public String viewPatientDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            Patient p = patient.get();
            List<Appointment> appointments = appointmentRepository.findByPatient(p);
            model.addAttribute("patient", p);
            model.addAttribute("appointments", appointments);
            return "patient/view";
        }
        redirectAttributes.addFlashAttribute("error", "Patient not found");
        return "redirect:/patient/list";
    }
}
