package com.johndobie.springboot.testing.cheatsheet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johndobie.springboot.testing.cheatsheet.model.Message;

public class TestDataHelper {

    public static final String HELLO_WORLD = "Hello, World!";
    public static final String EMPTY_STRING = "";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static Message getMessage(String content) {
        return Message.builder().content(content).build();
    }

    public static String getJsonObjectAsString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJsonAsObject(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
