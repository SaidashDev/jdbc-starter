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
                SELECT * 
                FROM ticket 
                """;

        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation());

            System.out.println(connection.getSchema());


            var executeResult = statement.executeQuery(sql);
            while (executeResult.next()){
                System.out.println(executeResult.getLong("id"));
                System.out.println(executeResult.getString("passenger_name"));
                System.out.println(executeResult.getBigDecimal("cost"));
                System.out.println("------------------------");
            }

        }
    }
}

