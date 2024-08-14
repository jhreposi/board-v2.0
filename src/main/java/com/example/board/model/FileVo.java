package com.example.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileVo {
    private int id;
    private int articleId;
    private String uuidName;
    private String originalName;
    private String dir;

    @Builder
    public FileVo(int articleId, String uuidName, String originalName, String dir) {
        this.articleId = articleId;
        this.uuidName = uuidName;
        this.originalName = originalName;
        this.dir = dir;
    }
}
