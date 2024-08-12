package com.example.board.model;

import lombok.Getter;

@Getter
public class Article {
    private int id;
    private int categoryId;
    private String title;
    private String author;
    private String password;
    private String content;
    private int viewCount;
    private String postDate;
    private String editDate;

    public Article() {
    }

    public Article(int id, int categoryId, String title, String author, String password, String content, int viewCount, String postDate, String editDate) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }
}
