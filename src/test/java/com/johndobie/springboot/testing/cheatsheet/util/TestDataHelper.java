package com.johndobie.springboot.testing.cheatsheet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johndobie.springboot.testing.cheatsheet.model.Message;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;

public class TestDataHelper {
    
    public static final String HELLO_WORLD = "Hello, World!";
    public static final String EMPTY_STRING = "";
    
    public static final String SAMPLE_TITLE_1 = "Sample Title 1";
    public static final String SAMPLE_BODY_1 = "Sample Body 1";
    public static final int USER_ID_1 = 1;
    public static final int ID_1 = 1;
    
    public static final String SAMPLE_TITLE_2 = "Sample Title 2";
    public static final String SAMPLE_BODY_2 = "Sample Body 2";
    public static final int USER_ID_2 = 2;
    public static final int ID_2 = 2;
    
    public static final long POST_ID_1 = 1L;
    public static final String POST_TEST_TITLE_1 = "Post Test Title 1";
    public static final String POST_TEST_BODY_1 = "Post Test Body 1";
    
    public static final long POST_ID_1000 = 1000L;
    public static final String POST_TEST_TITLE_1000 = "Post Test Title 1000";
    public static final String POST_TEST_BODY_1000 = "Post Test Body 1000";
    
    public static final String UPDATED_POST_TITLE = "Updated Post Title";
    
    
    public static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static Message getMessage(String content) {
        return Message.builder()
                      .content(content)
                      .build();
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
    
    public static RemotePost[] getRemotePosts() {
        return new RemotePost[]{
                new RemotePost(ID_1, SAMPLE_TITLE_1, SAMPLE_BODY_1, USER_ID_1),
                new RemotePost(ID_2, SAMPLE_TITLE_2, SAMPLE_BODY_2, USER_ID_2)
        };
    }
    
    public static Post testPostOne = Post.builder()
                   .id(POST_ID_1)
                   .title(POST_TEST_TITLE_1)
                   .body(POST_TEST_BODY_1)
                   .build();
    
    public static Post testPostOneThousand = Post.builder()
                                         .id(POST_ID_1000)
                                         .title(POST_TEST_TITLE_1000)
                                         .body(POST_TEST_BODY_1000)
                                         .build();
}
