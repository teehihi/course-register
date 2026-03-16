package com.course.dao;

import com.course.model.Section;
import java.util.List;

public interface ISectionDAO {
    List<Section> findAll();
    List<Section> findByKeyword(String keyword);
    Section findById(int sectionId);
    void updateEnrollment(int sectionId, int delta);
}
