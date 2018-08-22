package com.woowahan.smell.bazzangee.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;

public class PageVO {
    private static final int DEFAULT_SIZE = 10;

    private int size;
    private int page;

    public PageVO() {
        this.size = DEFAULT_SIZE;
    }

    public void setSize(int size) {
        this.size = DEFAULT_SIZE;
    }

    public void setPage(int page) {
        page = page < 0 ? 1 : page;
    }

    public Pageable makePageable(int direction) {
        Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
        return (Pageable) PageRequest.of(this.page-1, this.size, dir);

    }
}
