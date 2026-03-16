package com.course.dao.impl;

import com.course.config.DBConfig;
import com.course.dao.ISectionDAO;
import com.course.model.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAOImpl implements ISectionDAO {

    private static final String BASE_QUERY =
        "SELECT s.section_id, s.course_id, c.course_name, c.credits, " +
        "s.instructor, s.room, s.capacity, s.current_enrollment " +
        "FROM sections s JOIN courses c ON s.course_id = c.course_id";

    private Section mapRow(ResultSet rs) throws SQLException {
        return new Section(
            rs.getInt("section_id"),
            rs.getString("course_id"),
            rs.getString("course_name"),
            rs.getInt("credits"),
            rs.getString("instructor"),
            rs.getString("room"),
            rs.getInt("capacity"),
            rs.getInt("current_enrollment")
        );
    }

    @Override
    public List<Section> findAll() {
        List<Section> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(BASE_QUERY)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Section> findByKeyword(String keyword) {
        List<Section> list = new ArrayList<>();
        String sql = BASE_QUERY + " WHERE c.course_name LIKE ? OR s.instructor LIKE ? OR s.course_id LIKE ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Section findById(int sectionId) {
        String sql = BASE_QUERY + " WHERE s.section_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateEnrollment(int sectionId, int delta) {
        String sql = "UPDATE sections SET current_enrollment = current_enrollment + ? WHERE section_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, sectionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
