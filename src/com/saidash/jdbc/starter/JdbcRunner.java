package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS info (
                    id SERIAL PRIMARY KEY,
                    data TEXT NOT NULL 
                );
                """;

        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());
            System.out.println(connection.getSchema());
            var executeResult = statement.execute(sql);
            System.out.println(executeResult);

        }
    }
}

