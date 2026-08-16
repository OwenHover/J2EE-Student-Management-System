# J2EE-Student-Management-System
JSP/Servlet web app for managing students, courses, enrollments, and grades — built with DAO/MVC architecture on an H2 database.

Student Course Management System (J2EE)

A Java web application for managing student records, course enrollment, and grades — built with JSP, Servlets, and a DAO-based data access layer following the MVC architectural pattern.

Features
- Student, course, and enrollment management (add, view, update, delete)
- Grade tracking and management
- Admin user account management
- Clean separation between presentation (JSP), control logic (Servlets), and data access (DAO)

Tech Stack
- Backend: Java, Servlets
- Frontend: JSP (JavaServer Pages)
- Database: H2 (embedded relational database)
- Data Access: JDBC, DAO design pattern
- Architecture: MVC (Model-View-Controller)

Architecture:
This project follows the MVC pattern with a dedicated DAO layer for database access

How to Run (General Guide)

1. Clone the repository
2. Import the project into your IDE (Eclipse / IntelliJ IDEA) as a Dynamic Web Project
3. Confirm the H2 database connection settings in your DAO/config class
4. Deploy to a servlet container (e.g., Apache Tomcat if possible version 9)
5. Access the app at http://localhost:8080/<your-context-path>
Project Context

Built as part of a J2EE / Java Web Programming course at Zhejiang University of Science and Technology (ZUST). The base JSP templates were provided as part of the course; the Servlet controllers, DAO layer, and full integration and debugging were completed independently.