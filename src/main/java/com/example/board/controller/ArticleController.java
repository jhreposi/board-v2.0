package com.example.board.controller;

import com.example.board.dto.Search;
import com.example.board.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class ArticleController {
    ListService listService;

    public ArticleController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping("/list")
    public String getArticles(Search search, Model model){
        model.addAttribute("articles", listService.getArticleList(search));
        return "articleList";
    }
}
