package com.example.leasing.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

/***
 *
 * @author Ignas Ivoska
 *
 */
public class RestException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    @Getter
    private final List<String> details;

    RestException(HttpStatus status, String message, List<String> details) {
        super(message);
        this.status = status;
        this.details = details;
    }

}
