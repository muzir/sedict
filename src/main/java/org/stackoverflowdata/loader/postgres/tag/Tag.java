package org.stackoverflowdata.loader.postgres.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tag {

    private int id;
    private String tagName;
    private int count;
    private int excerptPostId;
    private int wikiPostId;
}

