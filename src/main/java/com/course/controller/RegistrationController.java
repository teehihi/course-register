package com.course.controller;

import com.course.model.ServiceResult;
import com.course.model.Student;
import com.course.service.IRegistrationService;
import com.course.util.SwingMessage;
import com.course.view.RegistrationPanel;

public class RegistrationController {

    private final RegistrationPanel view;
    private final IRegistrationService service;
    private Student currentStudent;

    public RegistrationController(RegistrationPanel view, IRegistrationService service) {
        this.view = view;
        this.service = service;
        bindEvents();
        loadAllSections();
    }

    private void bindEvents() {
        view.getBtnLoad().addActionListener(e -> handleLoadStudent());
        view.getBtnSearch().addActionListener(e -> handleSearch());
        view.getBtnRefresh().addActionListener(e -> loadAllSections());
        view.getBtnRegister().addActionListener(e -> handleRegister());
        view.getBtnDrop().addActionListener(e -> handleDrop());
        view.getBtnViewRegistered().addActionListener(e -> handleViewRegistered());
    }

    private void loadAllSections() {
        view.populateSections(service.getAllSections());
    }

    private void handleLoadStudent() {
        int studentId = view.getStudentId();
        if (studentId < 0) {
            SwingMessage.error(view, "Student ID không hợp lệ.");
            return;
        }
        currentStudent = service.loadStudent(studentId);
        view.setStudentInfo(currentStudent);
        if (currentStudent != null) {
            view.populateRegistered(service.getRegisteredSections(studentId));
        }
    }

    private void handleSearch() {
        String keyword = view.getKeyword();
        if (keyword.isEmpty()) {
            loadAllSections();
        } else {
            view.populateSections(service.searchSections(keyword));
        }
    }

    private void handleRegister() {
        if (currentStudent == null) {
            SwingMessage.error(view, "Vui lòng tải thông tin sinh viên trước.");
            return;
        }
        int sectionId = view.getSelectedSectionId();
        if (sectionId < 0) {
            SwingMessage.error(view, "Vui lòng chọn một lớp học phần.");
            return;
        }
        ServiceResult result = service.register(currentStudent.getStudentId(), sectionId);
        if (result.isSuccess()) {
            SwingMessage.success(view, result.getMessage());
            // Refresh data
            currentStudent = service.loadStudent(currentStudent.getStudentId());
            view.setStudentInfo(currentStudent);
            view.populateRegistered(service.getRegisteredSections(currentStudent.getStudentId()));
            loadAllSections();
        } else {
            SwingMessage.error(view, result.getMessage());
        }
    }

    private void handleDrop() {
        if (currentStudent == null) {
            SwingMessage.error(view, "Vui lòng tải thông tin sinh viên trước.");
            return;
        }
        int sectionId = view.getSelectedRegisteredSectionId();
        if (sectionId < 0) {
            SwingMessage.error(view, "Vui lòng chọn lớp cần hủy trong bảng 'Các lớp đã đăng ký'.");
            return;
        }
        if (!SwingMessage.confirm(view, "Bạn có chắc muốn hủy đăng ký lớp này không?")) return;

        ServiceResult result = service.drop(currentStudent.getStudentId(), sectionId);
        if (result.isSuccess()) {
            SwingMessage.success(view, result.getMessage());
            currentStudent = service.loadStudent(currentStudent.getStudentId());
            view.setStudentInfo(currentStudent);
            view.populateRegistered(service.getRegisteredSections(currentStudent.getStudentId()));
            loadAllSections();
        } else {
            SwingMessage.error(view, result.getMessage());
        }
    }

    private void handleViewRegistered() {
        if (currentStudent == null) {
            SwingMessage.error(view, "Vui lòng tải thông tin sinh viên trước.");
            return;
        }
        view.populateRegistered(service.getRegisteredSections(currentStudent.getStudentId()));
    }
}
