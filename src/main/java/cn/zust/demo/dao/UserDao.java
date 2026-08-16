package cn.zust.demo.dao;

import cn.zust.demo.entity.User;
import cn.zust.demo.utils.H2DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public boolean insert(User user) {
        try {
            String sql = "INSERT INTO admin_user(account,pwd,name) VALUES (?,?,?)";
            Connection connection = H2DbUtils.getConn();

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getAccount());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());

            int result = pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            return result >= 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public User getUserByAccount(String account) {

        User user = null;

        try {
            if ("".equalsIgnoreCase(account)) {
                return null;
            }

            String sql = "SELECT account,pwd,name FROM admin_user WHERE account=?";
            Connection connection = H2DbUtils.getConn();

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, account);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setAccount(rs.getString("account"));
                user.setPwd(rs.getString("pwd"));
                user.setName(rs.getString("name"));
            }

            rs.close();
            pstmt.close();
            connection.close();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean updatePassword(String account, String newPwd) {
        try {
            String sql = "UPDATE admin_user SET pwd=? WHERE account=?";
            Connection connection = H2DbUtils.getConn();

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newPwd);
            pstmt.setString(2, account);

            int result = pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllAdmins() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT account, pwd, name FROM admin_user";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setAccount(rs.getString("account"));
                u.setPwd(rs.getString("pwd"));
                u.setName(rs.getString("name"));
                list.add(u);
            }
            rs.close(); pstmt.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteAdmin(String account) {
        try {
            String sql = "DELETE FROM admin_user WHERE account=?";
            Connection conn = H2DbUtils.getConn();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, account);
            int result = pstmt.executeUpdate();
            pstmt.close(); conn.close();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {

        User user = new User();
        user.setAccount("admin");
        user.setPwd("admin");
        user.setName("Administrator");

        UserDao dao = new UserDao();
        dao.insert(user);

        User loginUser = dao.getUserByAccount("admin");

        if (loginUser != null) {
            System.out.println(loginUser.getAccount());
            System.out.println(loginUser.getPwd());
            System.out.println(loginUser.getName());
        }
    }
}