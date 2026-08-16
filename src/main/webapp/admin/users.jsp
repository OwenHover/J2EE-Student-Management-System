<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zust.demo.dao.UserDao" %>
<%@ page import="cn.zust.demo.dao.StudentDao" %>
<%@ page import="cn.zust.demo.entity.User" %>
<%@ page import="cn.zust.demo.entity.Student" %>
<%
  UserDao    userDao    = new UserDao();
  StudentDao studentDao = new StudentDao();

  List<User>    admins   = userDao.getAllAdmins();
  List<Student> students = studentDao.getAllStudents();

  // Read filter param for tab highlighting
  String roleFilter = request.getParameter("role");
  if (roleFilter == null) roleFilter = "all";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Management - Course Management System</title>
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
    .action-bar { display: flex; align-items: center; gap: 10px; margin-bottom: 16px; flex-wrap: wrap; }
    .btn { height: 36px; padding: 0 18px; border-radius: 4px; font-size: 14px; cursor: pointer; font-family: inherit; border: none; }
    .btn:hover { opacity: 0.85; }
    .btn-primary { background: #1a73e8; color: #fff; }
    .btn-default { background: #fff; color: #555; border: 1px solid #ddd; }
    .btn-danger { background: #e53935; color: #fff; }
    .btn-sm { height: 30px; padding: 0 12px; font-size: 13px; }
    .filter-tab { display: flex; gap: 0; border: 1px solid #ddd; border-radius: 4px; overflow: hidden; }
    .filter-tab button { height: 36px; padding: 0 18px; font-size: 14px; cursor: pointer; font-family: inherit; border: none; background: #fff; color: #555; }
    .filter-tab button.active { background: #1a73e8; color: #fff; }
    .filter-tab button:not(:last-child) { border-right: 1px solid #ddd; }
    .top-msg { padding: 10px 16px; border-radius: 6px; font-size: 13px; margin-bottom: 14px; }
    .top-msg.success { background: #e8f5e9; color: #2e7d32; border: 1px solid #a5d6a7; }
    .table-wrap { background: #fff; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); overflow: hidden; }
    table { width: 100%; border-collapse: collapse; font-size: 14px; }
    thead tr { background: #f8f9fa; }
    th { padding: 11px 14px; text-align: left; color: #555; font-weight: 600; font-size: 13px; border-bottom: 1px solid #eee; }
    td { padding: 11px 14px; color: #333; border-bottom: 1px solid #f0f0f0; }
    tr:last-child td { border-bottom: none; }
    tr:hover td { background: #f8fbff; }
    .ops { display: flex; gap: 6px; }
    .role-tag { display: inline-block; padding: 2px 10px; border-radius: 10px; font-size: 12px; font-weight: 500; }
    .role-student { background: #e8f5e9; color: #2e7d32; }
    .role-admin { background: #e8f0fe; color: #1557b0; }
    .modal-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.4); z-index: 100; align-items: center; justify-content: center; }
    .modal-overlay.show { display: flex; }
    .modal { background: #fff; border-radius: 8px; padding: 28px 32px; width: 440px; box-shadow: 0 4px 20px rgba(0,0,0,0.15); }
    .modal h3 { font-size: 16px; color: #333; margin-bottom: 18px; padding-bottom: 12px; border-bottom: 1px solid #eee; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; font-size: 13px; color: #555; margin-bottom: 5px; font-weight: 500; }
    .required { color: #e53935; }
    .form-group input, .form-group select { width: 100%; height: 36px; border: 1px solid #ddd; border-radius: 4px; padding: 0 10px; font-size: 14px; outline: none; font-family: inherit; }
    .form-group input:focus, .form-group select:focus { border-color: #1a73e8; }
    .readonly-field { background: #f5f5f5; color: #888; }
    .modal-btns { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
    .btn-cancel { background: #f5f5f5; border: 1px solid #ddd; color: #555; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
    .btn-confirm { background: #1a73e8; border: none; color: #fff; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
    .btn-del-confirm { background: #e53935; border: none; color: #fff; padding: 7px 20px; border-radius: 4px; font-size: 14px; cursor: pointer; }
    .del-warning { background: #fff8e1; border: 1px solid #ffe082; border-radius: 6px; padding: 10px 14px; font-size: 13px; color: #795548; margin-bottom: 10px; line-height: 1.6; }
    .pagination { display: flex; justify-content: flex-end; align-items: center; gap: 6px; padding: 14px 16px; font-size: 13px; color: #666; }
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
    <a href="grades.jsp"><span class="icon">📊</span>Grades</a>
    <a href="users.jsp" class="active"><span class="icon">🔑</span>Users</a>
  </div>

  <div class="content">
    <div class="page-title">User Management</div>

    <div class="action-bar">
      <button class="btn btn-primary" onclick="openAdd()">+ Add User</button>
      <div class="filter-tab">
        <button id="tab-all"     class="active"  onclick="filterRole('all')">All</button>
        <button id="tab-student"                 onclick="filterRole('student')">Students</button>
        <button id="tab-admin"                   onclick="filterRole('admin')">Admins</button>
      </div>
    </div>

    <div class="top-msg success" id="topMsg" style="display:none">Operation successful.</div>

    <div class="table-wrap">
      <table>
        <thead>
        <tr>
          <th>Username / Student ID</th>
          <th>Full Name</th>
          <th>Role</th>
          <th>Password</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody id="userTableBody">

        <!-- Admin rows -->
        <% for (User u : admins) { %>
        <tr class="row-admin">
          <td><%= u.getAccount() %></td>
          <td><%= u.getName() != null ? u.getName() : "-" %></td>
          <td><span class="role-tag role-admin">Administrator</span></td>
          <td>••••••</td>
          <td>
            <div class="ops">
              <button class="btn btn-default btn-sm"
                      onclick="openEdit('<%= u.getAccount() %>','<%= u.getName() != null ? u.getName() : "" %>','admin')">
                Edit
              </button>
              <button class="btn btn-danger btn-sm"
                      onclick="openDel('<%= u.getAccount() %>','<%= u.getName() != null ? u.getName() : u.getAccount() %>','admin')">
                Delete
              </button>
            </div>
          </td>
        </tr>
        <% } %>

        <!-- Student rows -->
        <% for (Student s : students) { %>
        <tr class="row-student">
          <td><%= s.getSno() %></td>
          <td><%= s.getSname() != null ? s.getSname() : "-" %></td>
          <td><span class="role-tag role-student">Student</span></td>
          <td>••••••</td>
          <td>
            <div class="ops">
              <button class="btn btn-default btn-sm"
                      onclick="openEdit('<%= s.getSno() %>','<%= s.getSname() != null ? s.getSname() : "" %>','student')">
                Edit
              </button>
              <button class="btn btn-danger btn-sm"
                      onclick="openDel('<%= s.getSno() %>','<%= s.getSname() != null ? s.getSname() : s.getSno() %>','student')">
                Delete
              </button>
            </div>
          </td>
        </tr>
        <% } %>

        </tbody>
      </table>
      <div class="pagination" id="recordCount">
        <%= admins.size() + students.size() %> record(s)
      </div>
    </div>
  </div>
</div>

<!-- ── Add User Modal ───────────────────────────────────────── -->
<div class="modal-overlay" id="addModal">
  <div class="modal">
    <h3>Add User</h3>
    <form id="addForm" action="${pageContext.request.contextPath}/addUser" method="post">

      <div class="form-group">
        <label>Role <span class="required">*</span></label>
        <select id="a_role" name="role" onchange="toggleAddFields()">
          <option value="student">Student</option>
          <option value="admin">Administrator</option>
        </select>
      </div>

      <!-- Student-only fields -->
      <div id="studentFields">
        <div class="form-group">
          <label>Student ID <span class="required">*</span></label>
          <input type="text" id="a_sno" name="sno" placeholder="8–15 characters">
        </div>
        <div class="form-group">
          <label>Full Name <span class="required">*</span></label>
          <input type="text" id="a_name_student" name="sname" placeholder="2–20 characters">
        </div>
      </div>

      <!-- Admin-only fields -->
      <div id="adminFields" style="display:none">
        <div class="form-group">
          <label>Admin Account <span class="required">*</span></label>
          <input type="text" id="a_account" name="account" placeholder="Must be unique">
        </div>
        <div class="form-group">
          <label>Full Name</label>
          <input type="text" id="a_name_admin" name="adminName" placeholder="Display name">
        </div>
      </div>

      <div class="form-group">
        <label>Password <span class="required">*</span></label>
        <input type="password" id="a_pwd" name="pwd"
               placeholder="Student default: 123456 | Admin default: admin">
      </div>

      <div class="modal-btns">
        <button type="button" class="btn-cancel" onclick="closeModal('addModal')">Cancel</button>
        <button type="button" class="btn-confirm" onclick="submitAdd()">Add User</button>
      </div>
    </form>
  </div>
</div>

<!-- ── Edit User Modal ──────────────────────────────────────── -->
<div class="modal-overlay" id="editModal">
  <div class="modal">
    <h3>Edit User</h3>
    <form id="editForm" action="${pageContext.request.contextPath}/editUser" method="post">

      <input type="hidden" id="e_account" name="account">
      <input type="hidden" id="e_roleHidden" name="role">

      <div class="form-group">
        <label>Username / Student ID</label>
        <input type="text" id="e_username_display" class="readonly-field" readonly>
      </div>

      <div class="form-group">
        <label>Role <span class="required">*</span></label>
        <select id="e_role_display" disabled>
          <option value="student">Student</option>
          <option value="admin">Administrator</option>
        </select>
      </div>

      <div class="form-group">
        <label>New Password <span class="required">*</span></label>
        <input type="password" id="e_pwd" name="pwd" placeholder="6–10 characters">
      </div>

      <div class="modal-btns">
        <button type="button" class="btn-cancel" onclick="closeModal('editModal')">Cancel</button>
        <button type="button" class="btn-confirm" onclick="submitEdit()">Save</button>
      </div>
    </form>
  </div>
</div>

<!-- ── Delete Confirm Modal ─────────────────────────────────── -->
<div class="modal-overlay" id="delModal">
  <div class="modal">
    <h3>Confirm Delete</h3>
    <div class="del-warning" id="delWarning"></div>
    <div class="modal-btns">
      <button class="btn-cancel" onclick="closeModal('delModal')">Cancel</button>
      <button class="btn-del-confirm" onclick="confirmDel()">Delete</button>
    </div>
  </div>
</div>

<script>
  // ── Filter tabs ───────────────────────────────────────────────
  function filterRole(role) {
    document.querySelectorAll('.filter-tab button').forEach(b => b.classList.remove('active'));
    document.getElementById('tab-' + role).classList.add('active');

    const adminRows   = document.querySelectorAll('.row-admin');
    const studentRows = document.querySelectorAll('.row-student');

    adminRows.forEach(r   => r.style.display = (role === 'all' || role === 'admin')   ? '' : 'none');
    studentRows.forEach(r => r.style.display = (role === 'all' || role === 'student') ? '' : 'none');

    // Update count
    let count = 0;
    document.querySelectorAll('#userTableBody tr').forEach(r => { if (r.style.display !== 'none') count++; });
    document.getElementById('recordCount').textContent = count + ' record(s)';
  }

  // ── Add modal ─────────────────────────────────────────────────
  function openAdd() {
    document.getElementById('a_role').value = 'student';
    document.getElementById('a_sno').value = '';
    document.getElementById('a_name_student').value = '';
    document.getElementById('a_account').value = '';
    document.getElementById('a_name_admin').value = '';
    document.getElementById('a_pwd').value = '';
    toggleAddFields();
    document.getElementById('addModal').classList.add('show');
  }

  function toggleAddFields() {
    const role = document.getElementById('a_role').value;
    document.getElementById('studentFields').style.display = role === 'student' ? 'block' : 'none';
    document.getElementById('adminFields').style.display   = role === 'admin'   ? 'block' : 'none';
  }

  function submitAdd() {
    const role = document.getElementById('a_role').value;
    const pwd  = document.getElementById('a_pwd').value.trim();

    if (role === 'student') {
      const sno   = document.getElementById('a_sno').value.trim();
      const sname = document.getElementById('a_name_student').value.trim();
      if (!sno)   { alert('Student ID is required.'); return; }
      if (!sname) { alert('Full Name is required.');  return; }
    } else {
      const acc = document.getElementById('a_account').value.trim();
      if (!acc) { alert('Admin Account is required.'); return; }
    }
    if (!pwd) { alert('Password is required.'); return; }

    document.getElementById('addForm').submit();
  }

  // ── Edit modal ────────────────────────────────────────────────
  function openEdit(account, name, role) {
    document.getElementById('e_account').value        = account;
    document.getElementById('e_roleHidden').value     = role;
    document.getElementById('e_username_display').value = account;
    document.getElementById('e_role_display').value   = role;
    document.getElementById('e_pwd').value            = '';
    document.getElementById('editModal').classList.add('show');
  }

  function submitEdit() {
    const pwd = document.getElementById('e_pwd').value.trim();
    if (!pwd) { alert('New password is required.'); return; }
    if (pwd.length < 6 || pwd.length > 10) { alert('Password must be 6–10 characters.'); return; }
    document.getElementById('editForm').submit();
  }

  // ── Delete modal ──────────────────────────────────────────────
  let deleteUrl = '';
  function openDel(account, name, role) {
    deleteUrl = '${pageContext.request.contextPath}/deleteUser?account=' + encodeURIComponent(account) + '&role=' + role;
    const isAdmin = role === 'admin';
    document.getElementById('delWarning').innerHTML = isAdmin
            ? 'Delete administrator <strong>' + name + '</strong>? All operation records will be permanently deleted and cannot be recovered!'
            : 'Delete student <strong>' + name + '</strong> (' + account + ')? All enrollment and grade records will be permanently deleted!';
    document.getElementById('delModal').classList.add('show');
  }

  function confirmDel() {
    window.location = deleteUrl;
  }

  function closeModal(id) {
    document.getElementById(id).classList.remove('show');
  }
</script>
</body>
</html>
