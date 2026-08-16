package cn.zust.demo.dao;

import cn.zust.demo.utils.H2DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDashboardDao {

    public int getTotalStudents() {
        try {
            int totalStudents = 0;
            String sql = "SELECT COUNT(*) totalStudents FROM student";

            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                totalStudents = rs.getInt("totalStudents");
            }

            rs.close();
            pstmt.close();
            connection.close();

            return totalStudents;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCourses() {
        try {
            int totalCourses = 0;
            String sql = "SELECT COUNT(*) totalCourses FROM course";

            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                totalCourses = rs.getInt("totalCourses");
            }

            rs.close();
            pstmt.close();
            connection.close();

            return totalCourses;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getEnrollmentRecords() {
        try {
            int totalEnrollments = 0;
            String sql = "SELECT COUNT(*) totalEnrollments FROM enrollment";

            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                totalEnrollments = rs.getInt("totalEnrollments");
            }

            rs.close();
            pstmt.close();
            connection.close();

            return totalEnrollments;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGradesEntered() {
        try {
            int totalGrades = 0;
            String sql = "SELECT COUNT(*) totalGrades FROM enrollment WHERE score IS NOT NULL";

            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                totalGrades = rs.getInt("totalGrades");
            }

            rs.close();
            pstmt.close();
            connection.close();

            return totalGrades;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}