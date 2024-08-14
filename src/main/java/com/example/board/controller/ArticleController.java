package com.example.board.controller;

import com.example.board.dto.Search;
import com.example.board.model.Article;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import com.example.board.service.CommentService;
import com.example.board.service.ListService;
import com.example.board.service.ViewService;
import com.example.board.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class ArticleController {
    @Autowired
    ListService listService;
    @Autowired
    ViewService viewService;
    @Autowired
    CommentService commentService;
    @Autowired
    WriteService writeService;

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

    @PostMapping("/write")
    public ResponseEntity<?> addArticle(@ModelAttribute Article article, @RequestPart("files") MultipartFile[] files) {
        try {
            //등록 게시글 컨텐츠 저장 후 id 반환
            int addedArticleId = writeService.addArticle(article);

            //multipartFile 업로드 후 정보담은 객체 반환
            List<FileVo> uploadedList = writeService.uploadFiles(files);

            //파일정보 저장
            writeService.addFileList(uploadedList, addedArticleId);
        } catch (Exception  e ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            // TODO: 2024-08-14 파일 용량 초과, 서버측 유효성 에러 어떻게 처리할지 고민
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addArticleComment(@RequestBody Comment comment) {
        System.out.println(comment.toString());
        int result = commentService.addComment(comment);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
