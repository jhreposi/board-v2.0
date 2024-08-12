package com.example.board.model;

import lombok.Getter;

@Getter
public class FileVo {
    private int id;
    private int articleId;
    private String uuidName;
    private String originalName;
    private String dir;

    public FileVo() {
    }

    public FileVo(int id, int articleId, String uuidName, String originalName, String dir) {
        this.id = id;
        this.articleId = articleId;
        this.uuidName = uuidName;
        this.originalName = originalName;
        this.dir = dir;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
