<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="cn.zust.demo.entity.Student" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Student Dashboard</title>
<style>
  * { margin: 0; padding: 0; box-sizing: border-box; }
  body { font-family: "Segoe UI", Arial, sans-serif; background: #f0f2f5; min-height: 100vh; }
  .topbar { background: #1a73e8; color: #fff; height: 50px; display: flex; align-items: center; padding: 0 24px; justify-content: space-between; }
  .topbar .sys-name { font-size: 16px; font-weight: 600; }
  .topbar nav { display: flex; gap: 4px; }
  .topbar nav a { color: rgba(255,255,255,0.85); text-decoration: none; font-size: 14px; padding: 6px 14px; border-radius: 4px; }
  .topbar nav a:hover, .topbar nav a.active { background: rgba(255,255,255,0.15); color: #fff; }
  .topbar .logout-btn { background: rgba(255,255,255,0.15); border: 1px solid rgba(255,255,255,0.3); color: #fff; padding: 5px 16px; border-radius: 4px; font-size: 13px; cursor: pointer; font-family: inherit; }
  .main { max-width: 1100px; margin: 0 auto; padding: 24px 16px; }
  .welcome { margin-bottom: 20px; }
  .welcome h2 { font-size: 20px; color: #333; font-weight: 600; }
  .welcome p { color: #666; font-size: 14px; margin-top: 4px; }
  .stats { display: flex; gap: 16px; margin-bottom: 28px; flex-wrap: wrap; }
  .stat-card { flex: 1; min-width: 140px; background: #fff; border-radius: 8px; padding: 20px 24px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); border-left: 4px solid #1a73e8; }
  .stat-card.green { border-color: #34a853; }
  .stat-card.orange { border-color: #fbbc04; }
  .stat-card.red { border-color: #ea4335; }
  .stat-card .num { font-size: 28px; font-weight: 700; color: #333; }
  .stat-card .label { font-size: 13px; color: #888; margin-top: 4px; }
  .func-title { font-size: 15px; color: #333; font-weight: 600; margin-bottom: 14px; }
  .func-btns { display: flex; gap: 14px; flex-wrap: wrap; margin-bottom: 32px; }
  .func-btn { display: flex; flex-direction: column; align-items: center; justify-content: center; width: 120px; height: 88px; background: #fff; border: 1px solid #e0e0e0; border-radius: 8px; text-decoration: none; color: #333; font-size: 14px; font-weight: 500; transition: box-shadow 0.2s, border-color 0.2s; }
  .func-btn:hover { box-shadow: 0 2px 8px rgba(26,115,232,0.15); border-color: #1a73e8; color: #1a73e8; }
  .func-btn .icon { font-size: 26px; margin-bottom: 8px; }
  .section-title { font-size: 15px; font-weight: 600; color: #333; margin-bottom: 14px; }
  .semester-block { margin-bottom: 28px; }
  .semester-tag { display: inline-block; background: #e8f0fe; color: #1a73e8; font-size: 13px; font-weight: 600; padding: 4px 14px; border-radius: 12px; margin-bottom: 12px; }
  .table-wrap { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); overflow: hidden; }
  table { width: 100%; border-collapse: collapse; font-size: 14px; }
  thead tr { background: #f8f9fa; }
  th { padding: 11px 14px; text-align: left; color: #555; font-weight: 600; font-size: 13px; border-bottom: 1px solid #eee; }
  td { padding: 11px 14px; color: #333; border-bottom: 1px solid #f0f0f0; }
  tr:last-child td { border-bottom: none; }
  tr:hover td { background: #f8fbff; }
  .tag { display: inline-block; padding: 2px 10px; border-radius: 10px; font-size: 12px; font-weight: 500; }
  .tag-comp { background: #fff3e0; color: #e65100; }
  .tag-elect { background: #e8f5e9; color: #2e7d32; }
</style>
</head>
<body>
<div class="topbar">
    <%@include file ="studentheader.jsp"%>
</div>
<div class="main">
  <%
      Student student = (Student) session.getAttribute("loginStudent");
      if (null==student) {
          response.sendRedirect("/student/login.jsp");
      }

      Map<String,Object> totalMap = (Map<String,Object>)session.getAttribute("totalMap");
  %>
  <div class="welcome">
    <h2>Welcome back, <%=student.getSname() %></h2>
    <p>Student ID: <%=student.getSno()%> &nbsp;|&nbsp; <%=student.getDepart()%> &nbsp;|&nbsp; <%=student.getClassname()%></p>
  </div>
  <div class="stats">
    <div class="stat-card"><div class="num"><%=totalMap.get("totalCourse")%></div><div class="label">Total Enrolled</div></div>
    <div class="stat-card green"><div class="num"><%=totalMap.get("totalPass")%></div><div class="label">Passed</div></div>
    <div class="stat-card orange"><div class="num"><%=totalMap.get("totalNoMark")%></div><div class="label">Awaiting Grade</div></div>
    <div class="stat-card red"><div class="num"><%=totalMap.get("totalFail")%></div><div class="label">Failed</div></div>
  </div>
  <div class="func-title">Quick Access</div>
  <div class="func-btns">
    <a class="func-btn" href="02_student_home.html"><span class="icon">📚</span>Courses</a>
    <a class="func-btn" href="03_student_course_select.html"><span class="icon">✏️</span>Enroll</a>
    <a class="func-btn" href="04_student_grades.html"><span class="icon">📊</span>Grades</a>
    <a class="func-btn" href="05_student_profile.html"><span class="icon">👤</span>Profile</a>
    <a class="func-btn" href="01_student_login.html"><span class="icon">🔓</span>Logout</a>
  </div>
  <div class="section-title">My Course List</div>
  <div class="semester-block">
    <div class="semester-tag">Semester 1</div>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Course Name</th><th>Course No.</th><th>Instructor</th><th>Email</th><th>Schedule</th><th>Type</th><th>Credits</th><th>Assessment</th></tr></thead>
        <tbody>
          <tr><td>Advanced Mathematics</td><td>MATH001</td><td>Dr. Li Ming</td><td>liming@university.edu</td><td>Mon 1-2</td><td><span class="tag tag-comp">Required</span></td><td>4</td><td>Written Exam</td></tr>
          <tr><td>College English</td><td>ENG001</td><td>Ms. Wang Fang</td><td>wangfang@university.edu</td><td>Wed 3-4</td><td><span class="tag tag-comp">Required</span></td><td>3</td><td>Comprehensive</td></tr>
          <tr><td>Programming Fundamentals</td><td>CS001</td><td>Dr. Chen Zhiqiang</td><td>czq@university.edu</td><td>Fri 1-2</td><td><span class="tag tag-elect">Elective</span></td><td>3</td><td>Lab Exam</td></tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="semester-block">
    <div class="semester-tag">Semester 2</div>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Course Name</th><th>Course No.</th><th>Instructor</th><th>Email</th><th>Schedule</th><th>Type</th><th>Credits</th><th>Assessment</th></tr></thead>
        <tbody>
          <tr><td>Linear Algebra</td><td>MATH002</td><td>Dr. Zhao Jianguo</td><td>zhaojg@university.edu</td><td>Tue 5-6</td><td><span class="tag tag-comp">Required</span></td><td>3</td><td>Written Exam</td></tr>
          <tr><td>Data Structures</td><td>CS002</td><td>Dr. Chen Zhiqiang</td><td>czq@university.edu</td><td>Thu 1-2</td><td><span class="tag tag-elect">Elective</span></td><td>4</td><td>Lab + Written</td></tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
