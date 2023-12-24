package org.stackoverflowdata.loader.postgres.post;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class Post {
    private Long id;
    private Long postTypeId;
    private Long acceptedAnswerId;  // nullable
    private Long parentId;  // nullable
    private Instant creationDate;
    private Long score;
    private Long viewCount;  // nullable
    private String body;
    private Long ownerUserId;  // nullable
    private Long lastEditorUserId;  // nullable
    private String lastEditorDisplayName;  // nullable
    private Instant lastEditDate;  // nullable
    private Instant lastActivityDate;  // nullable
    private String title;  // nullable
    private String tags;  // nullable
    private Long answerCount;  // nullable
    private Long commentCount;
    private Long favoriteCount;  // nullable
    private Instant closedDate;  // nullable
    private Instant communityOwnedDate;  // nullable
}
