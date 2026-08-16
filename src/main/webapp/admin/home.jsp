<%@ page import="cn.zust.demo.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="cn.zust.demo.dao.AdminDashboardDao" %>

<%
  User admin = (User) session.getAttribute("loginAdmin");
  if (admin == null) {
    response.sendRedirect("/scm/admin/login.jsp");
    return;
  }
%>
<%

  AdminDashboardDao dashboardDao =
          new AdminDashboardDao();

  int totalStudents =
          dashboardDao.getTotalStudents();

  int totalCourses =
          dashboardDao.getTotalCourses();

  int totalEnrollments =
          dashboardDao.getEnrollmentRecords();

  int totalGrades =
          dashboardDao.getGradesEntered();

%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - Course Management System</title>
<style>
  * { margin: 0; padding: 0; box-sizing: border-box; }
  body { font-family: "Segoe UI", Arial, sans-serif; background: #f0f2f5; min-height: 100vh; }
  .topbar { background: #1557b0; color: #fff; height: 50px; display: flex; align-items: center; padding: 0 24px; justify-content: space-between; }
  .topbar .sys-name { font-size: 16px; font-weight: 600; }
  .topbar .admin-info { font-size: 13px; color: rgba(255,255,255,0.8); }
  .topbar .logout-btn { background: rgba(255,255,255,0.15); border: 1px solid rgba(255,255,255,0.3); color: #fff; padding: 5px 16px; border-radius: 4px; font-size: 13px; cursor: pointer; font-family: inherit; }
  .layout { display: flex; min-height: calc(100vh - 50px); }
  .sidebar { width: 200px; background: #fff; border-right: 1px solid #e8e8e8; padding: 16px 0; flex-shrink: 0; }
  .sidebar-title { font-size: 12px; color: #aaa; padding: 8px 20px 4px; letter-spacing: 1px; text-transform: uppercase; }
  .sidebar a { display: flex; align-items: center; gap: 10px; padding: 10px 20px; color: #555; text-decoration: none; font-size: 14px; }
  .sidebar a:hover { background: #f0f4ff; color: #1a73e8; }
  .sidebar a.active { background: #e8f0fe; color: #1a73e8; font-weight: 600; border-right: 3px solid #1a73e8; }
  .sidebar .icon { font-size: 16px; width: 20px; text-align: center; }
  .content { flex: 1; padding: 24px; }
  .page-title { font-size: 18px; color: #333; font-weight: 600; margin-bottom: 20px; }
  .stats { display: flex; gap: 16px; margin-bottom: 28px; flex-wrap: wrap; }
  .stat-card { flex: 1; min-width: 180px; background: #fff; border-radius: 8px; padding: 20px 24px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); border-top: 3px solid #1a73e8; }
  .stat-card.green { border-color: #34a853; }
  .stat-card.orange { border-color: #fbbc04; }
  .stat-card.purple { border-color: #8e24aa; }
  .stat-card .num { font-size: 32px; font-weight: 700; color: #333; }
  .stat-card .label { font-size: 13px; color: #888; margin-top: 4px; }
  .func-title { font-size: 15px; color: #333; font-weight: 600; margin-bottom: 14px; }
  .func-grid { display: grid; grid-template-columns: repeat(5, 1fr); gap: 14px; }
  .func-card { background: #fff; border-radius: 8px; padding: 20px 16px; text-align: center; text-decoration: none; color: #333; box-shadow: 0 1px 4px rgba(0,0,0,0.08); border: 1px solid #eee; transition: box-shadow 0.2s, border-color 0.2s; display: block; }
  .func-card:hover { box-shadow: 0 3px 10px rgba(26,115,232,0.15); border-color: #1a73e8; }
  .func-card .icon { font-size: 28px; display: block; margin-bottom: 10px; }
  .func-card .name { font-size: 14px; font-weight: 500; }
  .func-card .desc { font-size: 12px; color: #999; margin-top: 4px; }
</style>
</head>
<body>
<div class="topbar">
  <div class="sys-name">
    <%
      HttpSession httpSession = request.getSession();
      User us = (User) httpSession.getAttribute("loginAdmin");
    %>
    <%=us.getAccount()%>
  </div>
  <div class="admin-info">Logged in as: admin (System Administrator)</div>
  <button class="logout-btn" onclick="location.href='06_admin_login.html'">Logout</button>
</div>
<div class="layout">
  <div class="sidebar">
    <div class="sidebar-title">Management</div>
    <a href="home.jsp" class="active"><span class="icon">🏠</span>Dashboard</a>
    <a href="students.jsp"><span class="icon">👥</span>Students</a>
    <a href="courses.jsp"><span class="icon">📚</span>Courses</a>
    <a href="selections.jsp"><span class="icon">✏️</span>Enrollments</a>
    <a href="grades.jsp"><span class="icon">📊</span>Grades</a>
    <a href="users.jsp"><span class="icon">🔑</span>Users</a>
  </div>
  <div class="content">
    <div class="page-title">Dashboard Overview</div>
    <div class="stats">
      <div class="stat-card">
        <div class="num"><%= totalStudents %></div>
        <div class="label">Total Students</div>
      </div>

      <div class="stat-card green">
        <div class="num"><%=totalCourses%></div>
        <div class="label">Total Courses</div>
      </div>

      <div class="stat-card orange">
        <div class="num"><%=totalEnrollments%></div>
        <div class="label">Enrollment Records</div>
      </div>

      <div class="stat-card purple">
        <div class="num"><%=totalGrades%></div>
        <div class="label">Grades Entered</div>
      </div>
    </div>
    <div class="func-title">Quick Navigation</div>
    <div class="func-grid">
      <a class="func-card" href="students.jsp"><span class="icon">👥</span><div class="name">Students</div><div class="desc">Add / Edit / Delete</div></a>
      <a class="func-card" href="courses.jsp"><span class="icon">📚</span><div class="name">Courses</div><div class="desc">Add / Edit / Delete</div></a>
      <a class="func-card" href="selections.jsp"><span class="icon">✏️</span><div class="name">Enrollments</div><div class="desc">View / Delete</div></a>
      <a class="func-card" href="grades.jsp"><span class="icon">📊</span><div class="name">Grades</div><div class="desc">Enter / Modify</div></a>
      <a class="func-card" href="users.jsp"><span class="icon">🔑</span><div class="name">Users</div><div class="desc">Add / Edit / Delete</div></a>
    </div>
  </div>
</div>
</body>
</html>
