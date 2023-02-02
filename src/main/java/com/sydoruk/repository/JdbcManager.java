package com.sydoruk.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test?currentSchema=public";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    private JdbcManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}