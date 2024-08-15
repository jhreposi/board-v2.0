package com.example.board.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
    int currentPage = 0;
    int totalCount = 0;
    int dataPerPage = 10;
    int lastPage = 0;

    int limit = 0;
    int offset = 0;
    @Builder
    public Paging(int currentPage, int totalCount) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.lastPage = this.totalCount / this.dataPerPage
                + (this.totalCount % this.dataPerPage > 0 ? 1 : 0);
        this.offset = calOffset();
        this.limit = this.dataPerPage;
    }

    public Paging(int currentPage, int totalCount, int dataPerPage) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.dataPerPage = dataPerPage;
        this.lastPage = this.totalCount / this.dataPerPage
                + (this.totalCount % this.dataPerPage > 0 ? 1 : 0);
        this.offset = calOffset();
        this.limit = this.dataPerPage;
    }

    int calOffset() {
        return (this.currentPage -1) * this.dataPerPage;
    }

}
