package cn.zust.demo.dao;

import cn.zust.demo.entity.Course;
import cn.zust.demo.utils.H2DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    // insert
    public boolean insert(Course course) {

        String sql =
                "INSERT INTO course " +
                        "(course_no, course_name, credits, instructor, instructor_email, semester, class_time, location, remarks, other_info, course_type, assessment, enrollment_limit) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseNo());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getCredits());
            ps.setString(4, course.getInstructor());
            ps.setString(5, course.getInstructorEmail());
            ps.setString(6, course.getSemester());
            ps.setString(7, course.getClassTime());
            ps.setString(8, course.getLocation());
            ps.setString(9, course.getRemarks());
            ps.setString(10, course.getOtherInfo());
            ps.setString(11, course.getCourseType());
            ps.setString(12, course.getAssessment());
            ps.setInt(13, course.getEnrollmentLimit());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // getcoursebycourseno
    public Course getCourseByCourseNo(String courseNo) {

        String sql = "SELECT * FROM course WHERE course_no = ?";
        Course course = null;

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course = new Course();

                course.setCourseNo(rs.getString("course_no"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                course.setInstructor(rs.getString("instructor"));
                course.setInstructorEmail(rs.getString("instructor_email"));
                course.setSemester(rs.getString("semester"));
                course.setClassTime(rs.getString("class_time"));
                course.setLocation(rs.getString("location"));
                course.setRemarks(rs.getString("remarks"));
                course.setOtherInfo(rs.getString("other_info"));
                course.setCourseType(rs.getString("course_type"));
                course.setAssessment(rs.getString("assessment"));
                course.setEnrollmentLimit(rs.getInt("enrollment_limit"));
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }

    // delete
    public boolean deleteCourse(String courseNo) {

        String sql = "DELETE FROM course WHERE course_no = ?";

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseNo);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // update
    public boolean updateCourse(Course course) {

        String sql = "UPDATE course SET " +
                "course_name = ?, " +
                "credits = ?, " +
                "instructor = ?, " +
                "instructor_email = ?, " +
                "semester = ?, " +
                "class_time = ?, " +
                "remarks = ?, " +
                "course_type = ?, " +
                "assessment = ?, " +
                "enrollment_limit = ? " +
                "WHERE course_no = ?";

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getCredits());
            ps.setString(3, course.getInstructor());
            ps.setString(4, course.getInstructorEmail());
            ps.setString(5, course.getSemester());
            ps.setString(6, course.getClassTime());
            ps.setString(7, course.getRemarks());
            ps.setString(8, course.getCourseType());
            ps.setString(9, course.getAssessment());
            ps.setInt(10, course.getEnrollmentLimit());
            ps.setString(11, course.getCourseNo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // get all
    public List<Course> getAllCourses() {

        List<Course> list = new ArrayList<>();

        String sql = "SELECT * FROM course";

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Course course = new Course();

                course.setCourseNo(rs.getString("course_no"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                course.setInstructor(rs.getString("instructor"));
                course.setInstructorEmail(rs.getString("instructor_email"));
                course.setSemester(rs.getString("semester"));
                course.setClassTime(rs.getString("class_time"));
                course.setCourseType(rs.getString("course_type"));
                course.setAssessment(rs.getString("assessment"));
                course.setEnrollmentLimit(rs.getInt("enrollment_limit"));
                course.setLocation(rs.getString("location"));
                course.setRemarks(rs.getString("remarks"));
                course.setOtherInfo(rs.getString("other_info"));

                list.add(course);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int getEnrolledCount(String courseNo) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE courseno = ?";
        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, courseNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // search
    public List<Course> searchByCourseNo(String keyword) {

        List<Course> list = new ArrayList<>();

        String sql = "SELECT * FROM course WHERE course_no LIKE ?";

        try (Connection conn = H2DbUtils.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Course course = new Course();

                course.setCourseNo(rs.getString("course_no"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                course.setInstructor(rs.getString("instructor"));
                course.setInstructorEmail(rs.getString("instructor_email"));
                course.setSemester(rs.getString("semester"));
                course.setCourseType(rs.getString("course_type"));
                course.setAssessment(rs.getString("assessment"));
                course.setEnrollmentLimit(rs.getInt("enrollment_limit"));
                course.setClassTime(rs.getString("class_time"));
                course.setLocation(rs.getString("location"));
                course.setRemarks(rs.getString("remarks"));
                course.setOtherInfo(rs.getString("other_info"));

                list.add(course);
            }

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}