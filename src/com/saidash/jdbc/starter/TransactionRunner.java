package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {

        long flightId = 8;
        var deleteFlightSql = "DELETE FROM flight WHERE id = " + flightId;
        var deleteTicketsSql = "DELETE FROM ticket WHERE flight_id = " + flightId;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            //Для batch-запросов prepared statement не подходит!!!
            statement = connection.createStatement();

            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);

            var ints = statement.executeBatch();

            connection.commit();

        }catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
               statement.close();
            }

        }
    }
}
