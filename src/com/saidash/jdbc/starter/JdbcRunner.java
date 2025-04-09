package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        try (var connection = ConnectionManager.open()) {

            System.out.println(connection.getTransactionIsolation());
        }
    }
}

