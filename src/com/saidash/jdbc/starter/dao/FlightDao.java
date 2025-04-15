package com.saidash.jdbc.starter.dao;

import com.saidash.jdbc.starter.entity.Flight;
import com.saidash.jdbc.starter.exception.DaoException;
import com.saidash.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();

    private FlightDao () {

    };

    private static final String FIND_BY_ID_SQL = """
            SELECT  id,
                    flight_no,
                    departure_date,
                    departure_airport_code,
                    arrival_date,
                    arrival_airport_code,
                    aircraft_id,
                    status
            FROM flight
            WHERE id = ? 
            """;


    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight entity) {
        return null;
    }

    @Override
    public void update(Flight entity) {

    }

    //findByID требует нового соединения, но брать его из пула не стоит на самом деле
    //Может быть долгое ожидание, из-за того, что нет свободных connection
    //Может быть deadLock
    //В реальных приложениях, connection открывают на уровне сервисов
    //и уже в сервисах передают connection на уровень Dao
    //с помощью Аспектно-ориенитрованного программирования
    //или с помощью ThreadLocal переменных
    //или самый простой вариант - принимать connection, реализация снизу


    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get()){
            return findById(id, connection);
        }catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Flight> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if(resultSet.next()) {

                flight = buildFlight(resultSet);

            }

            return Optional.ofNullable(flight);

        }catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }


    private Flight buildFlight(ResultSet resultSet) {
        try {
            return new Flight(
                    resultSet.getLong("id"),
                    resultSet.getString("flight_no"),
                    resultSet.getTimestamp("departure_date").toLocalDateTime(),
                    resultSet.getString("departure_airport_code"),
                    resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                    resultSet.getString("arrival_airport_code"),
                    resultSet.getInt("aircraft_id"),
                    resultSet.getString("status")
            );

        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<Flight> findAll() {
        return List.of();
    }
}
