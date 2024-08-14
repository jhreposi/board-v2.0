package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Article;
import com.example.board.model.FileVo;
import com.example.board.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class WriteService {
    ArticleMapper articleMapper;
    @Value("${spring.servlet.multipart.location}")
    String attaches;

    public WriteService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public int addArticle(Article article) {
        articleMapper.insertArticle(article);
        return article.getId();
    }

    public List<FileVo> uploadFiles(MultipartFile[] multipartFiles) throws IOException {
        MultipartFile[] requestFiles = multipartFiles;
        List<FileVo> files = new ArrayList<>();

        for (MultipartFile file : requestFiles) {
            if (StringUtil.isNullOrEmpty(file.getOriginalFilename())) {
                return null;
            }
            String uuidName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            //파일 업로드
            File uploadFile = new File(attaches + uuidName);
            file.transferTo(uploadFile);

            //db저장 vo 생성 , 리스트 추가
            FileVo fileVo = FileVo.builder().dir(attaches)
                    .uuidName(UUID.randomUUID() + "_" + file.getOriginalFilename())
                    .originalName(file.getOriginalFilename())
                    .build();
            files.add(fileVo);
        };
        return files;
    }
    public void addFileList(List<FileVo> files, int articleId) {
        if (files != null) {
            files.forEach(fileVo -> {
                fileVo.setArticleId(articleId);
                int result = articleMapper.insertFile(fileVo);
            });
        }
    }

}
