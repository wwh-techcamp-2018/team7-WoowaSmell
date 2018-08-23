package com.woowahan.smell.bazzangee.vo;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class PageVO {
    private static final int DEFAULT_SIZE = 10;

    private int size;
    private int page;

    public PageVO() {
        this.page = 1;
        this.size = DEFAULT_SIZE;
    }

    public void setSize(int size) {
        this.size = DEFAULT_SIZE;
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public Pageable makePageable(int direction, String... props) {
        Sort.Direction dir = direction == 0 ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(this.page - 1, this.size, dir, props);
    }
}
