package org.stackoverflowdata.loader.postgres;

import org.junit.jupiter.api.BeforeEach;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

public class BaseIntegrationTest {

    private final String USER = "postgres";
    private final String PASSWORD = "mysecretpassword";

    protected final DataSource dataSource;
    private final String databaseUrl;

    @BeforeEach
    public void setUp() throws Exception {
        String[] fileNames = {"create-all-tables.sql"};
        for (String fileName : fileNames) {
            runSqlFile(fileName);
        }
    }

    public BaseIntegrationTest() {
        this.databaseUrl = new AppConfig().loadProperties("local-test");
        this.dataSource = createTestDataSource();
    }

    private DataSource createTestDataSource() {
        PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
        pgDataSource.setUrl(databaseUrl);
        pgDataSource.setUser(USER);
        pgDataSource.setPassword(PASSWORD);
        return pgDataSource;
    }

    public void runSqlFile(String fileName) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        try (Connection connection = this.dataSource.getConnection()) {
            // Disable auto-commit to handle transactions
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                StringBuilder sql = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Ignore comments and empty lines
                        if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                            sql.append(line).append(" ");
                        }
                    }
                }

                // Split the SQL commands using semicolon as delimiter
                String[] commands = sql.toString().split(";");

                // Execute each command
                for (String command : commands) {
                    statement.addBatch(command);
                }

                // Execute the batch of commands
                statement.executeBatch();

                // Commit the transaction
                connection.commit();

                System.out.println("SQL file executed successfully.");
            } catch (Exception e) {
                // Rollback the transaction in case of any error
                connection.rollback();
                throw e;
            }
        }
    }

}
