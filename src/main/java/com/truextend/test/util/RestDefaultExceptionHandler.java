package com.truextend.test.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleException(Exception ex) {
        log.error(RestConstant.RESPONSE_SERVER_ERROR, ex);
        return new ResponseEntity<>(ErrorMessage.builder().message(RestConstant.RESPONSE_SERVER_ERROR).details(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
