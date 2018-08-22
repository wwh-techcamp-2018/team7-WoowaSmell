package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.aws.S3Uploader;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.ReviewRequestDto;
import com.woowahan.smell.bazzangee.dto.ReviewResponseDto;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.service.ReviewService;
import com.woowahan.smell.bazzangee.utils.FileUtils;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import com.woowahan.smell.bazzangee.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.woowahan.smell.bazzangee.utils.HttpSessionUtils.getUserFromSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ApiReviewController {

    private final S3Uploader s3Uploader;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<List> getReviewList(PageVO pageVO, Long filterId) {
        Pageable pageable = pageVO.makePageable(Sort.Direction.DESC.ordinal(), "writtenTime");
        if (filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsOrderByWrittenTime(pageable));
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsOrderByStarPoint(pageable));
    }

    @GetMapping("/categories")
    public ResponseEntity<List> getReviewListByCategory(PageVO pageVO, Long categoryId, Long filterId) {
        Pageable pageable = pageVO.makePageable(Sort.Direction.DESC.ordinal(), "writtenTime");
        if (filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsByCategoryOrderByWrittenTime(pageable, categoryId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsByCategoryOrderByStarPoint(pageable, categoryId));
    }

    @GetMapping("/user")
    public ResponseEntity<List> getReviewListOfUser(PageVO pageVO, Long filterId, HttpSession session) {
        Pageable pageable = pageVO.makePageable(Sort.Direction.DESC.ordinal(), "writtenTime");
        User user = getUserFromSession(session);
        if (filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsOrderByWrittenTime(pageable));
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsOrderByStarPoint(pageable));
    }

    @GetMapping("/user/categories")
    public ResponseEntity<List> getReviewListOfUserByCategory(PageVO pageVO, Long categoryId, Long filterId, HttpSession session) {
        Pageable pageable = pageVO.makePageable(Sort.Direction.DESC.ordinal(), "writtenTime");
        User user = getUserFromSession(session);
        if (filterId == 0) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsByCategoryOrderByWrittenTime(pageable, categoryId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getListsByCategoryOrderByStarPoint(pageable, categoryId));
    }

    @GetMapping("/{id}/good")
    public ResponseEntity<ReviewResponseDto> addGood(@PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLoginUser(httpSession))
            throw new UnAuthenticationException("로그인 후 이용 가능합니다.");
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateGood(id, getUserFromSession(httpSession)));
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> create(ReviewRequestDto reviewRequestDto, HttpSession session) throws IOException {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 등록 가능합니다.");
        String url = s3Uploader.upload(reviewRequestDto.getImage(), String.format("static/reviewImage/%s", LocalDate.now().toString().replace("-", "")));
        reviewService.create(reviewRequestDto, url, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 삭제 가능합니다.");
        reviewService.delete(id, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, ReviewRequestDto reviewRequestDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            throw new UnAuthenticationException("로그인 사용자만 수정 가능합니다.");
        reviewService.update(id, reviewRequestDto, HttpSessionUtils.getUserFromSession(session));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/upload")
    public JSONObject upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && multipartFile.getSize() > FileUtils.MAXIMUM_SIZE_MB)
            throw new ValidationException("업로드 가능한 이미지 최대 크기는 10MB 이상입니다.");

        // JSONObject 사용
        JSONObject jsonObject = new JSONObject();
        String url = s3Uploader.upload(multipartFile, String.format("static/tempImage/%s", LocalDate.now().toString().replace("-", "")));
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
