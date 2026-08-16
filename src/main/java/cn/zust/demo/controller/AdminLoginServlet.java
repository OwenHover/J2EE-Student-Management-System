package cn.zust.demo.controller;

import cn.zust.demo.dao.AdminDashboardDao;
import cn.zust.demo.dao.UserDao;
import cn.zust.demo.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String actionType = req.getParameter("actionType");
        HttpSession session = req.getSession();

        UserDao userDao = new UserDao();

        // ================= LOGIN =================
        if ("login".equals(actionType)) {

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if ("".equals(username) || "".equals(password)) {
                session.setAttribute("errorInfo", "username or password is empty");
                resp.sendRedirect("admin/fail.jsp");
                return;
            }

            User user = userDao.getUserByAccount(username);

            if (user != null && password.equals(user.getPwd())) {

                session.setAttribute("loginAdmin", user);

                AdminDashboardDao dao = new AdminDashboardDao();

                Map<String, Object> totalMap = new HashMap<>();
                totalMap.put("students", dao.getTotalStudents());
                totalMap.put("courses", dao.getTotalCourses());
                totalMap.put("enrollments", dao.getEnrollmentRecords());
                totalMap.put("grades", dao.getGradesEntered());

                session.setAttribute("totalMap", totalMap);

                resp.sendRedirect("admin/home.jsp");

            } else {
                session.setAttribute("errorInfo", "Login failed");
                resp.sendRedirect("admin/fail.jsp");
            }

        }

        // ================= UPDATE PASSWORD =================
        else if ("updatePwd".equals(actionType)) {

            String account = req.getParameter("a_account");
            String oldPwd = req.getParameter("oldPwd");
            String newPwd = req.getParameter("newPwd");
            String confirmPwd = req.getParameter("confirmPwd");

            if (oldPwd == null || oldPwd.equals("")) {
                session.setAttribute("errorInfo", "No old password!");
                resp.sendRedirect("admin/error.jsp");
                return;
            }

            if (!newPwd.equals(confirmPwd)) {
                session.setAttribute("errorInfo", "Passwords do not match!");
                resp.sendRedirect("admin/error.jsp");
                return;
            }

            User user = userDao.getUserByAccount(account);

            if (user == null || !oldPwd.equals(user.getPwd())) {
                session.setAttribute("errorInfo", "Old password incorrect!");
                resp.sendRedirect("admin/error.jsp");
                return;
            }

            boolean flag = userDao.updatePassword(account, newPwd);

            if (flag) {
                resp.sendRedirect("admin/login.jsp"); // force re-login
            } else {
                session.setAttribute("errorInfo", "Update password error");
                resp.sendRedirect("admin/error.jsp");
            }
        }
    }
}