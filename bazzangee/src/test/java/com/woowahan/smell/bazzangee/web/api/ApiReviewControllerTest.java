package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.aws.S3Uploader;
import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.request.ReviewRequestDto;
import com.woowahan.smell.bazzangee.repository.contents.ReviewRepository;
import com.woowahan.smell.bazzangee.service.ReviewService;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpSession;
import support.test.AcceptanceTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiReviewControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiReviewControllerTest.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Mock
    private S3Uploader mockS3Uploader;
    @Mock
    private ReviewService mockReviewService;
    @InjectMocks
    private ApiReviewController apiReviewController;

    @Test
    public void 리뷰_등록() throws IOException {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, new User());

        ReviewRequestDto reviewRequestDto =
                ReviewRequestDto.builder()
                .orderFoodId(1L)
                .contents("맛나더라")
                .starPoint(1.0)
                .savedImageUrl("www.naver.com")
                .build();

        assertThat(HttpStatus.OK).isEqualTo(apiReviewController.create(reviewRequestDto, session).getStatusCode());
        log.info("리뷰_등록 결과 : {} ", apiReviewController.create(reviewRequestDto, session).getBody());
    }
}