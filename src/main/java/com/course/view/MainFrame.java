package com.course.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private RegistrationPanel registrationPanel;
    private AdminPanel adminPanel;

    public MainFrame() {
        setTitle("Course Registration Management System - Java Swing MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        registrationPanel = new RegistrationPanel();
        adminPanel = new AdminPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sinh viên đăng ký học phần", registrationPanel);
        tabbedPane.addTab("Quản trị chương trình học", adminPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public RegistrationPanel getRegistrationPanel() { return registrationPanel; }
    public AdminPanel getAdminPanel() { return adminPanel; }
}
