package com.example.repository;

import com.example.config.MysqlConnection;
import com.example.model.Post;
import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        Connection conn = MysqlConnection.getConnection();
        String query = "SELECT p.id, p.title, p.description, p.created_at, p.updated_at, u.name  FROM posts AS p INNER JOIN users AS u ON p.user_id = u.id WHERE p.deleted_at IS NULL";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                try {
                    User user = new User();
                    user.setName(rs.getString("name"));

                    post.setUser(user);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                list.add(post);
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
        return list.isEmpty() ? null : list;
    }

    public Post getById(Long id) {
        List<Post> list = new ArrayList<>();
        Connection conn = MysqlConnection.getConnection();
        String query = "SELECT p.id, p.title, p.description, p.created_at, p.updated_at, u.name FROM posts AS p INNER JOIN users AS u ON p.user_id = u.id WHERE p.id = ? AND p.deleted_at IS NULL";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                try {
                    User user = new User();
                    user.setName(rs.getString("name"));

                    post.setUser(user);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                list.add(post);
            }
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
        return list.isEmpty() ? null : list.get(0);
    }

    public int deleteById(Long id) {
        int isDelete = 0;
        Connection conn = MysqlConnection.getConnection();
        String query = "DELETE FROM posts WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, id);
           isDelete = stmt.executeUpdate();
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
        return isDelete;
    }

    public int create(Post post) {
        int isInsert = 0;
        Connection conn = MysqlConnection.getConnection();
        String query = "INSERT INTO posts (title, description, user_id, created_at, updated_at) VALUES (?,?,?,?,?)";

        try {
//            post.getUserId() session user
            Timestamp current = new Timestamp(System.currentTimeMillis());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDescription());
            stmt.setLong(3, 25);
            stmt.setTimestamp(4, current);
            stmt.setTimestamp(5, current);
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

    public int update(Post post) {
        int isUpdate = 0;
        Connection conn = MysqlConnection.getConnection();
        String query = "UPDATE posts SET title = ?, description = ?, updated_at = ? WHERE id = ?";

        try {
            Timestamp current = new Timestamp(System.currentTimeMillis());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDescription());
            stmt.setTimestamp(3, current);
            stmt.setLong(4, post.getId());
            isUpdate = stmt.executeUpdate();
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
        return isUpdate;
    }

    public Post getLatest() {
        List<Post> list = new ArrayList<>();
        Connection conn = MysqlConnection.getConnection();
        String query = "SELECT p.id, p.title, p.description, p.created_at, p.updated_at, u.name FROM posts AS p INNER JOIN users AS u ON p.user_id = u.id WHERE p.deleted_at IS NULL ORDER BY updated_at DESC LIMIT 1";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setDescription(rs.getString("description"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                try {
                    User user = new User();
                    user.setName(rs.getString("name"));

                    post.setUser(user);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                list.add(post);
            }
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
        return list.isEmpty() ? null : list.get(0);
    }
}
