package cn.zust.demo.controller;


import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.entity.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        //read form
        String sno = req.getParameter("sno");
        String sname = req.getParameter("sname");
        String gender = req.getParameter("gender");
        String classname = req.getParameter("classname");
        String college = req.getParameter("college");
        String country = req.getParameter("country");
        String depart = req.getParameter("depart");
        String psw = req.getParameter("psw");

        //build entity (new student)
        Student student = new Student();
        student.setSno(sno);
        student.setSname(sname);
        student.setGender(gender);
        student.setClassname(classname);
        student.setCollegename(college);
        student.setCountry(country);
        student.setDepart(depart);
        student.setPsw(psw);

        //hand off to model
        StudentDao dao = new StudentDao();


        //redirect back to view
        //error if same student num
        if (dao.addStudent(student)) {
            resp.sendRedirect("admin/students.jsp");
        } else {
            resp.getWriter().write("Add failed");
        }
    }
}
