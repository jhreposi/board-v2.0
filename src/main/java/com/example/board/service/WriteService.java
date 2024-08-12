package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Article;
import com.example.board.model.FileVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

public class WriteService implements HttpService {
    ArticleMapper articleMapper;

    public WriteService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public ServiceResult doService(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals("GET")) {
            return new ServiceResult("dispatcher","write.jsp",request,response);
        }
        else if (request.getMethod().equals("POST")){
            int addedArticleId = articleAdd(request);
            if (isMultipartFile(request)) {
                try {
                    FileVo fileVo = fileService(request);
                    fileAdd(fileVo, addedArticleId);
                } catch (ServletException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return new ServiceResult("redirect", "/board/list",request,response);
        }
        return null;
    }
    int articleAdd(HttpServletRequest request) {
        Article article = new Article();
        article.setCategoryId(Integer.parseInt(request.getParameter("category")));
        article.setAuthor(request.getParameter("author"));
        article.setTitle(request.getParameter("title"));
        article.setContent(request.getParameter("content"));
        article.setPassword(request.getParameter("password"));
        int result = articleMapper.insertArticle(article);

        return article.getId();
    }
    
    FileVo fileService(HttpServletRequest request) throws ServletException, IOException {
        FileVo fileVo = new FileVo();
        Collection<Part> parts = request.getParts();
        String attacheDir = "E:\\attaches"; //프로퍼티화

        String uploadDir = attacheDir + File.separator;
        File uploadDirFile = new File(uploadDir);

        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }
        for (Part part : parts) {
            //todo 조건 유틸파일로 재정의하기
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                String originalName = part.getSubmittedFileName();
                String uuidName = UUID.randomUUID() + "_" + originalName;

                File file = new
                        File(uploadDir + uuidName);
                try (InputStream inputStream = part.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                fileVo.setUuidName(uuidName);
                fileVo.setOriginalName(originalName);
                fileVo.setDir(uploadDir);
            }
        }
        return fileVo;
    }

    void fileAdd(FileVo fileVo, int articleId) {
        if (fileVo.getOriginalName() == null) {
            return;// TODO: 2024-08-08 파일이 없을 떄 fileService도 실행 안하고 스킵하는 좋은방법은? 
        }
        fileVo.setArticleId(articleId);
        int result = articleMapper.insertFile(fileVo);
    }

    Boolean isMultipartFile(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart/");
    }

}
