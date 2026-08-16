package cn.zust.demo.dao;

import cn.zust.demo.utils.H2DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import cn.zust.demo.entity.Enrollment;

public class EnrollmentDao {

    public int getTotalCourseBySno(String sno) {
        if ("".equalsIgnoreCase(sno) || sno == null) {
            return 0;
        }
        try {
            int totalCourse = 0;
            String sql = "Select count(*) totalCourse from enrollment where sno=?";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCourse = rs.getInt("totalCourse");
            }
            rs.close();
            pstmt.close();
            connection.close();
            return totalCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }


    public int getTotalPassBySno(String sno) {
        if ("".equalsIgnoreCase(sno) || sno == null) {
            return 0;
        }
        try {
            int totalCourse = 0;
            String sql = "Select count(*) totalCourse from enrollment where sno=? and score >=60";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCourse = rs.getInt("totalCourse");
            }
            rs.close();
            pstmt.close();
            connection.close();
            return totalCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }


    public int getTotalNotPassBySno(String sno) {
        if ("".equalsIgnoreCase(sno) || sno == null) {
            return 0;
        }
        try {
            int totalCourse = 0;
            String sql = "Select count(*) totalCourse from enrollment where sno=? and score <60";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCourse = rs.getInt("totalCourse");
            }
            rs.close();
            pstmt.close();
            connection.close();
            return totalCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }

    public int getTotalNoMarkBySno(String sno) {
        if ("".equalsIgnoreCase(sno) || sno == null) {
            return 0;
        }
        try {
            int totalCourse = 0;
            String sql = "Select count(*) totalCourse from enrollment where sno=? and score is null";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCourse = rs.getInt("totalCourse");
            }
            rs.close();
            pstmt.close();
            connection.close();
            return totalCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }


    public boolean addNewEnrollment(Enrollment enrollment) {
        try {
            String sql = "INSERT INTO enrollment(courseno, sno, enrollmentTime, score, entryTime) " +
                    "VALUES (?, ?, ?, ?, ?)";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, enrollment.getCourseno());
            pstmt.setString(2, enrollment.getSno());
            pstmt.setString(3, enrollment.getEnrollmentTime());

            if (enrollment.getScore() != null) {
                pstmt.setDouble(4, enrollment.getScore());
            } else {
                pstmt.setNull(4, java.sql.Types.DECIMAL);
            }

            pstmt.setString(5, enrollment.getEntryTime());

            int result = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return result >= 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Enrollment> getAllEnrollments() {

        List<Enrollment> list = new ArrayList<>();

        try {

            String sql =
                    "SELECT * FROM enrollment";

            Connection conn = H2DbUtils.getConn();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Enrollment e = new Enrollment();

                e.setCourseno(
                        rs.getString("courseno"));

                e.setSno(
                        rs.getString("sno"));

                e.setEnrollmentTime(
                        rs.getString("enrollmentTime"));

                double scoreVal = rs.getDouble("score");
                e.setScore(rs.wasNull() ? null : scoreVal);

                e.setEntryTime(
                        rs.getString("entryTime"));

                list.add(e);
            }

            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Enrollment> searchEnrollments(
            String sno,
            String courseno,
            String semester) {

        List<Enrollment> list =
                new ArrayList<>();

        try {

            String sql =
                    "SELECT e.*, " +
                            "s.sname AS studentName, " +
                            "c.course_name AS courseName " +
                            "FROM enrollment e " +
                            "LEFT JOIN student s ON e.sno = s.sno " +
                            "LEFT JOIN course c ON e.courseno = c.course_no " +
                            "WHERE 1=1";


            if (sno != null && !sno.isEmpty())
                sql += " AND e.sno=?";

            if (courseno != null &&
                    !courseno.isEmpty())
                sql += " AND e.courseno=?";

            if (semester != null &&
                    !semester.isEmpty())
                sql += " AND e.enrollmentTime=?";

            Connection conn =
                    H2DbUtils.getConn();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            int i = 1;

            if (sno != null && !sno.isEmpty())
                pstmt.setString(i++, sno);

            if (courseno != null &&
                    !courseno.isEmpty())
                pstmt.setString(i++, courseno);

            if (semester != null &&
                    !semester.isEmpty())
                pstmt.setString(i++, semester);

            ResultSet rs =
                    pstmt.executeQuery();

            while (rs.next()) {

                Enrollment e =
                        new Enrollment();

                e.setStudentName(
                        rs.getString("studentName"));

                e.setCourseName(
                        rs.getString("courseName"));

                e.setSno(
                        rs.getString("sno"));

                e.setCourseno(
                        rs.getString("courseno"));

                e.setEnrollmentTime(
                        rs.getString("enrollmentTime"));

                double scoreVal = rs.getDouble("score");
                e.setScore(rs.wasNull() ? null : scoreVal);

                e.setEntryTime(
                        rs.getString("entryTime"));

                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateGrade(
            String sno,
            String courseno,
            Double score,
            String entryTime) {

        try {
            String sql =
                    "UPDATE enrollment " +
                            "SET score = ?, entryTime = ? " +
                            "WHERE sno = ? AND courseno = ?";

            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (score != null) {
                pstmt.setDouble(1, score);
            } else {
                pstmt.setNull(1, java.sql.Types.DECIMAL);
            }

            pstmt.setString(2, entryTime);
            pstmt.setString(3, sno);
            pstmt.setString(4, courseno);

            int result = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateEnrollment(
            String sno,
            String courseno,
            String semester,
            Double score) {

        try {
            String sql =
                    "UPDATE enrollment " +
                            "SET enrollmentTime = ?, score = ? " +
                            "WHERE sno = ? AND courseno = ?";

            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, semester);

            if (score != null) {
                pstmt.setDouble(2, score);
            } else {
                pstmt.setNull(2, java.sql.Types.DECIMAL);
            }

            pstmt.setString(3, sno);
            pstmt.setString(4, courseno);

            int result = pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean deleteEnrollment(
            String sno,
            String courseno) {

        try {

            String sql =
                    "DELETE FROM enrollment " +
                            "WHERE sno=? AND courseno=?";

            Connection conn =
                    H2DbUtils.getConn();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setString(1, sno);
            pstmt.setString(2, courseno);

            int result =
                    pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}







