package com.course.dao.impl;

import com.course.config.DBConfig;
import com.course.dao.IStudentDAO;
import com.course.model.Student;

import java.sql.*;

public class StudentDAOImpl implements IStudentDAO {

    @Override
    public Student findById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getInt("student_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("major"),
                    rs.getInt("total_credits")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTotalCredits(int studentId, int delta) {
        String sql = "UPDATE students SET total_credits = total_credits + ? WHERE student_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
