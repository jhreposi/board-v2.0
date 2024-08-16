package com.example.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModifyArticle {

    private int id;
    private int categoryId;
    private String password;
    private String title;
    private String author;
    private String content;

    private int [] removeFiles;

}
