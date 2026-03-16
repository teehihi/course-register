package com.course.model;

public class Section {
    private int sectionId;
    private String courseId;
    private String courseName;
    private int credits;
    private String instructor;
    private String room;
    private int capacity;
    private int currentEnrollment;

    public Section() {}

    public Section(int sectionId, String courseId, String courseName, int credits,
                   String instructor, String room, int capacity, int currentEnrollment) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.instructor = instructor;
        this.room = room;
        this.capacity = capacity;
        this.currentEnrollment = currentEnrollment;
    }

    /** Business rule: kiểm tra lớp còn chỗ không */
    public boolean isFull() {
        return currentEnrollment >= capacity;
    }

    public int getSlotsLeft() {
        return capacity - currentEnrollment;
    }

    public int getSectionId() { return sectionId; }
    public void setSectionId(int sectionId) { this.sectionId = sectionId; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }
}
