package org.stackoverflowdata.loader.postgres.config;

import org.junit.jupiter.api.Test;
import org.stackoverflowdata.loader.postgres.BaseIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DatabaseConnectionRepositoryTest extends BaseIntegrationTest {

    DatabaseConnectionRepository databaseConnectionRepository = new DatabaseConnectionRepository(dataSource);

    @Test
    public void testSaveConnection() {
        // Act
        assertDoesNotThrow(() -> databaseConnectionRepository.connect());
    }
}
