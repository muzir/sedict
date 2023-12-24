package org.stackoverflowdata.loader.postgres.stackoverflow;


import org.stackoverflowdata.loader.postgres.PostgresDataSource;
import org.stackoverflowdata.loader.postgres.tag.Tag;
import org.stackoverflowdata.loader.postgres.tag.TagRepository;
import org.stackoverflowdata.loader.postgres.tag.TagRepositoryImpl;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StackoverflowTagTable implements StackoverflowTable {

    private List<Tag> tags;
    private final TagRepository tagRepository;

    private static final String TAGS_ELEMENT = "tags";

    public StackoverflowTagTable(DataSource dataSource) {
        this.tags = new ArrayList<>();
        this.tagRepository = new TagRepositoryImpl(dataSource);
    }

    @Override
    public String getXmlElementName() {
        return TAGS_ELEMENT;
    }

    public void addRow(XMLStreamReader xmlStreamReader) {
        var tag = new Tag(parseIntValue(xmlStreamReader.getAttributeValue(null, "Id")),
                xmlStreamReader.getAttributeValue(null, "TagName"),
                parseIntValue(xmlStreamReader.getAttributeValue(null, "Count")),
                parseIntValue(xmlStreamReader.getAttributeValue(null, "ExcerptPostId")),
                parseIntValue(xmlStreamReader.getAttributeValue(null, "WikiPostId")));
        tags.add(tag);
    }

    @Override
    public void saveBatch() {
        System.out.println("Saving tag size:" + tags.size());
        tagRepository.saveBatch(tags);
        tags = new ArrayList<>();
    }

    int parseIntValue(String value) {
        return Optional.ofNullable(value).map(v -> Integer.parseInt(v)).orElse(0);
    }
}
