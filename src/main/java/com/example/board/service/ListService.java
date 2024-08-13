
package com.example.board.service;

import com.example.board.dto.Articles;
import com.example.board.dto.Search;
import com.example.board.mapper.ArticleMapper;
import com.example.board.util.Paging;
import com.example.board.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListService {
    ArticleMapper articleMapper;
    public ListService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public Model getArticleList(Search search, Model model) {
        //빈값일 시 기본 검색 값 설정
        search.setPageNum(defaultPageNum(search.getPageNum()));
        search.setStartDate(defaultStartDate(search.getStartDate()));
        search.setEndDate(defaultEndDate(search.getEndDate()));
        System.out.println(search.toString());

        //옵션이 포함된 게시글 카운트
        int articleCountByOption = articleMapper.countArticleOption(search);

        //페이징 빌드
        Paging paging = Paging.builder()
                        .currentPage(search.getPageNum())
                        .totalCount(articleCountByOption)
                        .build();

        //옵션으로 게시글 가지고오기
        Map<String, Object> map = new HashMap<>();
        map.put("search", search);
        map.put("paging", paging);
        List<Articles> articles = articleMapper.selectAllArticle(map);

        model.addAttribute("paging", paging);
        model.addAttribute("articles", articles);
        return model;
    }

    String defaultStartDate(String startDate) {
        if (StringUtil.isNullOrEmpty(startDate)) {
            return LocalDate.now().minusYears(1).toString();
        }
        return startDate;
    }
    String defaultEndDate(String endDate) {
        if (StringUtil.isNullOrEmpty(endDate)) {
            return LocalDate.now().toString();
        }
        return endDate;
    }
    int defaultPageNum(int pageNum) {
        if (pageNum == 0) {
            return 1;
        } else {
            return pageNum;
        }
    }

}
