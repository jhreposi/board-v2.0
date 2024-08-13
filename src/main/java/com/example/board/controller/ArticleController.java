package com.example.board.controller;

import com.example.board.dto.Search;
import com.example.board.model.Comment;
import com.example.board.service.CommentService;
import com.example.board.service.ListService;
import com.example.board.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class ArticleController {
    @Autowired
    ListService listService;
    @Autowired
    ViewService viewService;
    @Autowired
    CommentService commentService;

    @GetMapping("/list")
    public String getArticles(Search search, Model model){
        Model serviceModel = listService.getArticleList(search, model);
        return "articleList";
    }

    @GetMapping("/view/{articleId}")
    public String getArticleView(@PathVariable int articleId, Model model) {
        model.addAttribute("article", viewService.getArticleView(articleId));
        model.addAttribute("comments", viewService.getArticleComment(articleId));
        model.addAttribute("file", viewService.getArticleFile(articleId));

        return "articleView";
    }
    @GetMapping("/write")
    public String getArticleWrite() {
        return "articleWrite";
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> addArticleComment(@RequestBody Comment comment) {
        System.out.println(comment.toString());
        int result = commentService.addComment(comment);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
