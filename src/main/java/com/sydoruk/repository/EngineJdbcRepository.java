package com.sydoruk.repository;

import com.sydoruk.model.Engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EngineJdbcRepository implements InterfaceRepository<Engine> {
    private final Connection connection;
    private static EngineJdbcRepository instance;

    private EngineJdbcRepository (Connection connection){
        this.connection = connection;
    }

    public static EngineJdbcRepository getInstance (Connection connection) {
        if (instance == null) {
            instance = new EngineJdbcRepository (connection);
        }
        return instance;
    }


    @Override
    public void save(final Engine engine) {
        try {
            connection.setAutoCommit(false);
            final String insertEngine = "INSERT INTO engines (idEngine, typeEngine, power) VALUES (?, ?, ?)";
            try (PreparedStatement saveEngine = connection.prepareStatement(insertEngine)) {
                saveEngine.setString(1, engine.getId());
                saveEngine.setString(2, engine.getType());
                saveEngine.setInt(3, engine.getPower());
                saveEngine.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Engine> getAll() {
        List<Engine> engines = new ArrayList<>();
        final String selectAll = "SELECT * FROM engines";
        try (PreparedStatement getAllEngine = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = getAllEngine.executeQuery();
            while (resultSet.next()) {
                Engine engine = new Engine(resultSet.getString("idEngine"),
                resultSet.getInt("power"), resultSet.getString("typeEngine"));
                engines.add(engine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return engines;
    }

    @Override
    public Engine getById(final String id) {
        Engine engine = null;
        final String getEngine = "SELECT * FROM engines WHERE idEngine = ?;";
        try (PreparedStatement getEngineById = connection.prepareStatement(getEngine)) {
            getEngineById.setString(1, id);
            ResultSet resultSet = getEngineById.executeQuery();
            while (resultSet.next()) {
                engine = new Engine(resultSet.getString("idEngine"), resultSet.getInt("power"),
                        resultSet.getString("typeEngine"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return engine;
    }

    @Override
    public void delete(final String id) {
        final String deleteEngineById = "DELETE FROM engines WHERE idEnginer = ?;";
        try (PreparedStatement deleteEngine = connection.prepareStatement(deleteEngineById)) {
            deleteEngine.setString(1, id);
            deleteEngine.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}