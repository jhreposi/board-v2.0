package com.example.board.mapper;

import com.example.board.dto.Articles;
import com.example.board.dto.Search;
import com.example.board.model.Article;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    List<Articles> selectAllArticle(Map<String, Object> map);
    int insertArticle(Article article);
    int insertFile(FileVo fileVo);
    Articles selectArticleById(int articleId);
    List<Comment> selectComments(int articleId);
    List<FileVo> selectFiles(int articleId);
    FileVo selectFile(int fileId);
    int countArticle();
    int countArticleOption(Search search);
    int insertComment(Comment comment);
    int articlePasswordMatch(Article article);
    int updateArticle(Article article);
    int deleteFile(int fileId);

}
