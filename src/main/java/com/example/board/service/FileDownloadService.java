package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.FileVo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FileDownloadService {
    ArticleMapper articleMapper;

    public FileDownloadService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public ResponseEntity<Resource> fileDownloadService(int fileId) {
        FileVo fileInfo = articleMapper.selectFile(fileId);
        String filePath = fileInfo.getDir() + fileInfo.getUuidName();
        String fileName = null;

        try {
            fileName = fileNameEncoder(fileInfo.getOriginalName());
            File downloadFile = new File(filePath);
            Resource resource = new FileSystemResource(downloadFile);

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION , "attachment; filename=\"" + fileName + "\"")
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    String fileNameEncoder(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20");
    }
}
