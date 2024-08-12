package com.example.board.service;

import com.example.board.dto.Articles;
import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewService implements HttpService {
    ArticleMapper articleMapper;

    public ViewService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public ServiceResult doService(HttpServletRequest request, HttpServletResponse response) {
        int articleId = Integer.parseInt(request.getParameter("id"));

        Articles articleView = articleMapper.selectArticleById(articleId);
        List<Comment> articleComment = articleMapper.selectComments(articleId);
        FileVo articleFile = articleMapper.selectFiles(articleId);
        request.setAttribute("article", articleView);
        request.setAttribute("comments", articleComment);
        request.setAttribute("file", articleFile);

        return new ServiceResult("dispatcher", "articleOne.jsp",request,response);
    }
    public FileVo getFileById(int fileId) {
        return articleMapper.selectFile(fileId);
    }
}
