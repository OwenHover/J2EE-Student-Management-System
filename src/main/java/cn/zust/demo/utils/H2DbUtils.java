package cn.zust.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2DbUtils {
    public static final String JDBC_URL = "jdbc:h2:~/studentDB";
    public static final String USER = "osb";
    public static final String PASSWORD = "1234";
    public static final String DRIVER_CLASS = "org.h2.Driver";

    public static Connection getConn() {
        try {
            Class.forName(DRIVER_CLASS);
            Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initAdminUserTable() {
        try {
            String dropTable = "DROP TABLE IF EXISTS admin_user";
            String ddlUserAdmin = "CREATE TABLE admin_user(account varchar(20) unique not null, pwd varchar(50), name varchar(100))";
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(dropTable);
            int result = stmt.executeUpdate(ddlUserAdmin);

            if (result >= 0) {
                System.out.println("Create table admin_user OK");
            } else {
                System.out.println("Create table admin_user failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initEnrolTable() {
        try {
            String dropTable = "DROP TABLE IF EXISTS enrollment";
            String ddlUserAdmin = "CREATE TABLE enrollment (" +
                    "courseno varchar(20)," +
                    "sno varchar(100)," +
                    "enrollmentTime varchar(50)," +
                    "score DECIMAL(3,1)," +
                    "entryTime varchar(20)" +
                    ")";
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(dropTable);
            int result = stmt.executeUpdate(ddlUserAdmin);

            if (result >= 0) {
                System.out.println("Create table enrollment OK");
            } else {
                System.out.println("Create table enrollment failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initStudentTable() {
        try {
            String dropTable = "DROP TABLE IF EXISTS student";
            String ddlUserAdmin = "CREATE TABLE student (" +
                    "sno varchar(20) unique not null," +
                    "sname varchar(100)," +
                    "gender varchar(6)," +
                    "classname varchar(50)," +
                    "collegename varchar(50)," +
                    "country varchar(100)," +
                    "depart varchar(100)," +
                    "psw varchar(100)" +
                    ")";
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(dropTable);
            int result = stmt.executeUpdate(ddlUserAdmin);

            if (result >= 0) {
                System.out.println("Create table student OK");
            } else {
                System.out.println("Create table student failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllTables() {
        try {
            String sql = "SELECT table_name,table_type FROM information_schema.tables \n";
            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("table_name") + "=" + resultSet.getString("table_type"));
            }
            resultSet.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void initCourseTable() {
        try {
            String dropTable = "DROP TABLE IF EXISTS course";
            String sql = "CREATE TABLE course (" +
                    "course_no VARCHAR(20) PRIMARY KEY, " +
                    "course_name VARCHAR(100), " +
                    "credits INT, " +
                    "instructor VARCHAR(100), " +
                    "instructor_email VARCHAR(100), " +
                    "semester VARCHAR(50), " +
                    "class_time VARCHAR(100), " +
                    "location VARCHAR(100), " +
                    "remarks VARCHAR(255), " +
                    "other_info VARCHAR(255), " +
                    "course_type VARCHAR(50), " +        // NEW
                    "assessment VARCHAR(100), " +        // NEW
                    "enrollment_limit INT)";             // NEW

            Connection conn = getConn();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(dropTable);
            int result = stmt.executeUpdate(sql);
            if (result >= 0) {
                System.out.println("Create table course OK");
            } else {
                System.out.println("Create table course failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            H2DbUtils.initAdminUserTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            H2DbUtils.initCourseTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            H2DbUtils.initEnrolTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            H2DbUtils.initStudentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            H2DbUtils.getAllTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Connection conn = H2DbUtils.getConn();
            if (null != conn) {
                System.out.println(conn.getTransactionIsolation());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}