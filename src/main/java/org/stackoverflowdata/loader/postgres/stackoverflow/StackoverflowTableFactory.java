package org.stackoverflowdata.loader.postgres.stackoverflow;

import javax.sql.DataSource;

public class StackoverflowTableFactory {

    private static final String TAGS_ELEMENT = "tags";
    private static final String POSTS_ELEMENT = "posts";
    private final String localName;
    private final DataSource dataSource;

    public StackoverflowTableFactory(String localName, DataSource dataSource) {
        this.localName = localName;
        this.dataSource = dataSource;
    }

    public StackoverflowTable getStackoverflowTable() {
        if (TAGS_ELEMENT.equals(localName)) {
            return new StackoverflowTagTable(this.dataSource);
        } else if (POSTS_ELEMENT.equals(localName)) {
            return new StackoverflowPostTable(this.dataSource);
        }

        throw new UnsupportedOperationException("Unsupported stackoverflow table name:");
    }
}
