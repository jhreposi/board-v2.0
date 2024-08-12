package com.example.board.dto;

import lombok.Getter;

@Getter
public class Articles {
    private String name; //category name
    private int id;
    private int categoryId;
    private String title;
    private String author;
    private String password;
    private String content;
    private int viewCount;
    private String postDate;
    private String editDate;

    public Articles() {
    }

    public Articles(String name, int id, int categoryId, String title, String author, String password, String content, int viewCount, String postDate, String editDate) {
        this.name = name;
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.password = password;
        this.content = content;
        this.viewCount = viewCount;
        this.postDate = postDate;
        this.editDate = editDate;
    }

}
