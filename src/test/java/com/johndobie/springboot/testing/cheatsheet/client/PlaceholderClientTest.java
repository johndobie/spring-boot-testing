package com.johndobie.springboot.testing.cheatsheet.client;

import com.johndobie.springboot.testing.cheatsheet.remote.client.PlaceholderClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class PlaceholderClientTest {
    
    @Autowired
    private PlaceholderClient placeholderClient;
    
    @MockitoBean
    private RestTemplate restTemplate;
    
    @Test
    public void testGetPosts() {
        RemotePost[] mockRemotePosts = {
                new RemotePost(1, "Sample Title 1", "Sample Body 1", 1),
                new RemotePost(2, "Sample Title 2", "Sample Body 2", 2)
        };
        
        given(restTemplate.getForObject(anyString(), Mockito.<Class<RemotePost[]>>any()))
                .willReturn(mockRemotePosts);
        
        List<RemotePost> remotePosts = placeholderClient.getPosts();
        assertThat(remotePosts).isNotEmpty();
        assertThat(remotePosts.get(0).getId()).isNotNull();
        assertThat(remotePosts.get(0).getTitle()).isNotNull();
        assertThat(remotePosts.get(0).getBody()).isNotNull();
        assertThat(remotePosts.get(0).getUserId()).isNotNull();
    }
}
