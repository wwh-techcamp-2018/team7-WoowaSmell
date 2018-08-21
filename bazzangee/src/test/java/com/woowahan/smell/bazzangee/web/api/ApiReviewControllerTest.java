package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.dto.UserJoinDto;
import com.woowahan.smell.bazzangee.repository.ReviewRepository;
import com.woowahan.smell.bazzangee.web.HtmlFormDataBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiReviewControllerTest extends AcceptanceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void 리뷰_생성() {
        HttpEntity<MultiValueMap<String, Object>> requestCreate = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("contents", "컨텐츠입니다.123")
                .addParameter("image", new ClassPathResource("static/img/baezzangee.png"))
                .addParameter("starPoint", 4.5)
                .build();
        ResponseEntity<Void> response = basicAuthTemplate().postForEntity("/api/reviews", requestCreate, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }

    @Test
    public void 리뷰_삭제() {
        HttpEntity<MultiValueMap<String, Object>> requestCreate = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("contents", "컨텐츠입니다.123")
                .addParameter("image", new ClassPathResource("static/img/baezzangee.png"))
                .addParameter("starPoint", 4.5)
                .build();
        ResponseEntity<Void> response = basicAuthTemplate().postForEntity("/api/reviews", requestCreate, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ResponseEntity<Void> response1 = basicAuthTemplate().exchange(
                "/api/reviews/1",
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                Void.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 리뷰_수정() {
        HttpEntity<MultiValueMap<String, Object>> requestCreate = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("contents", "컨텐츠입니다.123")
                .addParameter("image", new ClassPathResource("static/img/baezzangee.png"))
                .addParameter("starPoint", 4.5)
                .build();
        ResponseEntity<Void> response = basicAuthTemplate().exchange("/api/reviews/1", HttpMethod.PUT, requestCreate, Void.class);
        assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
    }
}