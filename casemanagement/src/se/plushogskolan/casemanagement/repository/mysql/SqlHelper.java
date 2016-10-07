package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.plushogskolan.casemanagement.mapper.ResultMapper;

public final class SqlHelper {

    private final String url;
    private final List<Object> parameters;
    private String query;

    public SqlHelper(String url) {
        this.url = url;
        this.parameters = new ArrayList<>();
    }

    public SqlHelper query(String query) {
        this.query = query;
        return this;
    }

    public SqlHelper parameter(String parameter) {
        parameters.add(parameter);
        return this;
    }

    public SqlHelper parameter(int parameter) {
        parameters.add(parameter);
        return this;
    }

    public SqlHelper parameter(boolean parameter) {
        parameters.add(parameter);
        return this;
    }

    public void update() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = preparedStatement(connection)) {

            statement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new SQLException("Could not update database" , e);
        }
    }

    public <T> T single(ResultMapper<T> mapper) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = preparedStatement(connection);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                connection.commit();
                return mapper.map(resultSet);
            } else {
                return null;
            }
        }
    }

    public <T> List<T> many(ResultMapper<T> mapper) throws SQLException {

        List<T> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = preparedStatement(connection);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(mapper.map(resultSet));
            }
            connection.commit();
            
            if (result.isEmpty()) 
                return null;
        }
        return result;
    }

    private PreparedStatement preparedStatement(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(query);

        for (int i = 0; i < parameters.size(); i++) {
            statement.setObject(i + 1, parameters.get(i));
        }

        return statement;
    }

}
