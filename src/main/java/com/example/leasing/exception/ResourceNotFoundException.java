package com.example.leasing.exception;

import org.springframework.http.HttpStatus;

import static java.util.Collections.emptyList;

/***
 *
 * @author Ignas Ivoska
 *
 */
public class ResourceNotFoundException extends RestException {

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message, emptyList());
    }

}

