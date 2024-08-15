package com.example.board.service;

import com.example.board.dto.ModifyArticle;
import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ModifyService {
    @Autowired
    ArticleMapper articleMapper;

    public boolean passwordMatchConfirm(Article article) {
        int matchCount =  articleMapper.articlePasswordMatch(article);
        return matchCount != 0;
    }

    public void modifyArticle(ModifyArticle modifyArticle) {
        Article article = Article.builder()
                .id(modifyArticle.getId())
                .title(modifyArticle.getTitle())
                .author(modifyArticle.getAuthor())
                .content(modifyArticle.getContent())
                .categoryId(modifyArticle.getCategoryId())
                .build();

        articleMapper.updateArticle(article);
    }

    public void removeFiles(int [] fileIds) {
        Arrays.stream(fileIds).forEach(fileId -> {
            articleMapper.deleteFile(fileId);
        });
    }
}
