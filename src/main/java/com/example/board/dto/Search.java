package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Search {
    private int pageNum;
    private String startDate;
    private String endDate;
    private String category;
    private String keyword;
}
