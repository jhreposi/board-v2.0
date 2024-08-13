package com.example.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
    private int id;
    private String comment;
    private String postDate;
    private int articleId;

    public Comment() {
    }

}
