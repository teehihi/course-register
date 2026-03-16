package com.course.service;

import com.course.model.Course;
import com.course.model.ServiceResult;

import java.util.List;

public interface ICurriculumService {
    List<Course> getAllCourses();
    ServiceResult saveCourse(Course course);
    ServiceResult deleteCourse(String courseId);
}
