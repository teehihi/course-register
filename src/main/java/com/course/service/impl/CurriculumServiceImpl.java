package com.course.service.impl;

import com.course.dao.ICourseDAO;
import com.course.dao.impl.CourseDAOImpl;
import com.course.model.Course;
import com.course.model.ServiceResult;
import com.course.service.ICurriculumService;

import java.util.List;

public class CurriculumServiceImpl implements ICurriculumService {

    private final ICourseDAO courseDAO = new CourseDAOImpl();

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    @Override
    public ServiceResult saveCourse(Course course) {
        if (course.getCourseId() == null || course.getCourseId().isBlank())
            return ServiceResult.fail("Mã môn học không được để trống.");
        if (course.getCourseName() == null || course.getCourseName().isBlank())
            return ServiceResult.fail("Tên môn học không được để trống.");
        if (course.getCredits() <= 0)
            return ServiceResult.fail("Số tín chỉ phải lớn hơn 0.");
        courseDAO.save(course);
        return ServiceResult.ok("Lưu môn học thành công: " + course.getCourseId());
    }

    @Override
    public ServiceResult deleteCourse(String courseId) {
        if (courseDAO.findById(courseId) == null)
            return ServiceResult.fail("Không tìm thấy môn học: " + courseId);
        courseDAO.delete(courseId);
        return ServiceResult.ok("Đã xóa môn học: " + courseId);
    }
}
