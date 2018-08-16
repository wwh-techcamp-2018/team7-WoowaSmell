package com.woowahan.smell.bazzangee.domain;


//import lombok.extern.slf4j.Slf4j;
import com.woowahan.smell.bazzangee.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanup() {
        /**
         이후 테스트 코드에 영향을 끼치지 않기 위해
         테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         **/
        userRepository.deleteAll();
    }


    @Test
    public void 회원_가입() {
        //given
        LocalDateTime now = LocalDateTime.now();
        User savedUser = userRepository.save(
                User.builder()
                .userId("serverwizard1@naver.com")
                .password("password")
                .name("서버위저드")
                .phoneNumber("010-4231-5111")
                .build());

        //when
        User user = userRepository.findByUserId(savedUser.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        assertThat(user.getCreatedTime()).isAfter(now);
        assertThat(user.getModifiedTime()).isAfter(now);
    }
}