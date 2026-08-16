package cn.zust.demo.controller;

import cn.zust.demo.dao.CourseDao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/deleteCourse")
public class DeleteCourseServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)

            throws ServletException, IOException {

        String courseNo =
                req.getParameter("courseNo");

        CourseDao dao =
                new CourseDao();

        dao.deleteCourse(courseNo);

        resp.sendRedirect(
                "admin/courses.jsp");
    }
}
