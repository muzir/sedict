package org.stackoverflowdata.loader.postgres.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnectionRepository {

    private static final String CONNECTION_TEST_QUERY = "SELECT 1";
    private final DataSource dataSource;

    public DatabaseConnectionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void connect() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CONNECTION_TEST_QUERY);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
}
