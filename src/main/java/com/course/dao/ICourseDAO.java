package com.course.dao;

import com.course.model.Course;
import java.util.List;

public interface ICourseDAO {
    List<Course> findAll();
    Course findById(String courseId);
    void save(Course course);
    void delete(String courseId);
}
