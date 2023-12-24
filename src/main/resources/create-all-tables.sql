DROP TABLE IF EXISTS Tags;
CREATE TABLE Tags
(
    Id            int PRIMARY KEY,
    TagName       text not NULL,
    Count         int,
    ExcerptPostId int,
    WikiPostId    int
);

DROP TABLE IF EXISTS Posts;
CREATE TABLE Posts
(
    id                       int PRIMARY KEY,
    post_type_id             int       not NULL,
    accepted_answer_id       int,
    parent_id                int,
    creation_date            timestamp not NULL,
    score                    int,
    view_count               int,
    body                     text,
    owner_user_id            int,
    last_editor_user_id      int,
    last_editor_display_name text,
    last_edit_date           timestamp,
    last_activity_date       timestamp,
    title                    text,
    tags                     text,
    answer_count             int,
    comment_count            int,
    favorite_count           int,
    closed_date              timestamp,
    community_owned_date     timestamp
);



