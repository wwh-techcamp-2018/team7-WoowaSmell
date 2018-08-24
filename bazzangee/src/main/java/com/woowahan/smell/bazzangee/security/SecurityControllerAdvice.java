package com.woowahan.smell.bazzangee.security;

import com.woowahan.smell.bazzangee.exception.BadRequestException;
import com.woowahan.smell.bazzangee.exception.ErrorResponse;
import com.woowahan.smell.bazzangee.exception.NotMatchException;
import com.woowahan.smell.bazzangee.exception.UnAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@Slf4j
@RestControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> unAuthentication(Exception exception) {
        log.debug("UnAuthenticationException is happened!");
        return new ResponseEntity<>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> notMatch(Exception exception) {
        log.debug("NotMatchException is happened!");
        return new ResponseEntity<>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> notAllowedArgument(Exception exception) {
        log.debug("ValidationException is happened!");
        return new ResponseEntity<>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequest(Exception exception) {
        log.debug("BadRequestException is happened!");
        return new ResponseEntity<>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> IllegalArgument(Exception exception) {
        log.debug("IllegalArgumentException is happened!");
        return new ResponseEntity<>(ErrorResponse.ofString(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
