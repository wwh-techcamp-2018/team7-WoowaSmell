package com.woowahan.smell.bazzangee.security;

import com.woowahan.smell.bazzangee.exception.ErrorResponse;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> unAuthentication(Exception exception) {
        log.debug("UnAuthenticationException is happened!");
        return new ResponseEntity<ErrorResponse>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> notMatch(Exception exception) {
        log.debug("NotMatchException is happened!");
        return new ResponseEntity<ErrorResponse>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
