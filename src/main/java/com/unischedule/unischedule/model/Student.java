package com.unischedule.unischedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Column(nullable = false)
    private String email;
     
    @NotBlank(message = "Registration number is required")
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    private String course;

    // Constructors
    public Student() {
    }

    public Student(String name, String registrationNumber, String course, String email) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.course = course;
        this.email = email;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getEmail() {
    return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}