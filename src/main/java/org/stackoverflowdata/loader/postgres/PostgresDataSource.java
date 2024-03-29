package org.stackoverflowdata.loader.postgres;

import org.postgresql.ds.PGSimpleDataSource;
import org.stackoverflowdata.loader.postgres.config.DatabaseConnectionRepository;

import javax.sql.DataSource;

public class PostgresDataSource {

    private final String DATABASE_URL = "jdbc:postgresql://localhost:15432/stackoverflow";
    private final String USER = "postgres";
    private final String PASSWORD = "mysecretpassword";

    private static DataSource dataSource = null;

    public PostgresDataSource() {
        PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(DATABASE_URL);
        pgDataSource.setUser(USER);
        pgDataSource.setPassword(PASSWORD);
        this.dataSource = pgDataSource;
    }

    public PostgresDataSource(String databaseUrl, String user, String password) {
        PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(databaseUrl);
        pgDataSource.setUser(user);
        pgDataSource.setPassword(password);
        this.dataSource = pgDataSource;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}

