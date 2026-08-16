<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zust.demo.dao.EnrollmentDao" %>
<%@ page import="cn.zust.demo.entity.Enrollment" %>
<%
  EnrollmentDao dao = new EnrollmentDao();

  String sno      = request.getParameter("sno");
  String courseno = request.getParameter("courseno");
  String semester = request.getParameter("semester");

  // Reuse searchEnrollments — it already JOINs student + course names
  List<Enrollment> grades = dao.searchEnrollments(sno, courseno, semester);
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Grade Management - Course Management System</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: "Segoe UI", Arial, sans-serif; background: #f0f2f5; min-height: 100vh; }
    .topbar { background: #1557b0; color: #fff; height: 50px; display: flex; align-items: center; padding: 0 24px; justify-content: space-between; }
    .topbar .sys-name { font-size: 16px; font-weight: 600; }
    .topbar .logout-btn { background: rgba(255,255,255,0.15); border: 1px solid rgba(255,255,255,0.3); color: #fff; padding: 5px 16px; border-radius: 4px; font-size: 13px; cursor: pointer; font-family: inherit; }
    .layout { display: flex; min-height: calc(100vh - 50px); }
    .sidebar { width: 200px; background: #fff; border-right: 1px solid #e8e8e8; padding: 16px 0; flex-shrink: 0; }
    .sidebar-title { font-size: 12px; color: #aaa; padding: 8px 20px 4px; text-transform: uppercase; letter-spacing: 1px; }
    .sidebar a { display: flex; align-items: center; gap: 10px; padding: 10px 20px; color: #555; text-decoration: none; font-size: 14px; }
    .sidebar a:hover { background: #f0f4ff; color: #1a73e8; }
    .sidebar a.active { background: #e8f0fe; color: #1a73e8; font-weight: 600; border-right: 3px solid #1a73e8; }
    .sidebar .icon { font-size: 16px; width: 20px; text-align: center; }
    .content { flex: 1; padding: 24px; }
    .page-title { font-size: 18px; color: #333; font-weight: 600; margin-bottom: 18px; }
    .search-bar { background: #fff; border-radius: 8px; padding: 14px 18px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); margin-bottom: 18px; display: flex; align-items: center; gap: 10px; flex-wrap: wrap; }
    .search-bar label { font-size: 14px; color: #555; white-space: nowrap; }
    .search-bar input { height: 34px; border: 1px solid #ddd; border-radius: 4px; padding: 0 10px; font-size: 14px; outline: none; font-family: inherit; width: 160px; }
    .search-bar input:focus { border-color: #1a73e8; }
    .search-bar select { height: 34px; border: 1px solid #ddd; border-radius: 4px; padding: 0 10px; font-size: 14px; outline: none; font-family: inherit; }
    .search-bar select:focus { border-color: #1a73e8; }
    .btn { height: 34px; padding: 0 16px; border-radius: 4px; font-size: 14px; cursor: pointer; font-family: inherit; border: none; }
    .btn:hover { opacity: 0.88; }
    .btn-primary { background: #1a73e8; color: #fff; }
    .btn-default { background: #fff; color: #555; border: 1px solid #ddd; }
    .btn-sm { height: 28px; padding: 0 12px; font-size: 12px; border-radius: 4px; cursor: pointer; font-family: inherit; border: none; }
    .top-msg { padding: 10px 16px; border-radius: 6px; font-size: 13px; margin-bottom: 14px; }
    .top-msg.success { background: #e8f5e9; color: #2e7d32; border: 1px solid #a5d6a7; }
    .table-wrap { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); overflow: hidden; }
    table { width: 100%; border-collapse: collapse; font-size: 14px; }
    thead tr { background: #f8f9fa; }
    th { padding: 11px 13px; text-align: left; color: #555; font-weight: 600; font-size: 13px; border-bottom: 1px solid #eee; }
    td { padding: 11px 13px; color: #333; border-bottom: 1px solid #f0f0f0; }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: #f8fbff; }
    .score-pending { color: #9e9e9e; font-size: 13px; }
    .score-val { font-weight: 600; }
    .score-pass { color: #2e7d32; }
    .score-fail { color: #c62828; }
    .btn-enter { background: #1a73e8; color: #fff; }
    .btn-modify { background: #f57c00; color: #fff; }
    .pagination { display: flex; justify-content: flex-end; align-items: center; gap: 6px; padding: 14px 16px; font-size: 13px; color: #666; }
    .page-btn { height: 30px; min-width: 30px; padding: 0 8px; border: 1px solid #ddd; background: #fff; border-radius: 4px; cursor: pointer; font-size: 13px; }
    .page-btn.active { background: #1a73e8; color: #fff; border-color: #1a73e8; }
    .modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; }
    .modal-overlay.show { display: flex; }
    .modal { background: #fff; border-radius: 8px; padding: 28px 32px; width: 380px; box-shadow: 0 4px 20px rgba(0,0,0,0.15); }
    .modal h3 { font-size: 16px; color: #333; margin-bottom: 16px; padding-bottom: 12px; border-bottom: 1px solid #eee; }
    .info-row { display: flex; margin-bottom: 10px; font-size: 14px; }
    .info-row .lbl { width: 90px; color: #888; flex-shrink: 0; }
    .info-row .val { color: #333; }
    .form-group { margin-top: 14px; }
    .form-group label { display: block; font-size: 13px; color: #555; margin-bottom: 6px; font-weight: 500; }
    .required { color: #e53935; }
    .form-group input { width: 100%; height: 38px; border: 1px solid #ddd; border-radius: 4px; padding: 0 10px; font-size: 14px; outline: none; font-family: inherit; }
    .form-group input:focus { border-color: #1a73e8; }
    .hint { font-size: 12px; color: #999; margin-top: 4px; }
    .modal-btns { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
    .btn-cancel { background: #f5f5f5; border: 1px solid #ddd; color: #555; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
    .btn-confirm { background: #1a73e8; border: none; color: #fff; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
  </style>
</head>
<body>
<div class="topbar">
  <%@include file="adminheader.jsp"%>
  <div class="sys-name">Course Management System | Admin Panel</div>
  <button class="logout-btn" onclick="location.href='login.jsp'">Logout</button>
</div>

<div class="layout">
  <div class="sidebar">
    <div class="sidebar-title">Management</div>
    <a href="home.jsp"><span class="icon">🏠</span>Dashboard</a>
    <a href="students.jsp"><span class="icon">👥</span>Students</a>
    <a href="courses.jsp"><span class="icon">📚</span>Courses</a>
    <a href="selections.jsp"><span class="icon">✏️</span>Enrollments</a>
    <a href="grades.jsp" class="active"><span class="icon">📊</span>Grades</a>
    <a href="users.jsp"><span class="icon">🔑</span>Users</a>
  </div>

  <div class="content">
    <div class="page-title">Grade Management</div>

    <!-- Search Bar -->
    <form method="get" action="grades.jsp">
      <div class="search-bar">
        <label>Student ID:</label>
        <input type="text" name="sno" placeholder="Exact match"
               value="<%= sno == null ? "" : sno %>">
        <label>Course No.:</label>
        <input type="text" name="courseno" placeholder="Exact match"
               value="<%= courseno == null ? "" : courseno %>">
        <label>Semester:</label>
        <select name="semester">
          <option value="">All</option>
          <option value="Semester 1" <%= "Semester 1".equals(semester) ? "selected" : "" %>>Semester 1</option>
          <option value="Semester 2" <%= "Semester 2".equals(semester) ? "selected" : "" %>>Semester 2</option>
          <option value="Semester 3" <%= "Semester 3".equals(semester) ? "selected" : "" %>>Semester 3</option>
          <option value="Semester 4" <%= "Semester 4".equals(semester) ? "selected" : "" %>>Semester 4</option>
        </select>
        <button class="btn btn-primary" type="submit">Search</button>
        <button class="btn btn-default" type="button"
                onclick="window.location='grades.jsp'">Reset</button>
      </div>
    </form>

    <div class="top-msg success" id="topMsg" style="display:none">Operation successful.</div>

    <div class="table-wrap">
      <table>
        <thead>
        <tr>
          <th>Student Name</th>
          <th>Student ID</th>
          <th>Course Name</th>
          <th>Course No.</th>
          <th>Semester</th>
          <th>Score</th>
          <th>Entry Time</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Enrollment e : grades) { %>
        <tr>
          <td><%= e.getStudentName() != null ? e.getStudentName() : "-" %></td>
          <td><%= e.getSno() %></td>
          <td><%= e.getCourseName() != null ? e.getCourseName() : "-" %></td>
          <td><%= e.getCourseno() %></td>
          <td><%= e.getEnrollmentTime() %></td>
          <td>
            <%
              Double score = e.getScore();
              // searchEnrollments uses rs.getDouble() which returns 0.0 for SQL NULL
              // We check entryTime as the reliable null indicator
              boolean hasScore = e.getEntryTime() != null && !e.getEntryTime().isEmpty() && score != null && score > 0;
            %>
            <% if (!hasScore) { %>
            <span class="score-pending">Not Entered</span>
            <% } else if (score >= 60) { %>
            <span class="score-val score-pass"><%= score % 1 == 0 ? String.valueOf(score.intValue()) : String.valueOf(score) %></span>
            <% } else { %>
            <span class="score-val score-fail"><%= score % 1 == 0 ? String.valueOf(score.intValue()) : String.valueOf(score) %></span>
            <% } %>
          </td>
          <td><%= (e.getEntryTime() != null && !e.getEntryTime().isEmpty()) ? e.getEntryTime() : "—" %></td>
          <td>
            <% if (!hasScore) { %>
            <button class="btn-sm btn-enter"
                    onclick="openGrade(
                            '<%= e.getSno() %>',
                            '<%= e.getCourseno() %>',
                            '<%= e.getStudentName() != null ? e.getStudentName() : e.getSno() %>',
                            '<%= e.getCourseName() != null ? e.getCourseName() : e.getCourseno() %>',
                            '<%= e.getEnrollmentTime() %>',
                            ''
                            )">Enter Grade</button>
            <% } else { %>
            <button class="btn-sm btn-modify"
                    onclick="openGrade(
                            '<%= e.getSno() %>',
                            '<%= e.getCourseno() %>',
                            '<%= e.getStudentName() != null ? e.getStudentName() : e.getSno() %>',
                            '<%= e.getCourseName() != null ? e.getCourseName() : e.getCourseno() %>',
                            '<%= e.getEnrollmentTime() %>',
                            '<%= score % 1 == 0 ? String.valueOf(score.intValue()) : String.valueOf(score) %>'
                            )">Edit Grade</button>
            <% } %>
          </td>
        </tr>
        <% } %>
        </tbody>
      </table>
      <div class="pagination">
        <%= grades.size() %> record(s) found
      </div>
    </div>
  </div>
</div>

<!-- Enter / Edit Grade Modal -->
<div class="modal-overlay" id="gradeModal">
  <div class="modal">
    <h3 id="gradeTitle">Enter Grade</h3>
    <form id="gradeForm" action="${pageContext.request.contextPath}/updateGrade" method="post">
      <!-- Hidden fields passed to servlet -->
      <input type="hidden" id="g_sno"      name="sno">
      <input type="hidden" id="g_courseno" name="courseno">
      <input type="hidden" id="g_semester" name="semester">

      <div class="info-row">
        <div class="lbl">Student:</div>
        <div class="val" id="g_name"></div>
      </div>
      <div class="info-row">
        <div class="lbl">Course:</div>
        <div class="val" id="g_course"></div>
      </div>

      <div class="form-group">
        <label>Score <span class="required">*</span></label>
        <input type="number" id="g_score" name="score"
               min="0" max="100" step="1"
               placeholder="Enter integer 0–100">
        <div class="hint">Score must be an integer between 0 and 100.</div>
      </div>

      <div class="modal-btns">
        <button type="button" class="btn-cancel" onclick="closeModal()">Cancel</button>
        <button type="button" class="btn-confirm" onclick="submitGrade()">Save</button>
      </div>
    </form>
  </div>
</div>

<script>
  function openGrade(sno, courseno, studentName, courseName, semester, score) {
    const isEnter = (score === '');
    document.getElementById('gradeTitle').textContent = isEnter ? 'Enter Grade' : 'Edit Grade';
    document.getElementById('g_name').textContent    = studentName;
    document.getElementById('g_course').textContent  = courseName;
    document.getElementById('g_sno').value           = sno;
    document.getElementById('g_courseno').value      = courseno;
    document.getElementById('g_semester').value      = semester;
    document.getElementById('g_score').value         = score;
    document.getElementById('gradeModal').classList.add('show');
  }

  function closeModal() {
    document.getElementById('gradeModal').classList.remove('show');
  }

  function submitGrade() {
    const raw = document.getElementById('g_score').value;
    const v   = parseInt(raw);
    if (raw === '' || isNaN(v) || v < 0 || v > 100) {
      alert('Score must be an integer between 0 and 100.');
      return;
    }
    document.getElementById('gradeForm').submit();
  }
</script>
</body>
</html>
