package com.johndobie.springboot.testing.cheatsheet.client;

import com.johndobie.springboot.testing.cheatsheet.remote.client.PlaceholderClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlaceholderClientIntegrationTest {
    
    @Autowired
    private PlaceholderClient placeholderClient;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Test
    public void testGetPosts() {
        List<RemotePost> remotePosts = placeholderClient.getPosts();
        assertThat(remotePosts).isNotEmpty();
        assertThat(remotePosts.get(0).getId()).isNotNull();
        assertThat(remotePosts.get(0).getTitle()).isNotNull();
        assertThat(remotePosts.get(0).getBody()).isNotNull();
        assertThat(remotePosts.get(0).getUserId()).isNotNull();
    }
}
