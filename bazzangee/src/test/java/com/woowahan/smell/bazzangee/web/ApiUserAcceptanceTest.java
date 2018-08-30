package com.woowahan.smell.bazzangee.web;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.UserJoinDto;
import com.woowahan.smell.bazzangee.dto.UserLoginDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


public class ApiUserAcceptanceTest extends AcceptanceTest {
    private UserLoginDto userLoginDto;

    @Test
    public void 로그인_테스트() {
        UserJoinDto newUser = new UserJoinDto("gusdk765600@naver.com", "1234qwer!", "1234qwer!", "권현아", "01040908370", LocalDate.parse("1995-08-25"));
        template().postForEntity("/api/users", newUser, User.class);

        ResponseEntity<Void> response = template().postForEntity("/api/users/login", newUser, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}