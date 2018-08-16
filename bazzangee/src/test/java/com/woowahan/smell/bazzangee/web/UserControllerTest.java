package com.woowahan.smell.bazzangee.web;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.UserJoinDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserControllerTest extends AcceptanceTest {

    @Test
    public void create() {
        UserJoinDto newUser = new UserJoinDto("gusdk7656@naver.com", "1234qwer!", "1234qwer!", "권현아", "01040908370", LocalDate.parse("1995-08-25"));
        ResponseEntity response = template().postForEntity("/api/users", newUser, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}