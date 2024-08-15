package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModifyArticle {

    private int id;
    private int categoryId;
    private String title;
    private String author;
    private String content;

    private int [] removeFiles;
    public ModifyArticle(int categoryId, String title, String author, String content, int[] removeFiles) {
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.content = content;
        this.removeFiles = removeFiles;
    }
}
