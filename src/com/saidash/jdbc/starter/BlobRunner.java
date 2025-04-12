package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// По хорошему, с этим не работают современные базы данных, это очень затратно и замедляет
// используют сторонние хранилища и вставляют только URL в бд

public class BlobRunner {
    public static void main(String[] args) throws SQLException, IOException {
//        blob - binary large object (bytea в Postgresql)
//        clop - character large object (TEXT в Postgresql)
        getImage();
    }


    private static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM aircraft
                WHERE id = ?
                """;

        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var image = resultSet.getBytes("image");
                Files.write(Path.of("resources", "boing777_new.jpg"), image, StandardOpenOption.CREATE);
            }
        }
    }


    private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE aircraft
                SET image = ?
                Where id = 1
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "boing777.jpg")));
            preparedStatement.executeUpdate();

        }
    }



    /*                  ВАЖНО !!!
     * НИЖЕ ПРИВЕДЕН ПРИМЕР ДЛЯ ORACLE, ГДЕ СУЩЕСТВУЕТ Blob и Clop
     * blob - binary large object (bytea в Postgresql)
     * clop - character large object (TEXT в Postgresql)
     * В случае с POSTGRESQL это работать не будет.
     * Обработка транзакций тоже приведен не полный. Правильный пример в TransactionRunner
     */

//    private static void saveImage() throws SQLException, IOException {
//        var sql = """
//                UPDATE aircraft
//                SET image = ?
//                Where id = 1
//                """;
//        try (var connection = ConnectionManager.open();
//             var preparedStatement = connection.prepareStatement(sql)) {
//            connection.setAutoCommit(false);
//
//            var blob = connection.createBlob();
//            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "boing777.jpg")));
//            preparedStatement.setBlob(1, blob);
//            preparedStatement.executeUpdate();
//
//            connection.commit();
//        }
//    }
//


}
