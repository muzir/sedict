package org.stackoverflowdata.loader.postgres.post;

import org.junit.jupiter.api.Test;
import org.stackoverflowdata.loader.postgres.BaseIntegrationTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostRepositoryTest extends BaseIntegrationTest {

    private PostRepository postRepository = new PostRepositoryImpl(dataSource);

    @Test
    public void testSavePostsBatch() {
        var post = Post.builder()
                .id(1L)
                .postTypeId(1L)
                .acceptedAnswerId(1L)
                .parentId(1L)
                .creationDate(Instant.now())
                .score(1L)
                .viewCount(1L)
                .body("Test body")
                .ownerUserId(1L)
                .lastEditorUserId(1L)
                .lastEditorDisplayName("Test last editor display name")
                .lastEditDate(null)
                .lastActivityDate(null)
                .title("Test title")
                .tags("Test tags")
                .answerCount(1L)
                .commentCount(1L)
                .favoriteCount(1L)
                .closedDate(null)
                .communityOwnedDate(null)
                .build();


        // Act
        postRepository.saveBatch(List.of(post));

        // Assert
        assertEquals("Test body", postRepository.findById(1L).get().getBody(), "Body name should be equal");

    }
}
