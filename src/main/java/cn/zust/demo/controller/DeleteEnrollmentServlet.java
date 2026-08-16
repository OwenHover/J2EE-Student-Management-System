package cn.zust.demo.controller;

import cn.zust.demo.dao.EnrollmentDao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/deleteEnrollment")
public class DeleteEnrollmentServlet
        extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String sno =
                request.getParameter("sno");

        String courseno =
                request.getParameter("courseno");

        EnrollmentDao dao =
                new EnrollmentDao();

        dao.deleteEnrollment(
                sno,
                courseno);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/selections.jsp");
    }
}

