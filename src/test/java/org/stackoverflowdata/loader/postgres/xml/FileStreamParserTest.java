package org.stackoverflowdata.loader.postgres.xml;

import org.junit.jupiter.api.Test;
import org.stackoverflowdata.loader.postgres.BaseIntegrationTest;
import org.stackoverflowdata.loader.postgres.FilePath;
import org.stackoverflowdata.loader.postgres.post.PostRepository;
import org.stackoverflowdata.loader.postgres.post.PostRepositoryImpl;
import org.stackoverflowdata.loader.postgres.tag.TagRepository;
import org.stackoverflowdata.loader.postgres.tag.TagRepositoryImpl;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileStreamParserTest extends BaseIntegrationTest {
    private final static String Tags_File_Location = "Tags-test.xml";
    private final static String Posts_File_Location = "Posts-test.xml";

    private TagRepository tagRepository = new TagRepositoryImpl(dataSource);
    private PostRepository postRepository = new PostRepositoryImpl(dataSource);

    @Test
    void testSaveTagSuccessfully() throws XMLStreamException, FileNotFoundException {
        var fileStreamParser =
                new FileStreamParser(new FileStreamReader(new FilePath(Tags_File_Location)).getXmlStreamReader(),
                        dataSource);
        fileStreamParser.parseXmlData();
        var tag = tagRepository.findById(1);
        assertEquals(tag.getTagName(), ".net");
    }

    @Test
    void testSavePostsSuccessfully() throws XMLStreamException, FileNotFoundException {
        var fileStreamParser =
                new FileStreamParser(new FileStreamReader(new FilePath(Posts_File_Location)).getXmlStreamReader(),
                        dataSource);
        fileStreamParser.parseXmlData();
        var post = postRepository.findById(4L);
        assertTrue(post.isPresent());
        assertEquals(post.get().getTitle(), "How to convert Decimal to Double in C#?");
    }
}