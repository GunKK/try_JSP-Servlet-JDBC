package com.example.repository;
import com.example.config.MysqlConnection;
import com.example.model.Post;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public User findUserByEmail(String email) {
        List<User> list = new ArrayList<>();
        Connection conn = MysqlConnection.getConnection();
        String query = "Select * from users where users.email = ? and user.deleted_at=null";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getLong("role"));
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }
        return list.isEmpty() ? null : list.get(0);
    }

    public int create(User user) {
        int isInsert = 0;
        Connection conn = MysqlConnection.getConnection();
        String query = "INSERT INTO posts (name, email, password, role, created_at, updated_at) VALUES (?,?,?,?,?,?)";

        try {
            Timestamp current = new Timestamp(System.currentTimeMillis());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setLong(4, user.getRole());
            stmt.setTimestamp(5, current);
            stmt.setTimestamp(6, current);
            isInsert = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }
        return isInsert;
    }
}
