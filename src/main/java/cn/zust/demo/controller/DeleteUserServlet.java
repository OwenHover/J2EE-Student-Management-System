package cn.zust.demo.controller;

import cn.zust.demo.dao.StudentDao;
import cn.zust.demo.dao.UserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String account = req.getParameter("account");
        String role    = req.getParameter("role");

        if ("admin".equals(role)) {
            UserDao dao = new UserDao();
            dao.deleteAdmin(account);
        } else {
            StudentDao dao = new StudentDao();
            dao.deleteStudent(account); // account = sno for students
        }

        resp.sendRedirect(req.getContextPath() + "/admin/users.jsp");
    }
}
