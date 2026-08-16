package cn.zust.demo.controller;

import cn.zust.demo.dao.CourseDao;
import cn.zust.demo.entity.Course;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/updateCourse")
public class UpdateCourseServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // DEBUG BLOCK
        System.out.println("=== UPDATE COURSE DEBUG ===");
        System.out.println("courseNo = " + req.getParameter("courseNo"));
        System.out.println("courseName = " + req.getParameter("courseName"));
        System.out.println("credits = " + req.getParameter("credits"));
        System.out.println("instructor = " + req.getParameter("instructor"));
        System.out.println("instructorEmail = " + req.getParameter("instructorEmail"));
        System.out.println("classTime = " + req.getParameter("classTime"));
        System.out.println("semester = " + req.getParameter("semester"));
        System.out.println("courseType = " + req.getParameter("courseType"));
        System.out.println("assessment = " + req.getParameter("assessment"));
        System.out.println("enrollmentLimit = " + req.getParameter("enrollmentLimit"));

        Course course = new Course();

        course.setCourseNo(req.getParameter("courseNo"));
        course.setCourseName(req.getParameter("courseName"));

        String credits = req.getParameter("credits");
        if (credits != null && !credits.isEmpty()) {
            course.setCredits(Integer.parseInt(credits));
        } else {
            course.setCredits(0);
        }

        course.setInstructor(req.getParameter("instructor"));
        course.setInstructorEmail(req.getParameter("instructorEmail"));
        course.setSemester(req.getParameter("semester"));
        course.setClassTime(req.getParameter("classTime"));
        course.setLocation(req.getParameter("location"));
        course.setRemarks(req.getParameter("remarks"));
        course.setOtherInfo(req.getParameter("otherInfo"));
        course.setCourseType(req.getParameter("courseType"));
        course.setAssessment(req.getParameter("assessment"));

        String limit = req.getParameter("enrollmentLimit");
        if (limit != null && !limit.isEmpty()) {
            course.setEnrollmentLimit(Integer.parseInt(limit));
        } else {
            course.setEnrollmentLimit(0);
        }

        CourseDao dao = new CourseDao();
        dao.updateCourse(course);

        resp.sendRedirect("admin/courses.jsp");
    }
}