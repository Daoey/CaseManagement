package se.plushogskolan.casemanagement.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultMapper<T> {
    T map(ResultSet resultSet) throws SQLException;
}