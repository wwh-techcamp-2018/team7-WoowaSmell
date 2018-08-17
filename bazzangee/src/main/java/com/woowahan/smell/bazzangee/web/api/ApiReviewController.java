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
import com.woowahan.smell.bazzangee.dto.ReviewDto;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @PostMapping("")
    public ResponseEntity<Void> create(ReviewDto reviewDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 등록 가능합니다.");
        reviewService.create(reviewDto, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 삭제 가능합니다.");
        reviewService.delete(id, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, ReviewDto reviewDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 수정 가능합니다.");
        reviewService.update(id, reviewDto, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
