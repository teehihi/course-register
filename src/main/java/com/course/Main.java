package com.course;

import com.course.controller.AdminController;
import com.course.controller.RegistrationController;
import com.course.service.impl.CurriculumServiceImpl;
import com.course.service.impl.RegistrationServiceImpl;
import com.course.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            MainFrame frame = new MainFrame();

            // Khởi tạo services
            RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
            CurriculumServiceImpl curriculumService = new CurriculumServiceImpl();

            // Kết nối Controller với View và Service
            // Thứ tự: Model -> DAO -> Service -> Controller (theo Sequence Diagram)
            new RegistrationController(frame.getRegistrationPanel(), registrationService);
            new AdminController(frame.getAdminPanel(), curriculumService);

            frame.setVisible(true);
        });
    }
}
