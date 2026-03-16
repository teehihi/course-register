package com.course.view;

import com.course.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {

    private JTable tblCourses;
    private DefaultTableModel courseModel;

    private JTextField txtCourseId;
    private JTextField txtCourseName;
    private JTextField txtCredits;

    private JButton btnSave;
    private JButton btnDelete;
    private JButton btnClear;

    public AdminPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        // Bảng danh sách môn học
        String[] cols = {"Course ID", "Course Name", "Credits"};
        courseModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCourses = new JTable(courseModel);
        tblCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tblCourses);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thêm / Sửa môn học"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtCourseId = new JTextField(15);
        txtCourseName = new JTextField(25);
        txtCredits = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 1; formPanel.add(txtCourseId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Tên môn học:"), gbc);
        gbc.gridx = 1; formPanel.add(txtCourseName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Số tín chỉ:"), gbc);
        gbc.gridx = 1; formPanel.add(txtCredits, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnSave = new JButton("Lưu môn học");
        btnDelete = new JButton("Xóa môn học");
        btnClear = new JButton("Xóa form");
        btnSave.setBackground(new Color(173, 216, 230));
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);
        btnDelete.setBackground(new Color(255, 182, 193));
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(false);
        btnPanel.add(btnSave);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Click row -> fill form
        tblCourses.getSelectionModel().addListSelectionListener(e -> {
            int row = tblCourses.getSelectedRow();
            if (row >= 0) {
                txtCourseId.setText(courseModel.getValueAt(row, 0).toString());
                txtCourseName.setText(courseModel.getValueAt(row, 1).toString());
                txtCredits.setText(courseModel.getValueAt(row, 2).toString());
            }
        });
    }

    public void populateCourses(List<Course> courses) {
        courseModel.setRowCount(0);
        for (Course c : courses) {
            courseModel.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getCredits()});
        }
    }

    public Course getFormCourse() {
        try {
            return new Course(
                txtCourseId.getText().trim(),
                txtCourseName.getText().trim(),
                Integer.parseInt(txtCredits.getText().trim())
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getSelectedCourseId() {
        int row = tblCourses.getSelectedRow();
        if (row < 0) return null;
        return courseModel.getValueAt(row, 0).toString();
    }

    public void clearForm() {
        txtCourseId.setText("");
        txtCourseName.setText("");
        txtCredits.setText("");
        tblCourses.clearSelection();
    }

    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
}
