package se.plushogskolan.casemanagement.repository.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import se.plushogskolan.casemanagement.exception.RepositoryException;

public interface ResultMapper<T> {
    T map(ResultSet resultSet) throws SQLException, RepositoryException;
}