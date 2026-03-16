package com.course.controller;

import com.course.model.Course;
import com.course.model.ServiceResult;
import com.course.service.ICurriculumService;
import com.course.util.SwingMessage;
import com.course.view.AdminPanel;

public class AdminController {

    private final AdminPanel view;
    private final ICurriculumService service;

    public AdminController(AdminPanel view, ICurriculumService service) {
        this.view = view;
        this.service = service;
        bindEvents();
        loadCourses();
    }

    private void bindEvents() {
        view.getBtnSave().addActionListener(e -> handleSave());
        view.getBtnDelete().addActionListener(e -> handleDelete());
        view.getBtnClear().addActionListener(e -> view.clearForm());
    }

    private void loadCourses() {
        view.populateCourses(service.getAllCourses());
    }

    private void handleSave() {
        Course course = view.getFormCourse();
        if (course == null) {
            SwingMessage.error(view, "Số tín chỉ phải là số nguyên hợp lệ.");
            return;
        }
        ServiceResult result = service.saveCourse(course);
        if (result.isSuccess()) {
            SwingMessage.success(view, result.getMessage());
            view.clearForm();
            loadCourses();
        } else {
            SwingMessage.error(view, result.getMessage());
        }
    }

    private void handleDelete() {
        String courseId = view.getSelectedCourseId();
        if (courseId == null) {
            SwingMessage.error(view, "Vui lòng chọn môn học cần xóa.");
            return;
        }
        if (!SwingMessage.confirm(view, "Xóa môn học " + courseId + "?")) return;
        ServiceResult result = service.deleteCourse(courseId);
        if (result.isSuccess()) {
            SwingMessage.success(view, result.getMessage());
            view.clearForm();
            loadCourses();
        } else {
            SwingMessage.error(view, result.getMessage());
        }
    }
}
