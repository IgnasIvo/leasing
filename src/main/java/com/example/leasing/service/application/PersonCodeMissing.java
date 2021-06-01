package com.example.leasing.service.application;

import com.example.leasing.exception.ServerErrorException;

/***
 *
 * @author Ignas Ivoska
 *
 */
class PersonCodeMissing extends ServerErrorException {

    PersonCodeMissing() {
        super("Person code is missing");
    }

}
