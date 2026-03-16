package com.course.model;

import java.time.LocalDateTime;

public class Registration {
    private int regId;
    private int studentId;
    private int sectionId;
    private LocalDateTime registrationDate;
    private String status; // 'enrolled' hoặc 'dropped'

    public Registration() {}

    public Registration(int studentId, int sectionId) {
        this.studentId = studentId;
        this.sectionId = sectionId;
        this.status = "enrolled";
    }

    public int getRegId() { return regId; }
    public void setRegId(int regId) { this.regId = regId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
