package com.unischedule.unischedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Optional link to a specific timetable entry (nullable — some reminders are standalone, e.g. "submit assignment")
    @ManyToOne
    @JoinColumn(name = "timetable_entry_id")
    private TimetableEntry timetableEntry;

    @NotBlank(message = "Message is required")
    @Column(nullable = false)
    private String message;

    @NotNull(message = "Due date/time is required")
    @Column(nullable = false)
    private LocalDateTime dueDateTime;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(nullable = false)
    private boolean notified = false;

    // Constructors
    public Reminder() {
    }

    public Reminder(Student student, TimetableEntry timetableEntry, String message, LocalDateTime dueDateTime) {
        this.student = student;
        this.timetableEntry = timetableEntry;
        this.message = message;
        this.dueDateTime = dueDateTime;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TimetableEntry getTimetableEntry() {
        return timetableEntry;
    }

    public void setTimetableEntry(TimetableEntry timetableEntry) {
        this.timetableEntry = timetableEntry;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}