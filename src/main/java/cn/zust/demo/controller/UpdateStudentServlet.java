package cn.zust.demo.controller;

import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.entity.Student;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/updateStudent")
public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        Student student = new Student();

        student.setSno(req.getParameter("sno"));
        student.setSname(req.getParameter("sname"));
        student.setGender(req.getParameter("gender"));
        student.setClassname(req.getParameter("classname"));
        student.setDepart(req.getParameter("depart"));
        student.setCollegename(req.getParameter("college"));
        student.setCountry(req.getParameter("country"));
        student.setPsw(req.getParameter("psw"));

        StudentDao dao = new StudentDao();

        dao.updateStudent(student);

        resp.sendRedirect("admin/students.jsp");
    }
}