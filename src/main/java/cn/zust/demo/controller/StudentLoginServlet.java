package cn.zust.demo.controller;

import cn.zust.demo.dao.EnrollmentDao;
import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/studentLogin")
public class StudentLoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("student/login.jsp");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        if ("".equals(username) || "".equals(password)) {
            session.setAttribute("errorInfo", "username or password is empty, please input it");
            resp.sendRedirect("student/error.jsp");
        }
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.getStudnetBySnoAndPsw(username, password);
        if (null != student) {
            session.setAttribute("loginStudent", student);

            EnrollmentDao enrollmentDao = new EnrollmentDao();

            Map<String, Object> totalMap = new HashMap<>();
            totalMap.put("totalCourse", enrollmentDao.getTotalCourseBySno(student.getSno()));
            totalMap.put("totalPass", enrollmentDao.getTotalPassBySno(student.getSno()));
            totalMap.put("totalNoMark", enrollmentDao.getTotalNoMarkBySno(student.getSno()));
            totalMap.put("totalFail", enrollmentDao.getTotalNotPassBySno(student.getSno()));

            session.setAttribute("totalMap", totalMap);

            resp.sendRedirect("student/home.jsp");
        }

    }
}
