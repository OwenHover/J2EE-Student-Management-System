package cn.zust.demo.controller;

import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.dao.UserDao;
import cn.zust.demo.entity.Student;
import cn.zust.demo.entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String role = req.getParameter("role");
        String pwd  = req.getParameter("pwd");

        if ("admin".equals(role)) {
            // Add to admin_user table
            String account   = req.getParameter("account");
            String adminName = req.getParameter("adminName");

            User user = new User();
            user.setAccount(account);
            user.setPwd(pwd);
            user.setName(adminName != null ? adminName : "");

            UserDao dao = new UserDao();
            dao.insert(user);

        } else {
            // Add to student table
            String sno   = req.getParameter("sno");
            String sname = req.getParameter("sname");

            Student student = new Student();
            student.setSno(sno);
            student.setSname(sname);
            student.setPsw(pwd);
            // Set defaults for required fields
            student.setGender("Male");
            student.setClassname("");
            student.setCollegename("");
            student.setCountry("");
            student.setDepart("");

            StudentDao dao = new StudentDao();
            dao.addStudent(student);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users.jsp");
    }
}

