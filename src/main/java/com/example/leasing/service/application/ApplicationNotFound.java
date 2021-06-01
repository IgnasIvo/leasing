package com.example.leasing.service.application;

import com.example.leasing.exception.ResourceNotFoundException;

import static java.lang.String.format;

/***
 *
 * @author Ignas Ivoska
 *
 */
class ApplicationNotFound extends ResourceNotFoundException {

    ApplicationNotFound(String reference) {
        super(format("Application not found by reference %s", reference));
    }

}
