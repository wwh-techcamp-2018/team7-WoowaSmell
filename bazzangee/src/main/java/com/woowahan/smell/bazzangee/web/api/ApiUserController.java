package com.woowahan.smell.bazzangee.web.api;

import com.woowahan.smell.bazzangee.domain.User;
import com.woowahan.smell.bazzangee.dto.UserJoinDto;
import com.woowahan.smell.bazzangee.dto.UserLoginDto;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.service.UserService;
import com.woowahan.smell.bazzangee.utils.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody UserJoinDto userJoinDto) throws NotMatchException {
        log.debug("UserController create : {}", userJoinDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userJoinDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginDto userLoginDto, HttpSession session) {
      log.info("params: {}", userLoginDto);
      HttpSessionUtils.setUserInSession(session, userService.login(userLoginDto));
      return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        HttpSessionUtils.removeUserInSession(session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}