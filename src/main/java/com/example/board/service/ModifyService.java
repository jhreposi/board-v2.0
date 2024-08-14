package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyService {
    @Autowired
    ArticleMapper articleMapper;

    public boolean passwordMatchConfirm(Article article) {
        int matchCount =  articleMapper.articlePasswordMatch(article);
        return matchCount != 0;
    }
}
