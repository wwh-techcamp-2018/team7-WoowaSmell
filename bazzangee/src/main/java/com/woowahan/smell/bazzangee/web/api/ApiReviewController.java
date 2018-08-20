package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ApiReviewController {

    private final int REVIEW_PAGE_NUM = 10;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<List> getReviewList(@PageableDefault(size=REVIEW_PAGE_NUM, direction = Sort.Direction.DESC, sort="writtenTime") Pageable pageable) {
        log.info("getReviewList : {}, {}, {}", pageable.getPageNumber(), pageable.getOffset(), pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getLists(pageable));
    }
}
