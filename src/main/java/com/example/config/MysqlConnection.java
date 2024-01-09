package com.example.config;

import com.example.common.SystemConst;

import java.sql.*;

public class MysqlConnection {
    private final static String url = SystemConst.DB_DATABASE;
    private final static String username = SystemConst.DB_USERNAME;
    private final static String password = SystemConst.DB_PASSWORD;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("not found driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("not found database");
            e.printStackTrace();
        }
        return null;
    }
}
