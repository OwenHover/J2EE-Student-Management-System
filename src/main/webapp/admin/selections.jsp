<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zust.demo.dao.EnrollmentDao" %>
<%@ page import="cn.zust.demo.entity.Enrollment" %>
<%
  EnrollmentDao dao =
          new EnrollmentDao();

  String sno = request.getParameter("sno");

  String courseno = request.getParameter("courseno");

  String semester = request.getParameter("semester");

  List<Enrollment> enrollments =
          dao.searchEnrollments(sno, courseno, semester);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Enrollment Management - Course Management System</title>
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
  .notice { background: #fff8e1; border: 1px solid #ffe082; border-radius: 6px; padding: 10px 16px; font-size: 13px; color: #795548; margin-bottom: 16px; }
  .search-bar { background: #fff; border-radius: 8px; padding: 14px 18px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); margin-bottom: 18px; display: flex; align-items: center; gap: 10px; flex-wrap: wrap; }
  .search-bar label { font-size: 14px; color: #555; white-space: nowrap; }
  .search-bar input, .search-bar select { height: 34px; border: 1px solid #ddd; border-radius: 4px; padding: 0 10px; font-size: 14px; outline: none; font-family: inherit; width: 160px; }
  .search-bar input:focus, .search-bar select:focus { border-color: #1a73e8; }
  .btn { height: 34px; padding: 0 16px; border-radius: 4px; font-size: 14px; cursor: pointer; font-family: inherit; border: none; }
  .btn-primary { background: #1a73e8; color: #fff; }
  .btn-default { background: #fff; color: #555; border: 1px solid #ddd; }
  .btn-danger { background: #e53935; color: #fff; }
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
  .pagination { display: flex; justify-content: flex-end; align-items: center; gap: 6px; padding: 14px 16px; font-size: 13px; color: #666; }
  .page-btn { height: 30px; min-width: 30px; padding: 0 8px; border: 1px solid #ddd; background: #fff; border-radius: 4px; cursor: pointer; font-size: 13px; }
  .page-btn.active { background: #1a73e8; color: #fff; border-color: #1a73e8; }
  .modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; }
  .modal-overlay.show { display: flex; }
  .modal { background: #fff; border-radius: 8px; padding: 28px 32px; width: 420px; box-shadow: 0 4px 20px rgba(0,0,0,0.15); }
  .modal h3 { font-size: 16px; color: #333; margin-bottom: 14px; }
  .del-warning { background: #fff8e1; border: 1px solid #ffe082; border-radius: 6px; padding: 10px 14px; font-size: 13px; color: #795548; margin-bottom: 10px; line-height: 1.6; }
  .modal-btns { display: flex; justify-content: flex-end; gap: 10px; margin-top: 16px; }
  .btn-cancel { background: #f5f5f5; border: 1px solid #ddd; color: #555; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
  .btn-del-confirm { background: #e53935; border: none; color: #fff; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
</style>
</head>
<body>
<div class="topbar">
  <%@include file="adminheader.jsp"%>
  <div class="sys-name">Course Management System | Admin Panel</div>
  <button class="logout-btn" onclick="location.href='06_admin_login.html'">Logout</button>
</div>
<div class="layout">
  <div class="sidebar">
    <div class="sidebar-title">Management</div>
    <a href="home.jsp"><span class="icon">🏠</span>Dashboard</a>
    <a href="students.jsp"><span class="icon">👥</span>Students</a>
    <a href="courses.jsp" ><span class="icon">📚</span>Courses</a>
    <a href="selections.jsp" class="active"><span class="icon">✏️</span>Enrollments</a>
    <a href="grades.jsp"><span class="icon">📊</span>Grades</a>
    <a href="users.jsp"><span class="icon">🔑</span>Users</a>
  </div>
  <div class="content">
    <div class="page-title">Enrollment Management</div>
    <div class="notice">
      Note: Enrollment records are created by students. Administrators may only delete invalid records (e.g. withdrawn students or cancelled courses). Adding or editing records is not permitted.
    </div>
    <form method="get" action="selections.jsp">

      <div class="search-bar">

        <label>Student ID:</label>

        <input
                type="text"
                name="sno"
                value="<%=request.getParameter("sno")==null?"":request.getParameter("sno")%>"
                placeholder="Exact match">

        <label>Course No.:</label>

        <input
                type="text"
                name="courseno"
                value="<%=request.getParameter("courseno")==null?"":request.getParameter("courseno")%>"
                placeholder="Exact match">

        <label>Semester:</label>

        <select name="semester">

          <option value="">All</option>

          <option value="Semester 1">Semester 1</option>

          <option value="Semester 2">Semester 2</option>

          <option value="Semester 3">Semester 3</option>

        </select>

        <button type="submit"
                class="btn btn-primary">
          Search
        </button>

        <button type="button"
                class="btn btn-default"
                onclick="window.location='selections.jsp'">
          Reset
        </button>
      </div>
    </form>

    <div class="top-msg success" id="topMsg" style="display:none">Enrollment record deleted successfully.</div>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Student Name</th><th>Student ID</th><th>Course Name</th><th>Course No.</th><th>Semester</th><th>Enrollment Time</th><th>Actions</th></tr></thead>
        <tbody>


        <% for(Enrollment e : enrollments){ %>

        <tr>

          <td><%= e.getStudentName() %></td>

          <td><%= e.getSno() %></td>

          <td><%= e.getCourseName() %></td>

          <td><%= e.getCourseno() %></td>

          <td><%= e.getEnrollmentTime() %></td>

          <td><%= e.getEntryTime() %></td>

          <td>

            <button
                    class="btn-sm btn-danger"

                    onclick="openDel(
                            '<%= e.getSno() %>',
                            '<%= e.getCourseno() %>'
                            )">

              Delete

            </button>

          </td>

        </tr>

        <% } %>

        </tbody>
        </tbody>
      </table>
      <div class="pagination">1,243 records &nbsp;<button class="page-btn active">1</button><button class="page-btn">2</button><button class="page-btn">3</button><span>...</span></div>
    </div>
  </div>
</div>
<div class="modal-overlay" id="delModal">
  <div class="modal">
    <h3>Confirm Delete</h3>
    <div class="del-warning">Delete this enrollment record? The corresponding grade record will also be permanently deleted and cannot be recovered!</div>
    <div class="modal-btns">
      <button class="btn-cancel" onclick="closeModal()">Cancel</button>
      <button class="btn-del-confirm" onclick="confirmDel()">Delete</button>
    </div>
  </div>
</div>
<script>
  let deleteUrl="";
  function openDel(sno,courseno){deleteUrl = '${pageContext.request.contextPath}' + '/deleteEnrollment?sno=' + sno + '&courseno=' + courseno;document.getElementById('delModal').classList.add('show');}
  function closeModal(){document.getElementById('delModal').classList.remove('show');}
  function confirmDel(){window.location = deleteUrl;}
</script>
</body>
</html>
