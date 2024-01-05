package org.stackoverflowdata.loader.postgres.tag;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagRepositoryImpl implements TagRepository {

    private final static String TABLE_NAME = "tags";
    private final static String ID = "id";
    private final static String TAG_NAME = "tagname";
    private final static String COUNT = "count";
    private final static String EXCERPT_POST_ID = "excerptpostid";
    private final static String WIKI_POST_ID = "wikipostid";

    private final DataSource dataSource;

    public TagRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String SAVE_TAG_QUERY =
            "INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?) RETURNING %s".formatted(TABLE_NAME, ID, TAG_NAME,
                    COUNT,
                    EXCERPT_POST_ID, WIKI_POST_ID, ID);
    private static final String FIND_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE %s = ?".formatted(ID);

    @Override
    public void saveBatch(List<Tag> tags) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_TAG_QUERY)) {
            for (Tag tag : tags) {
                preparedStatement.setInt(1, tag.getId());
                preparedStatement.setString(2, tag.getTagName());
                preparedStatement.setInt(3, tag.getCount());
                preparedStatement.setInt(4, tag.getExcerptPostId());
                preparedStatement.setInt(5, tag.getWikiPostId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
        }
    }

    public Tag findById(int tagId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TAG_BY_ID_QUERY)) {

            preparedStatement.setInt(1, tagId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Tag tag = new Tag(resultSet.getInt(ID), resultSet.getString(TAG_NAME), resultSet.getInt(COUNT),
                        resultSet.getInt(EXCERPT_POST_ID), resultSet.getInt(WIKI_POST_ID));
                return tag;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
        }

        return null; // Return null if no tag is found
    }
}

