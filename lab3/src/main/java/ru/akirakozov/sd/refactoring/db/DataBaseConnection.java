package ru.akirakozov.sd.refactoring.db;

import java.sql.*;

public class DataBaseConnection {
    private String url;
    private Connection connection;

    public DataBaseConnection(String url) {
        this.url = url;
    }

    public DataBaseConnection open() {
        try {
            connection = DriverManager.getConnection(url);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DataBaseConnection executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);

            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DataBaseConnection executeQuery(String sql, ResultSetHandler handler) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            handler.handle(rs);
            rs.close();

            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
