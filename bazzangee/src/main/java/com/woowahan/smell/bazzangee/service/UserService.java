package com.woowahan.smell.bazzangee.service;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.UserJoinDto;
import com.woowahan.smell.bazzangee.dto.UserLoginDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import com.woowahan.smell.bazzangee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(UserJoinDto userJoinDto) {
        return userRepository.save(userJoinDto.toUser(passwordEncoder.encode(userJoinDto.getPassword())));
    }

    public User login(UserLoginDto userLoginDto) {
        User savedUser = userRepository.findByUserId(
                userLoginDto.getUserId()).orElseThrow(()
                -> new UnAuthenticationException("해당 ID를 가진 사용자가 존재하지 않습니다."));
        savedUser.matchPasswordBy(userLoginDto, passwordEncoder);
        return savedUser;
    }

    public boolean isCreatedUser(User user) {
        return userRepository.findByUserId(user.getUserId()).isPresent();
    }

    public void createKakaoUser(User kakaoUser) {
        userRepository.save(kakaoUser);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }
}
