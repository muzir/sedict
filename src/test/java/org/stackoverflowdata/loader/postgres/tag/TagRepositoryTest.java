package org.stackoverflowdata.loader.postgres.tag;

import org.junit.jupiter.api.Test;
import org.stackoverflowdata.loader.postgres.BaseIntegrationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagRepositoryTest extends BaseIntegrationTest {

    private TagRepository tagRepository = new TagRepositoryImpl(dataSource);

    @Test
    public void testSaveTagsBatch() {
        // Arrange
        var tag = new Tag(1, "TestTag", 5, 1, 2);

        // Act
        tagRepository.saveBatch(List.of(tag));

        // Assert
        assertEquals("TestTag", tagRepository.findById(1).getTagName(), "Tag name should be equal");

    }
}
