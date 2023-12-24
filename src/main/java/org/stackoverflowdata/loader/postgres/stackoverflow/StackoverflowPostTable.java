package org.stackoverflowdata.loader.postgres.stackoverflow;


import org.stackoverflowdata.loader.postgres.post.Post;
import org.stackoverflowdata.loader.postgres.post.PostRepository;
import org.stackoverflowdata.loader.postgres.post.PostRepositoryImpl;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StackoverflowPostTable implements StackoverflowTable {

    private List<Post> posts;
    private final PostRepository postRepository;

    private static final String POSTS_ELEMENT = "posts";

    public StackoverflowPostTable(DataSource dataSource) {
        this.posts = new ArrayList<>();
        this.postRepository = new PostRepositoryImpl(dataSource);
    }

    @Override
    public String getXmlElementName() {
        return POSTS_ELEMENT;
    }

    public void addRow(XMLStreamReader xmlStreamReader) {
        var post = Post.builder()
                .id(Long.parseLong(xmlStreamReader.getAttributeValue(null, "Id")))
                .postTypeId(Long.parseLong(xmlStreamReader.getAttributeValue(null, "PostTypeId")))
                .acceptedAnswerId(parseLongValue(xmlStreamReader.getAttributeValue(null, "AcceptedAnswerId")))
                .parentId(parseLongValue(xmlStreamReader.getAttributeValue(null, "ParentId")))
                .creationDate(parseInstantValue(xmlStreamReader.getAttributeValue(null, "CreationDate")))
                .score(parseLongValue(xmlStreamReader.getAttributeValue(null, "Score")))
                .viewCount(parseLongValue(xmlStreamReader.getAttributeValue(null, "ViewCount")))
                .body(xmlStreamReader.getAttributeValue(null, "Body"))
                .ownerUserId(parseLongValue(xmlStreamReader.getAttributeValue(null, "OwnerUserId")))
                .lastEditorUserId(parseLongValue(xmlStreamReader.getAttributeValue(null, "LastEditorUserId")))
                .lastEditorDisplayName(xmlStreamReader.getAttributeValue(null, "LastEditorDisplayName"))
                .lastEditDate(parseInstantValue(xmlStreamReader.getAttributeValue(null, "LastEditDate")))
                .lastActivityDate(parseInstantValue(xmlStreamReader.getAttributeValue(null, "LastActivityDate")))
                .title(xmlStreamReader.getAttributeValue(null, "Title"))
                .tags(xmlStreamReader.getAttributeValue(null, "Tags"))
                .answerCount(parseLongValue(xmlStreamReader.getAttributeValue(null, "AnswerCount")))
                .commentCount(parseLongValue(xmlStreamReader.getAttributeValue(null, "CommentCount")))
                .favoriteCount(parseLongValue(xmlStreamReader.getAttributeValue(null, "FavoriteCount")))
                .closedDate(parseInstantValue(xmlStreamReader.getAttributeValue(null, "ClosedDate")))
                .communityOwnedDate(parseInstantValue(xmlStreamReader.getAttributeValue(null, "CommunityOwnedDate")))
                .build();
        posts.add(post);
    }

    @Override
    public void saveBatch() {
        System.out.println("Saving posts size:" + posts.size());
        postRepository.saveBatch(posts);
        posts = new ArrayList<>();
    }

    Long parseLongValue(String value) {
        return Optional.ofNullable(value).map(v -> Long.parseLong(v)).orElse(0L);
    }

    Instant parseInstantValue(String value) {
        return Optional.ofNullable(value).map(v -> LocalDateTime.parse(v).toInstant(ZoneOffset.UTC)).orElse(null);
    }
}
