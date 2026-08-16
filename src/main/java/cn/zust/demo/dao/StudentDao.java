package cn.zust.demo.dao;

import cn.zust.demo.entity.Course;
import cn.zust.demo.entity.Enrollment;
import cn.zust.demo.entity.Student;
import cn.zust.demo.utils.H2DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class StudentDao {

    public boolean addStudent(Student student) {
        try {
            String sql = "INSERT INTO STUDENT(sno,sname,gender,className,collegeName,country,depart,psw) VALUES (?,?,?,?,?,?,?,?)";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, student.getSno());
            pstmt.setString(2, student.getSname());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getClassname());
            pstmt.setString(5, student.getCollegename());
            pstmt.setString(6, student.getCountry());
            pstmt.setString(7, student.getDepart());
            pstmt.setString(8, student.getPsw());
            int result = pstmt.executeUpdate();
            pstmt.close();
            connection.close();
            return result >= 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudnetBySnoAndPsw(String sno, String psw) {
        Student student = null;
        try {
            if ("".equalsIgnoreCase(sno) || "".equalsIgnoreCase(psw)) {
                return null;
            }
            String sql = "SELECT sno,sname,gender,className,collegeName,country,depart,psw FROM student WHERE sno=? AND psw=?";
            Connection connection = H2DbUtils.getConn();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sno);
            pstmt.setString(2, psw);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                student = new Student();
                student.setSno(rs.getString("sno"));
                student.setSname(rs.getString("sname"));
                student.setGender(rs.getString("gender"));
                student.setClassname(rs.getString("className"));
                student.setCollegename(rs.getString("collegeName"));
                student.setCountry(rs.getString("country"));
                student.setDepart(rs.getString("depart"));
                student.setPsw(rs.getString("psw"));
            }
            rs.close();
            pstmt.close();
            connection.close();
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try {
            String sql = "SELECT sno,sname,gender,className,collegeName,country,depart,psw FROM student";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setGender(rs.getString("gender"));
                s.setClassname(rs.getString("className"));
                s.setCollegename(rs.getString("collegeName"));
                s.setCountry(rs.getString("country"));
                s.setDepart(rs.getString("depart"));
                s.setPsw(rs.getString("psw"));
                list.add(s);
            }
            rs.close(); pstmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean deleteStudent(String sno) {
        try {
            String sql = "DELETE FROM student WHERE sno=?";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            int result = pstmt.executeUpdate();
            pstmt.close(); conn.close();
            return result > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateStudent(Student student) {
        try {
            String sql = "UPDATE student SET sname=?,gender=?,className=?,collegeName=?,country=?,depart=?,psw=? WHERE sno=?";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getSname());
            pstmt.setString(2, student.getGender());
            pstmt.setString(3, student.getClassname());
            pstmt.setString(4, student.getCollegename());
            pstmt.setString(5, student.getCountry());
            pstmt.setString(6, student.getDepart());
            pstmt.setString(7, student.getPsw());
            pstmt.setString(8, student.getSno());
            int result = pstmt.executeUpdate();
            pstmt.close(); conn.close();
            return result > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    public Student getStudentBySno(String sno) {
        try {
            String sql = "SELECT sno,sname,gender,className,collegeName,country,depart,psw FROM student WHERE sno=?";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setGender(rs.getString("gender"));
                s.setClassname(rs.getString("className"));
                s.setCollegename(rs.getString("collegeName"));
                s.setCountry(rs.getString("country"));
                s.setDepart(rs.getString("depart"));
                s.setPsw(rs.getString("psw"));
                rs.close(); pstmt.close(); conn.close();
                return s;
            }
            rs.close(); pstmt.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> searchBySno(String sno) {
        List<Student> list = new ArrayList<>();
        try {
            String sql = "SELECT sno,sname,gender,className,collegeName,country,depart,psw FROM student WHERE sno LIKE ?";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + sno + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setGender(rs.getString("gender"));
                s.setClassname(rs.getString("className"));
                s.setCollegename(rs.getString("collegeName"));
                s.setCountry(rs.getString("country"));
                s.setDepart(rs.getString("depart"));
                s.setPsw(rs.getString("psw"));
                list.add(s);
            }
            rs.close(); pstmt.close(); conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ─── SEED DATA ────────────────────────────────────────────────────────────
    // Run this main() once to populate students, courses, and enrollments.
    // The student IDs and course numbers here match the enrollment test records
    // already inserted by EnrollmentDao.main(), so names will no longer be null.
    // ──────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        StudentDao studentDao   = new StudentDao();
        CourseDao  courseDao    = new CourseDao();
        EnrollmentDao enrollDao = new EnrollmentDao();

        // ── Students ──────────────────────────────────────────────────────────
        // These three match the enrollment records already in the DB
        Object[][] students = {
                // sno,         sname,            gender,  class,         college,                    country,  depart,                    psw
                {"20210001", "Owen Osborn",      "Male",  "CS-2021-A",   "School of Computing",      "USA",    "Computer Science",        "123456"},
                {"20210002", "Mia Chen",         "Female","CS-2021-B",   "School of Computing",      "China",  "Computer Science",        "123456"},
                {"20210003", "Lucas Martin",     "Male",  "SE-2021-A",   "School of Engineering",    "France", "Software Engineering",    "123456"},
                {"20210004", "Aisha Patel",      "Female","SE-2021-B",   "School of Engineering",    "India",  "Software Engineering",    "123456"},
                {"20210005", "James Kim",        "Male",  "IT-2021-A",   "School of Computing",      "Korea",  "Information Technology",  "123456"},
                {"20210006", "Sofía García",     "Female","IT-2021-B",   "School of Computing",      "Spain",  "Information Technology",  "123456"},
                {"20210007", "Ethan Brown",      "Male",  "CS-2021-C",   "School of Computing",      "UK",     "Computer Science",        "123456"},
                {"20210008", "Yuki Tanaka",      "Female","CS-2021-A",   "School of Computing",      "Japan",  "Computer Science",        "123456"},
                {"20210009", "Noah Williams",    "Male",  "SE-2021-C",   "School of Engineering",    "Canada", "Software Engineering",    "123456"},
                {"20210010", "Fatima Al-Hassan", "Female","IT-2021-C",   "School of Computing",      "UAE",    "Information Technology",  "123456"},
        };

        System.out.println("=== Inserting Students ===");
        for (Object[] row : students) {
            Student s = new Student();
            s.setSno((String) row[0]);
            s.setSname((String) row[1]);
            s.setGender((String) row[2]);
            s.setClassname((String) row[3]);
            s.setCollegename((String) row[4]);
            s.setCountry((String) row[5]);
            s.setDepart((String) row[6]);
            s.setPsw((String) row[7]);
            boolean ok = studentDao.addStudent(s);
            System.out.println((ok ? "  OK  " : " SKIP ") + " " + row[0] + " - " + row[1]);
        }

        // ── Courses ───────────────────────────────────────────────────────────
        // CS003, CS004, CS005 match the existing enrollment records
        Object[][] courses = {
                // no,      name,                         credits, instructor,       email,                         time,           semester,     type,       assessment,    limit
                {"CS003", "Data Structures",              3, "Dr. Alice Wang",   "alice@university.edu",   "Mon 1-2",      "Semester 3", "Required", "Written Exam", 40},
                {"CS004", "Database Systems",             3, "Dr. Bob Lee",      "bob@university.edu",     "Tue 3-4",      "Semester 2", "Required", "Project",      35},
                {"CS005", "Operating Systems",            3, "Dr. Carol Smith",  "carol@university.edu",   "Wed 1-2",      "Semester 1", "Required", "Written Exam", 40},
                {"CS006", "Computer Networks",            2, "Dr. David Park",   "david@university.edu",   "Thu 2-3",      "Semester 2", "Elective", "Written Exam", 30},
                {"CS007", "Software Engineering",         3, "Dr. Emily Zhang",  "emily@university.edu",   "Fri 1-2",      "Semester 3", "Required", "Project",      35},
                {"CS008", "Artificial Intelligence",      3, "Dr. Frank Liu",    "frank@university.edu",   "Mon 3-4",      "Semester 4", "Elective", "Project",      25},
        };

        System.out.println("\n=== Inserting Courses ===");
        for (Object[] row : courses) {
            Course c = new Course();
            c.setCourseNo((String) row[0]);
            c.setCourseName((String) row[1]);
            c.setCredits((Integer) row[2]);
            c.setInstructor((String) row[3]);
            c.setInstructorEmail((String) row[4]);
            c.setClassTime((String) row[5]);
            c.setSemester((String) row[6]);
            c.setCourseType((String) row[7]);
            c.setAssessment((String) row[8]);
            c.setEnrollmentLimit((Integer) row[9]);
            c.setLocation("");
            c.setRemarks("");
            c.setOtherInfo("");
            boolean ok = courseDao.insert(c);
            System.out.println((ok ? "  OK  " : " SKIP ") + " " + row[0] + " - " + row[1]);
        }

        // ── Extra Enrollments ─────────────────────────────────────────────────
        // The first three (20210001/CS003, 20210002/CS004, 20210003/CS005)
        // were already inserted by EnrollmentDao.main(). These add more.
        Object[][] enrollments = {
                // sno,         courseno,  semester,       score,  entryTime
                {"20210001", "CS004", "Semester 2", 78.0,  "2026-03-10 08:00:00"},
                {"20210002", "CS003", "Semester 3", 91.0,  "2026-03-10 09:00:00"},
                {"20210003", "CS006", "Semester 2", 55.0,  "2026-03-11 10:00:00"},
                {"20210004", "CS003", "Semester 3", 88.0,  "2026-03-11 11:00:00"},
                {"20210004", "CS005", "Semester 1", 72.0,  "2026-03-11 11:30:00"},
                {"20210005", "CS007", "Semester 3", null,  "2026-03-12 08:00:00"},
                {"20210006", "CS008", "Semester 4", 95.0,  "2026-03-12 09:00:00"},
                {"20210007", "CS003", "Semester 3", 67.0,  "2026-03-13 08:00:00"},
                {"20210008", "CS004", "Semester 2", null,  "2026-03-13 09:00:00"},
                {"20210009", "CS005", "Semester 1", 83.0,  "2026-03-14 10:00:00"},
                {"20210010", "CS006", "Semester 2", 49.0,  "2026-03-14 11:00:00"},
        };

        System.out.println("\n=== Inserting Extra Enrollments ===");
        for (Object[] row : enrollments) {
            Enrollment e = new Enrollment();
            e.setSno((String) row[0]);
            e.setCourseno((String) row[1]);
            e.setEnrollmentTime((String) row[2]);
            e.setScore((Double) row[3]);
            e.setEntryTime((String) row[4]);
            boolean ok = enrollDao.addNewEnrollment(e);
            System.out.println((ok ? "  OK  " : " SKIP ") + " " + row[0] + " -> " + row[1]);
        }

        System.out.println("\nDone. Run the app and check the Enrollments page.");
    }
}
