package com.unischedule.unischedule.controller;

import com.unischedule.unischedule.model.Reminder;
import com.unischedule.unischedule.model.Student;
import com.unischedule.unischedule.service.ReminderService;
import com.unischedule.unischedule.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students/{studentId}/reminders")
public class ReminderController {

    private final ReminderService reminderService;
    private final StudentService studentService;

    @Autowired
    public ReminderController(ReminderService reminderService, StudentService studentService) {
        this.reminderService = reminderService;
        this.studentService = studentService;
    }

    @GetMapping
    public String viewReminders(@PathVariable Long studentId, Model model) {
        model.addAttribute("reminders", reminderService.getActiveRemindersForStudent(studentId));
        model.addAttribute("studentId", studentId);
        return "reminders/list";
    }

    @GetMapping("/new")
    public String showCreateForm(@PathVariable Long studentId, Model model) {
        Reminder reminder = new Reminder();
        Student student = studentService.getStudentById(studentId).orElse(null);
        reminder.setStudent(student);
        model.addAttribute("reminder", reminder);
        model.addAttribute("studentId", studentId);
        return "reminders/form";
    }

    @PostMapping
    public String createReminder(@PathVariable Long studentId, @ModelAttribute Reminder reminder) {
        Student student = studentService.getStudentById(studentId).orElse(null);
        reminder.setStudent(student);
        reminderService.saveReminder(reminder);
        return "redirect:/students/" + studentId + "/reminders";
    }

    @PostMapping("/{id}/complete")
    public String completeReminder(@PathVariable Long studentId, @PathVariable Long id) {
        reminderService.markComplete(id);
        return "redirect:/students/" + studentId + "/reminders";
    }

    @PostMapping("/{id}/delete")
    public String deleteReminder(@PathVariable Long studentId, @PathVariable Long id) {
        reminderService.deleteReminder(id);
        return "redirect:/students/" + studentId + "/reminders";
    }
}