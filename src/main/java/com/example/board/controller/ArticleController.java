package com.example.board.controller;

import com.example.board.dto.ModifyArticle;
import com.example.board.dto.Search;
import com.example.board.model.Article;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import com.example.board.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/board")
public class ArticleController {
    ListService listService;
    ViewService viewService;
    CommentService commentService;
    WriteService writeService;
    ModifyService modifyService;
    FileDownloadService fileDownloadService;

    public ArticleController(ListService listService, ViewService viewService, CommentService commentService, WriteService writeService, ModifyService modifyService, FileDownloadService fileDownloadService) {
        this.listService = listService;
        this.viewService = viewService;
        this.commentService = commentService;
        this.writeService = writeService;
        this.modifyService = modifyService;
        this.fileDownloadService = fileDownloadService;
    }

    @GetMapping("/list")
    public String getArticles(Search search, Model model){
        listService.getArticleList(search, model);

        return "articleList";
    }

    @GetMapping("/view/{articleId}")
    public String getArticleView(@PathVariable int articleId, Model model) {
        model.addAttribute("article", viewService.getArticleView(articleId));
        model.addAttribute("comments", viewService.getArticleComment(articleId));
        model.addAttribute("files", viewService.getArticleFiles(articleId));

        return "articleView";
    }

    @GetMapping("/write")
    public String getArticleWrite() {
        return "articleWrite";
    }

    @PostMapping("/write")
    public ResponseEntity<?> addArticle(@ModelAttribute Article article,
                                        @RequestPart("files") MultipartFile[] files) {
        try {
            //등록 게시글 컨텐츠 저장 후 id 반환
            int addedArticleId = writeService.addArticle(article);

            //multipartFile 업로드 후 정보담은 객체 반환
            List<FileVo> uploadedList = writeService.uploadFiles(files);

            //파일정보 저장
            writeService.addFileList(uploadedList, addedArticleId);
        } catch (Exception  e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            // TODO: 2024-08-14 파일 용량 초과, 서버측 유효성 에러 어떻게 처리할지 고민
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addArticleComment(@RequestBody Comment comment) {
        int result = commentService.addComment(comment); // add보다는 다른 어휘
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/modify/{articleId}")
    public String modifyArticle(@PathVariable int articleId, Model model) {
        model.addAttribute("article", viewService.getArticleView(articleId));
        model.addAttribute("files", viewService.getArticleFiles(articleId));

        return "modifyArticle";
    }

    @PostMapping("/modify")
    public ResponseEntity<String> modifyArticle(@RequestBody ModifyArticle requestArticle) {
        Article article = Article.builder()
                .id(requestArticle.getId())
                .password(requestArticle.getPassword())
                .build();
        boolean isMatched = modifyService.passwordMatchConfirm(article);
        if (!isMatched) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password!");
        }
        modifyService.modifyArticle(requestArticle);
        modifyService.removeFiles(requestArticle.getRemoveFiles());

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/board/view/" + requestArticle.getId())).build();

    }

    @PostMapping("/pass-check")// 프론트에서 조작할 수 있는 방식
    public ResponseEntity<String> passCheck(@RequestBody Article article) {
        if (modifyService.passwordMatchConfirm(article)) {
            return ResponseEntity.ok("일치합니다");//body 확인 메세지
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
    }

    @ResponseBody
    @GetMapping("/file-download")
    public ResponseEntity<Resource> fileDownload(@RequestParam("fileId") int fileId) {
        System.out.println(fileId);
        return fileDownloadService.fileDownloadService(fileId);
    }
}
