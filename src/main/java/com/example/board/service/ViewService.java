package com.example.board.service;

import com.example.board.dto.Articles;
import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ViewService {
    ArticleMapper articleMapper;

    public ViewService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public Articles getArticleView(int articleId) {
       return articleMapper.selectArticleById(articleId);
    }

    public List<Comment> getArticleComment(int articleId) {
        return articleMapper.selectComments(articleId);
    }
    public List<FileVo> getArticleFiles(int articleId) {
        return articleMapper.selectFiles(articleId);
    }
}
