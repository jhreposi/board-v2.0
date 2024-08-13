package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    ArticleMapper articleMapper;

    public int addComment(Comment comment) {
        return articleMapper.insertComment(comment);
    }
}
