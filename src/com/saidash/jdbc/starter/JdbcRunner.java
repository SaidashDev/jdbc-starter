package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        //В самом конце нужно закрывать pool соединений
        try{
            checkMetaData();
        } finally {
            ConnectionManager.closePool();
        }




    }

    private static void checkMetaData() throws SQLException {

        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()){
                var catalog = catalogs.getString(1);
                var schemas = metaData.getSchemas();
                while (schemas.next()){
                    var schema = schemas.getString("TABLE_SCHEM");
                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    if(schema.equals("public")) {
                        while (tables.next()) {
                            System.out.println(tables.getString("TABLE_NAME"));
                        }
                    }
                }

            }




        }


    }





    private static List<Long> getFlightsBetween (LocalDateTime start, LocalDateTime end) throws SQLException {

        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?                
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {

            // Java прил будет доставать по 50 строк за раз
            preparedStatement.setFetchSize(50); //ограничение на 1 сетевой round-trip (запрос),
            preparedStatement.setQueryTimeout(10);
            preparedStatement.setMaxRows(100); // ограничение на весь ResultSet

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }

        return result;
    }




    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;

        List<Long> result = new ArrayList<>();

        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, flightId);

            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe

            }
        }
        return result;
    }
}

