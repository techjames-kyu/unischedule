package com.unischedule.unischedule.controller;

import com.unischedule.unischedule.model.Student;
import com.unischedule.unischedule.model.TimetableEntry;
import com.unischedule.unischedule.service.StudentService;
import com.unischedule.unischedule.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students/{studentId}/timetable")
public class TimetableController {

    private final TimetableService timetableService;
    private final StudentService studentService;

    @Autowired
    public TimetableController(TimetableService timetableService, StudentService studentService) {
        this.timetableService = timetableService;
        this.studentService = studentService;
    }

    @GetMapping
    public String viewTimetable(@PathVariable Long studentId, Model model) {
        model.addAttribute("entries", timetableService.getEntriesForStudent(studentId));
        model.addAttribute("studentId", studentId);
        return "timetable/list";
    }

    @GetMapping("/new")
    public String showCreateForm(@PathVariable Long studentId, Model model) {
        TimetableEntry entry = new TimetableEntry();
        Student student = studentService.getStudentById(studentId).orElse(null);
        entry.setStudent(student);
        model.addAttribute("entry", entry);
        model.addAttribute("studentId", studentId);
        return "timetable/form";
    }

    @PostMapping
    public String createEntry(@PathVariable Long studentId, @ModelAttribute TimetableEntry entry, Model model) {
        Student student = studentService.getStudentById(studentId).orElse(null);
        entry.setStudent(student);
        try {
            timetableService.saveEntry(entry);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("entry", entry);
            model.addAttribute("studentId", studentId);
            return "timetable/form";
        }
        return "redirect:/students/" + studentId + "/timetable";
    }

    @PostMapping("/{id}/delete")
    public String deleteEntry(@PathVariable Long studentId, @PathVariable Long id) {
        timetableService.deleteEntry(id);
        return "redirect:/students/" + studentId + "/timetable";
    }
}