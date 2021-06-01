package com.example.leasing.exception.handler;

import com.example.leasing.exception.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ControllerAdvice
class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler
    final ResponseEntity<ErrorResponse> handleApplicationExceptions(RestException exception) {
        LOGGER.error("REST Exception occurred", exception);
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getDetails());
        return new ResponseEntity<>(response, exception.getStatus());
    }

    @ExceptionHandler
    final ResponseEntity<ErrorResponse> handleAnyOtherExceptions(RuntimeException exception) {
        LOGGER.error("Runtime Exception occurred", exception);
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getStackTrace());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
