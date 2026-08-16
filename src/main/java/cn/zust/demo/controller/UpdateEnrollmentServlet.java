package cn.zust.demo.controller;

import cn.zust.demo.dao.EnrollmentDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/updateEnrollment")
public class UpdateEnrollmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String sno      = req.getParameter("sno");
        String courseno = req.getParameter("courseno");
        String semester = req.getParameter("semester");
        String scoreStr = req.getParameter("score");

        Double score = null;
        if (scoreStr != null && !scoreStr.trim().isEmpty()) {
            score = Double.parseDouble(scoreStr);
        }

        EnrollmentDao dao = new EnrollmentDao();

        if (dao.updateEnrollment(sno, courseno, semester, score)) {
            resp.sendRedirect(req.getContextPath() + "/admin/enrollments.jsp");
        } else {
            resp.getWriter().write("Update enrollment failed.");
        }
    }
}

