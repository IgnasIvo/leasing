package com.example.leasing.service.mapper.json;

import com.example.leasing.service.mapper.MapperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Service
class JsonMapper implements MapperService {

    private final ObjectMapper objectMapper;

    @Autowired
    JsonMapper() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T> T toObject(String string, Class<T> type) {
        try {
            return objectMapper.readValue(string, type);
        } catch (JsonProcessingException exception) {
            throw new JsonMappingException(exception);
        }
    }

    @Override
    public String toString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new JsonMappingException(exception);
        }
    }

}
