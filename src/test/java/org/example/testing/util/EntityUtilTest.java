package org.example.testing.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityUtilTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "000000",
            "000001"
    })
    void itShouldValidateCode(String code){
        assertTrue(EntityUtil.isValidCode(code));
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "John",
            "Bobster"
    })
    void itShouldValidateName(String name){
        assertTrue(EntityUtil.isValidName(name));
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "something",
            "not something"
    })
    void itShouldValidateDescription(String description){
        assertTrue(EntityUtil.isValidDescription(description));
    }
}