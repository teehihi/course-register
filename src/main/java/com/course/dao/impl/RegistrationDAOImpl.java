package com.course.dao.impl;

import com.course.config.DBConfig;
import com.course.dao.IRegistrationDAO;
import com.course.model.Registration;
import com.course.model.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAOImpl implements IRegistrationDAO {

    @Override
    public void save(Registration reg) {
        String sql = "INSERT INTO registrations (student_id, section_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reg.getStudentId());
            ps.setInt(2, reg.getSectionId());
            ps.setString(3, reg.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(int studentId, int sectionId, String status) {
        String sql = "UPDATE registrations SET status = ? WHERE student_id = ? AND section_id = ? AND status = 'enrolled'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, studentId);
            ps.setInt(3, sectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Section> findRegisteredSectionsByStudent(int studentId) {
        List<Section> list = new ArrayList<>();
        String sql = "SELECT s.section_id, s.course_id, c.course_name, c.credits, " +
                     "s.instructor, s.room, s.capacity, s.current_enrollment " +
                     "FROM registrations r " +
                     "JOIN sections s ON r.section_id = s.section_id " +
                     "JOIN courses c ON s.course_id = c.course_id " +
                     "WHERE r.student_id = ? AND r.status = 'enrolled'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Section(
                    rs.getInt("section_id"),
                    rs.getString("course_id"),
                    rs.getString("course_name"),
                    rs.getInt("credits"),
                    rs.getString("instructor"),
                    rs.getString("room"),
                    rs.getInt("capacity"),
                    rs.getInt("current_enrollment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isAlreadyRegistered(int studentId, int sectionId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND section_id = ? AND status = 'enrolled'";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
