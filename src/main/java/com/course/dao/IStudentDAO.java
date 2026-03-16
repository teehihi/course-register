package com.course.dao;

import com.course.model.Student;

public interface IStudentDAO {
    Student findById(int studentId);
    void updateTotalCredits(int studentId, int delta);
}
