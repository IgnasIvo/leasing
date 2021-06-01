package com.example.leasing.exception.handler;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Data
class ErrorResponse {
    private final String message;
    private final List<String> details;

    ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    ErrorResponse(String message, StackTraceElement[] stackTraceElements) {
        this.message = message;
        this.details = Arrays.stream(stackTraceElements)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
    }

}
