package com.example.leasing.exception;

import org.springframework.http.HttpStatus;

import static java.util.Collections.emptyList;

/***
 *
 * @author Ignas Ivoska
 *
 */
public class ServerErrorException extends RestException {

    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, emptyList());
    }

}
