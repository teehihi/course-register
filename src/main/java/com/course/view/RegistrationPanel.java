package com.course.view;

import com.course.model.Section;
import com.course.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegistrationPanel extends JPanel {

    // Thông tin sinh viên
    private JTextField txtStudentId;
    private JButton btnLoad;
    private JLabel lblStudentInfo;

    // Tìm kiếm
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JButton btnRefresh;

    // Bảng danh sách lớp
    private JTable tblSections;
    private DefaultTableModel sectionModel;

    // Bảng lớp đã đăng ký
    private JTable tblRegistered;
    private DefaultTableModel registeredModel;

    // Nút hành động
    private JButton btnRegister;
    private JButton btnDrop;
    private JButton btnViewRegistered;

    public RegistrationPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        initComponents();
    }

    private void initComponents() {
        // Panel trên: thông tin sinh viên + tìm kiếm
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Row 1: Thông tin sinh viên
        JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        studentPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        txtStudentId = new JTextField(10);
        btnLoad = new JButton("Load Sinh viên");
        lblStudentInfo = new JLabel("Chưa tải thông tin sinh viên");
        studentPanel.add(new JLabel("Student ID:"));
        studentPanel.add(txtStudentId);
        studentPanel.add(btnLoad);
        studentPanel.add(lblStudentInfo);

        // Row 2: Tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm học phần / giảng viên"));
        txtKeyword = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        btnRefresh = new JButton("Tải lại");
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtKeyword);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        topPanel.add(studentPanel);
        topPanel.add(searchPanel);

        // Bảng danh sách lớp
        String[] sectionCols = {"Section ID", "Course ID", "Course Name", "Credits", "Instructor", "Room", "Capacity", "Current", "Slots Left"};
        sectionModel = new DefaultTableModel(sectionCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblSections = new JTable(sectionModel);
        tblSections.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollSections = new JScrollPane(tblSections);
        scrollSections.setBorder(BorderFactory.createTitledBorder("Danh sách lớp học phần"));
        scrollSections.setPreferredSize(new Dimension(0, 200));

        // Bảng lớp đã đăng ký
        String[] regCols = {"Section ID", "Course ID", "Course Name", "Credits", "Instructor", "Room"};
        registeredModel = new DefaultTableModel(regCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblRegistered = new JTable(registeredModel);
        tblRegistered.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollRegistered = new JScrollPane(tblRegistered);
        scrollRegistered.setBorder(BorderFactory.createTitledBorder("Các lớp đã đăng ký"));
        scrollRegistered.setPreferredSize(new Dimension(0, 150));

        // Panel nút
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnRegister = new JButton("Đăng ký lớp đã chọn");
        btnDrop = new JButton("Hủy lớp đã chọn");
        btnViewRegistered = new JButton("Xem lớp đã đăng ký");
        Dimension btnSize = new Dimension(180, 30);
        btnRegister.setPreferredSize(btnSize);
        btnDrop.setPreferredSize(btnSize);
        btnViewRegistered.setPreferredSize(btnSize);
        btnRegister.setBackground(new Color(173, 216, 230));
        btnRegister.setOpaque(true);
        btnRegister.setBorderPainted(false);
        btnDrop.setBackground(new Color(255, 182, 193));
        btnDrop.setOpaque(true);
        btnDrop.setBorderPainted(false);
        btnPanel.add(btnRegister);
        btnPanel.add(btnDrop);
        btnPanel.add(btnViewRegistered);

        // Ghép layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollSections, scrollRegistered);
        splitPane.setResizeWeight(0.6);

        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // --- Populate methods ---

    public void populateSections(List<Section> sections) {
        sectionModel.setRowCount(0);
        for (Section s : sections) {
            sectionModel.addRow(new Object[]{
                s.getSectionId(), s.getCourseId(), s.getCourseName(),
                s.getCredits(), s.getInstructor(), s.getRoom(),
                s.getCapacity(), s.getCurrentEnrollment(), s.getSlotsLeft()
            });
        }
    }

    public void populateRegistered(List<Section> sections) {
        registeredModel.setRowCount(0);
        for (Section s : sections) {
            registeredModel.addRow(new Object[]{
                s.getSectionId(), s.getCourseId(), s.getCourseName(),
                s.getCredits(), s.getInstructor(), s.getRoom()
            });
        }
    }

    public void setStudentInfo(Student student) {
        if (student != null) {
            lblStudentInfo.setText(student.getFullName() + " | " + student.getMajor() +
                " | Tín chỉ hiện tại: " + student.getTotalCredits() + "/20");
        } else {
            lblStudentInfo.setText("Không tìm thấy sinh viên.");
        }
    }

    // --- Getters ---

    public int getStudentId() {
        try { return Integer.parseInt(txtStudentId.getText().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public int getSelectedSectionId() {
        int row = tblSections.getSelectedRow();
        if (row < 0) return -1;
        return (int) sectionModel.getValueAt(row, 0);
    }

    public int getSelectedRegisteredSectionId() {
        int row = tblRegistered.getSelectedRow();
        if (row < 0) return -1;
        return (int) registeredModel.getValueAt(row, 0);
    }

    public String getKeyword() { return txtKeyword.getText().trim(); }

    public JButton getBtnLoad() { return btnLoad; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnDrop() { return btnDrop; }
    public JButton getBtnViewRegistered() { return btnViewRegistered; }
}
