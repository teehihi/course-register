package com.course.model;

public class Student {
    private int studentId;
    private String fullName;
    private String email;
    private String major;
    private int totalCredits;

    public Student() {}

    public Student(int studentId, String fullName, String email, String major, int totalCredits) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.major = major;
        this.totalCredits = totalCredits;
    }

    /** Business rule: max 20 tín chỉ/học kỳ */
    public boolean canRegisterMore(int credits) {
        return (totalCredits + credits) <= 20;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public int getTotalCredits() { return totalCredits; }
    public void setTotalCredits(int totalCredits) { this.totalCredits = totalCredits; }

    @Override
    public String toString() {
        return fullName + " (" + major + ") - " + totalCredits + " tín chỉ";
    }
}
