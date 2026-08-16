package cn.zust.demo.controller;

import cn.zust.demo.dao.StudentDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/deleteStudent")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sno = req.getParameter("sno");

        StudentDao dao = new StudentDao();

        dao.deleteStudent(sno);

        resp.sendRedirect("admin/students.jsp");
    }
}
