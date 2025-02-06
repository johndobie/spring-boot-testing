package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.util.IntegrationBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.johndobie.springboot.testing.cheatsheet.controller.PlaceholderController.POSTS_GET_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaceholderControllerIntegrationTest extends IntegrationBaseTest {

    @Test
    public void testGetPosts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = getBaseUrl() +  POSTS_GET_ENDPOINT;
        List posts = restTemplate.getForObject(url, List.class);
        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(100);
    }
}