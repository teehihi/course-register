package com.course.service;

import com.course.model.Section;
import com.course.model.ServiceResult;
import com.course.model.Student;

import java.util.List;

public interface IRegistrationService {
    Student loadStudent(int studentId);
    ServiceResult register(int studentId, int sectionId);
    ServiceResult drop(int studentId, int sectionId);
    List<Section> getRegisteredSections(int studentId);
    List<Section> searchSections(String keyword);
    List<Section> getAllSections();
}
