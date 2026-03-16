package com.course.dao.impl;

import com.course.config.DBConfig;
import com.course.dao.ICourseDAO;
import com.course.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements ICourseDAO {

    @Override
    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Course(rs.getString("course_id"), rs.getString("course_name"), rs.getInt("credits")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Course findById(String courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Course(rs.getString("course_id"), rs.getString("course_name"), rs.getInt("credits"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Course course) {
        String sql = "INSERT INTO courses (course_id, course_name, credits) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE course_name = VALUES(course_name), credits = VALUES(credits)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getCourseId());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getCredits());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
