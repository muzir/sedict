package org.stackoverflowdata.loader.postgres.tag;

import java.util.List;

public interface TagRepository {

    void saveBatch(List<Tag> tags);

    Tag findById(int tagId);
}
