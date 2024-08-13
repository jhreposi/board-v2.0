package com.example.board.controller;

import com.example.board.dto.Search;
import com.example.board.service.ListService;
import com.example.board.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class ArticleController {
    @Autowired
    ListService listService;
    @Autowired
    ViewService viewService;

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
}
