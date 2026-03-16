package com.course.dao;

import com.course.model.Registration;
import com.course.model.Section;
import java.util.List;

public interface IRegistrationDAO {
    void save(Registration registration);
    void updateStatus(int studentId, int sectionId, String status);
    List<Section> findRegisteredSectionsByStudent(int studentId);
    boolean isAlreadyRegistered(int studentId, int sectionId);
}
