package com.course.service.impl;

import com.course.config.DBConfig;
import com.course.dao.IRegistrationDAO;
import com.course.dao.ISectionDAO;
import com.course.dao.IStudentDAO;
import com.course.dao.impl.RegistrationDAOImpl;
import com.course.dao.impl.SectionDAOImpl;
import com.course.dao.impl.StudentDAOImpl;
import com.course.model.*;
import com.course.service.IRegistrationService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RegistrationServiceImpl implements IRegistrationService {

    private final ISectionDAO sectionDAO = new SectionDAOImpl();
    private final IRegistrationDAO registrationDAO = new RegistrationDAOImpl();
    private final IStudentDAO studentDAO = new StudentDAOImpl();

    @Override
    public Student loadStudent(int studentId) {
        return studentDAO.findById(studentId);
    }

    @Override
    public ServiceResult register(int studentId, int sectionId) {
        // Bước 1: Lấy thông tin lớp
        Section section = sectionDAO.findById(sectionId);
        if (section == null) return ServiceResult.fail("Không tìm thấy lớp học phần.");

        // Bước 2: Kiểm tra sĩ số (Business Rule 1)
        if (section.isFull()) return ServiceResult.fail("Lớp đã hết chỗ (capacity: " + section.getCapacity() + ").");

        // Bước 3: Kiểm tra đã đăng ký chưa
        if (registrationDAO.isAlreadyRegistered(studentId, sectionId))
            return ServiceResult.fail("Bạn đã đăng ký lớp này rồi.");

        // Bước 4: Kiểm tra giới hạn tín chỉ (Business Rule 2)
        Student student = studentDAO.findById(studentId);
        if (student == null) return ServiceResult.fail("Không tìm thấy sinh viên ID: " + studentId);
        if (!student.canRegisterMore(section.getCredits()))
            return ServiceResult.fail("Vượt quá giới hạn 20 tín chỉ/học kỳ. Hiện tại: " + student.getTotalCredits());

        // Bước 5: Ghi nhận đăng ký trong Transaction
        try (Connection conn = DBConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // INSERT registration
                String insertSql = "INSERT INTO registrations (student_id, section_id, status) VALUES (?, ?, 'enrolled')";
                try (var ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, sectionId);
                    ps.executeUpdate();
                }
                // UPDATE current_enrollment
                String updateSql = "UPDATE sections SET current_enrollment = current_enrollment + 1 WHERE section_id = ?";
                try (var ps = conn.prepareStatement(updateSql)) {
                    ps.setInt(1, sectionId);
                    ps.executeUpdate();
                }
                // UPDATE total_credits của sinh viên
                String creditSql = "UPDATE students SET total_credits = total_credits + ? WHERE student_id = ?";
                try (var ps = conn.prepareStatement(creditSql)) {
                    ps.setInt(1, section.getCredits());
                    ps.setInt(2, studentId);
                    ps.executeUpdate();
                }
                conn.commit();
                return ServiceResult.ok("Đăng ký thành công lớp: " + section.getCourseName());
            } catch (SQLException e) {
                conn.rollback();
                return ServiceResult.fail("Lỗi hệ thống: " + e.getMessage());
            }
        } catch (SQLException e) {
            return ServiceResult.fail("Không thể kết nối database: " + e.getMessage());
        }
    }

    @Override
    public ServiceResult drop(int studentId, int sectionId) {
        if (!registrationDAO.isAlreadyRegistered(studentId, sectionId))
            return ServiceResult.fail("Bạn chưa đăng ký lớp này.");

        Section section = sectionDAO.findById(sectionId);
        if (section == null) return ServiceResult.fail("Không tìm thấy lớp học phần.");

        try (Connection conn = DBConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String updateReg = "UPDATE registrations SET status = 'dropped' WHERE student_id = ? AND section_id = ? AND status = 'enrolled'";
                try (var ps = conn.prepareStatement(updateReg)) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, sectionId);
                    ps.executeUpdate();
                }
                String updateSection = "UPDATE sections SET current_enrollment = current_enrollment - 1 WHERE section_id = ?";
                try (var ps = conn.prepareStatement(updateSection)) {
                    ps.setInt(1, sectionId);
                    ps.executeUpdate();
                }
                String updateCredits = "UPDATE students SET total_credits = total_credits - ? WHERE student_id = ?";
                try (var ps = conn.prepareStatement(updateCredits)) {
                    ps.setInt(1, section.getCredits());
                    ps.setInt(2, studentId);
                    ps.executeUpdate();
                }
                conn.commit();
                return ServiceResult.ok("Hủy đăng ký thành công: " + section.getCourseName());
            } catch (SQLException e) {
                conn.rollback();
                return ServiceResult.fail("Lỗi hệ thống: " + e.getMessage());
            }
        } catch (SQLException e) {
            return ServiceResult.fail("Không thể kết nối database: " + e.getMessage());
        }
    }

    @Override
    public List<Section> getRegisteredSections(int studentId) {
        return registrationDAO.findRegisteredSectionsByStudent(studentId);
    }

    @Override
    public List<Section> searchSections(String keyword) {
        return sectionDAO.findByKeyword(keyword);
    }

    @Override
    public List<Section> getAllSections() {
        return sectionDAO.findAll();
    }
}
