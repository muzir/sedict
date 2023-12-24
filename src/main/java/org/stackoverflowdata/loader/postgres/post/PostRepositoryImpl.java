package org.stackoverflowdata.loader.postgres.post;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {

    private static final String ID = "id";
    private static final String POST_TYPE_ID = "post_type_id";
    private static final String ACCEPTED_ANSWER_ID = "accepted_answer_id";
    private static final String PARENT_ID = "parent_id";
    private static final String CREATION_DATE = "creation_date";
    private static final String SCORE = "score";
    private static final String VIEW_COUNT = "view_count";
    private static final String BODY = "body";
    private static final String OWNER_USER_ID = "owner_user_id";
    private static final String LAST_EDITOR_USER_ID = "last_editor_user_id";
    private static final String LAST_EDITOR_DISPLAY_NAME = "last_editor_display_name";
    private static final String LAST_EDIT_DATE = "last_edit_date";
    private static final String LAST_ACTIVITY_DATE = "last_activity_date";
    private static final String TITLE = "title";
    private static final String TAGS = "tags";
    private static final String ANSWER_COUNT = "answer_count";
    private static final String COMMENT_COUNT = "comment_count";
    private static final String FAVORITE_COUNT = "favorite_count";
    private static final String CLOSED_DATE = "closed_date";
    private static final String COMMUNITY_OWNED_DATE = "community_owned_date";
    private final DataSource dataSource;

    public PostRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveBatch(List<Post> posts) {
        try (var connection = dataSource.getConnection()) {
            // create a statement which will have all post table columns
            var statement = connection.prepareStatement(
                    "INSERT INTO posts (id, post_type_id, accepted_answer_id, parent_id, creation_date, score, view_count, body, owner_user_id, last_editor_user_id, last_editor_display_name, last_edit_date, last_activity_date, title, tags, answer_count, comment_count, favorite_count, closed_date, community_owned_date) \n" +
                            "VALUES \n" + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (Post post : posts) {
                // Assuming `statement` is your PreparedStatement
                statement.setLong(1, post.getId());
                statement.setLong(2, post.getPostTypeId());
                statement.setLong(3, post.getAcceptedAnswerId());
                statement.setLong(4, post.getParentId());
                statement.setTimestamp(5, Optional.ofNullable(post.getCreationDate())
                        .map(Timestamp::from)
                        .orElse(null));
                statement.setLong(6, post.getScore());
                statement.setLong(7, post.getViewCount());
                statement.setString(8, post.getBody());
                statement.setLong(9, post.getOwnerUserId());
                statement.setLong(10, post.getLastEditorUserId());
                statement.setString(11, post.getLastEditorDisplayName());
                statement.setTimestamp(12,
                        post.getLastEditDate() != null ? Timestamp.from(post.getLastEditDate()) : null);
                statement.setTimestamp(13,
                        post.getLastActivityDate() != null ? Timestamp.from(post.getLastActivityDate()) : null);
                statement.setString(14, post.getTitle());
                statement.setString(15, post.getTags());
                statement.setLong(16, post.getAnswerCount());
                statement.setLong(17, post.getCommentCount());
                statement.setLong(18, post.getFavoriteCount());
                statement.setTimestamp(19, post.getClosedDate() != null ? Timestamp.from(post.getClosedDate()) : null);
                statement.setTimestamp(20,
                        post.getCommunityOwnedDate() != null ? Timestamp.from(post.getCommunityOwnedDate()) : null);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
        }
    }

    public Optional<Post> mapResultSetToPost(ResultSet resultSet) throws SQLException {
        return Optional.of(Post.builder().id(resultSet.getLong(ID)).postTypeId(resultSet.getLong(POST_TYPE_ID))
                .acceptedAnswerId(resultSet.getLong(ACCEPTED_ANSWER_ID)).parentId(resultSet.getLong(PARENT_ID))
                .creationDate(resultSet.getTimestamp(CREATION_DATE).toInstant()).score(resultSet.getLong(SCORE))
                .viewCount(resultSet.getLong(VIEW_COUNT)).body(resultSet.getString(BODY))
                .ownerUserId(resultSet.getLong(OWNER_USER_ID))
                .lastEditorUserId(resultSet.getLong(LAST_EDITOR_USER_ID))
                .lastEditorDisplayName(resultSet.getString(LAST_EDITOR_DISPLAY_NAME))
                .lastEditDate(Optional.ofNullable(resultSet.getTimestamp(LAST_EDIT_DATE)).map(Timestamp::toInstant)
                        .orElse(null))
                .lastEditDate(Optional.ofNullable(resultSet.getTimestamp(LAST_EDIT_DATE)).map(Timestamp::toInstant)
                        .orElse(null))
                .title(resultSet.getString(TITLE)).tags(resultSet.getString(TAGS))
                .answerCount(resultSet.getLong(ANSWER_COUNT)).commentCount(resultSet.getLong(COMMENT_COUNT))
                .favoriteCount(resultSet.getLong(FAVORITE_COUNT))
                // closedDate and communityOwnedDate are nullable
                .closedDate(
                        Optional.ofNullable(resultSet.getTimestamp(CLOSED_DATE)).map(Timestamp::toInstant).orElse(null))
                .communityOwnedDate(
                        Optional.ofNullable(resultSet.getTimestamp(COMMUNITY_OWNED_DATE)).map(Timestamp::toInstant)
                                .orElse(null)).build());
    }

    @Override
    public Optional<Post> findById(Long id) {
        // Write find by id query
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM posts WHERE id = ?");
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Move all literals to constants
                return mapResultSetToPost(resultSet);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
