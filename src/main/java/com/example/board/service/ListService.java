
package com.example.board.service;

import com.example.board.dto.Articles;
import com.example.board.dto.Search;
import com.example.board.mapper.ArticleMapper;
import com.example.board.model.Article;
import com.example.board.util.Paging;
import com.example.board.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListService implements HttpService {
    ArticleMapper articleMapper;

    public ListService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public ServiceResult doService(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = optionToMap(request);

        int pageNum = Paging.defaultPageNum(request.getParameter("pageNum"));
        int totalArticle = articleMapper.countArticleOption(map);
        Paging paging = new Paging(pageNum, totalArticle, 5);
        map.put("paging", paging);

        List<Articles> articlesList = articleMapper.selectAllArticle(map);
        request.setAttribute("page", paging);
        request.setAttribute("articles", articlesList);

        return new ServiceResult("dispatcher","list.jsp",request,response);
    }
    public List<Article> getArticleList(Search search) {
        search.setPageNum(defaultPageNum(search.getPageNum()));
        search.setStartDate(defaultStartDate(search.getStartDate()));
        search.setEndDate(defaultEndDate(search.getEndDate()));

        System.out.println(search.toString());
        return articleMapper.selectArticles();
    }
    Map<String, Object> optionToMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");
        map.put("startDate", defaultStartDate(start));
        map.put("endDate", defaultEndDate(end));
        map.put("category", category);
        map.put("keyword", keyword);
        return map;
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
