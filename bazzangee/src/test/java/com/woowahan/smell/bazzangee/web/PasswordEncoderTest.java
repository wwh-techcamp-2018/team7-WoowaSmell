package com.woowahan.smell.bazzangee.web;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void 비밀번호_인코딩_학습_테스트() {
        System.out.println(passwordEncoder.encode("password"));
    }
}
