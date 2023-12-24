package org.stackoverflowdata.loader.postgres.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    void saveBatch(List<Post> posts);

    Optional<Post> findById(Long id);
}
