package com.example.leasing.service.mapper.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/***
 *
 * @author Ignas Ivoska
 *
 */
class JsonMapperTest {
    private static final String JSON_REPRESENTATION = "{\"fieldOne\":\"one\",\"fieldTwo\":0}";

    private final JsonMapper jsonMapper = new JsonMapper();

    @Test
    void convertsToString() {
        assertThat(jsonMapper.toString(new TestClass("one", BigDecimal.ZERO)))
                .isEqualTo(JSON_REPRESENTATION);
    }

    @Test
    void convertsFromString() {
        assertThat(jsonMapper.toObject(JSON_REPRESENTATION, TestClass.class))
                .isEqualTo(new TestClass("one", BigDecimal.ZERO));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TestClass {
        private String fieldOne;
        private BigDecimal fieldTwo;
    }

}