package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.aws.S3Uploader;
import com.woowahan.smell.bazzangee.dto.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.ReviewService;
import com.woowahan.smell.bazzangee.utils.FileUtils;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ApiReviewController {

    private final int REVIEW_PAGE_NUM = 10;
    private final S3Uploader s3Uploader;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<List> getReviewList(@PageableDefault(size = REVIEW_PAGE_NUM, direction = Sort.Direction.DESC, sort = "writtenTime") Pageable pageable) {
        log.info("getReviewList : {}, {}, {}", pageable.getPageNumber(), pageable.getOffset(), pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getLists(pageable));
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> create(ReviewRequestDto reviewRequestDto, HttpSession session) throws IOException {
        log.info("reviewRequestDto : {}", reviewRequestDto);
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 등록 가능합니다.");
        String url = s3Uploader.upload(reviewRequestDto.getImage(), "static");
        log.info("url : {}", url);
        reviewService.create(reviewRequestDto, url, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 삭제 가능합니다.");
        reviewService.delete(id, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, ReviewRequestDto reviewRequestDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 수정 가능합니다.");
        log.info("reviewRequestDto : {}", reviewRequestDto.toString());
        reviewService.update(id, reviewRequestDto, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/upload")
    public JSONObject upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        log.info("multipartFile.getSize() : {}", multipartFile.getSize());

        if (multipartFile != null && multipartFile.getSize() > FileUtils.MAXIMUM_SIZE_MB)
            throw new ValidationException("업로드 가능한 이미지 최대 크기는 10MB 이상입니다.");

        // JSONObject 사용
        JSONObject jsonObject = new JSONObject();
        String url = s3Uploader.upload(multipartFile, String.format("static/%s", LocalDate.now().toString().replace("-", "")));
        System.out.println("url : {}"+ url);
        jsonObject.put("url", url);
        return jsonObject;
    }

    @GetMapping("/{id}/good")
    public ResponseEntity<ReviewResponseDto> addGood(@PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLoginUser(httpSession))
            throw new UnAuthenticationException("로그인 후 이용 가능합니다.");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateGood(id, HttpSessionUtils.getUserFromSession(httpSession)));
    }
}
