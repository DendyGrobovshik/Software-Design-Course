package ru.akirakozov.sd.refactoring.db;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler {
    void handle(ResultSet rs) throws SQLException, IOException;
}