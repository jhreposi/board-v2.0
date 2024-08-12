package com.example.board.model;

import lombok.Getter;

@Getter
public class Comment {
    private int id;
    private String comment;
    private String postDate;
    private int articleId;

    public Comment() {
    }

}
