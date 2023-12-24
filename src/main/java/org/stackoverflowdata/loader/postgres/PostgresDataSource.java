package org.stackoverflowdata.loader.postgres;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class PostgresDataSource {

    private final String DATABASE_URL = "jdbc:postgresql://localhost:15432/stackoverflow";
    private final String USER = "postgres";
    private final String PASSWORD = "mysecretpassword";

    private final DataSource dataSource;

    public PostgresDataSource() {
        PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(DATABASE_URL);
        pgDataSource.setUser(USER);
        pgDataSource.setPassword(PASSWORD);
        this.dataSource = pgDataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}

