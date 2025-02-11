package com.johndobie.springboot.testing.cheatsheet.client;

import com.johndobie.springboot.testing.cheatsheet.remote.client.PlaceholderClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@WebMvcTest(PlaceholderClient.class)
@AutoConfigureMockMvc
public class PlaceholderClientTest {
    
    @Autowired
    private PlaceholderClient placeholderClient;
    
    @MockitoBean
    private RestTemplate restTemplate;
    
    @Test
    public void testGetPosts() {
        
        given(restTemplate.getForObject(anyString(), Mockito.<Class<RemotePost[]>>any()))
                .willReturn(TestDataHelper.getRemotePosts());
        
        List<RemotePost> remotePosts = placeholderClient.getPosts();
        assertThat(remotePosts).isNotEmpty();
        
        RemotePost remotePost = remotePosts.get(0);
        
        assertThat(remotePost.getId()).isEqualTo(ID_1);
        assertThat(remotePost.getTitle()).isEqualTo(SAMPLE_TITLE_1);
        assertThat(remotePost.getBody()).isEqualTo(SAMPLE_BODY_1);
    }
    
    @Test
    public void testGetPostsThrowsException() {
        given(restTemplate.getForObject(anyString(), Mockito.<Class<RemotePost[]>>any()))
                .willThrow(new RestClientException("Error occurred"));
        
        assertThrows(RestClientException.class, () -> {
            placeholderClient.getPosts();
        });
    }
}
