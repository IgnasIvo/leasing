package com.example.leasing.service.mapper.json;

import com.example.leasing.exception.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;

/***
 *
 * @author Ignas Ivoska
 *
 */
class JsonMappingException extends ServerErrorException {

    JsonMappingException(JsonProcessingException exception) {
        super(exception.getMessage());
    }

}
