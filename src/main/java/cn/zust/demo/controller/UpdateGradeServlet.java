package cn.zust.demo.controller;

import cn.zust.demo.dao.EnrollmentDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/updateGrade")
public class UpdateGradeServlet extends HttpServlet {

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

        // Auto-update entryTime to now when grade is entered or edited
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        EnrollmentDao dao = new EnrollmentDao();

        // updateEnrollment updates score + semester; we also need to update entryTime
        // We call the dedicated updateGrade method which only touches score + entryTime
        if (dao.updateGrade(sno, courseno, score, now)) {
            resp.sendRedirect(req.getContextPath() + "/admin/grades.jsp");
        } else {
            resp.getWriter().write("Failed to update grade.");
        }
    }
}
