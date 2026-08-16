package cn.zust.demo.controller;

import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.dao.UserDao;
import cn.zust.demo.entity.Student;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String account = req.getParameter("account");
        String role    = req.getParameter("role");
        String pwd     = req.getParameter("pwd");

        if ("admin".equals(role)) {
            // Update password in admin_user table
            UserDao dao = new UserDao();
            dao.updatePassword(account, pwd);

        } else {
            // Update password in student table
            StudentDao dao    = new StudentDao();
            Student    student = dao.getStudentBySno(account);
            if (student != null) {
                student.setPsw(pwd);
                dao.updateStudent(student);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users.jsp");
    }
}
