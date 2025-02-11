package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.client.PlaceholderClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = JsonPlaceholderService.class)
public class PlaceholderServiceTest {
    
    @MockitoBean
    private PlaceholderClient placeholderClient;
    
    @Autowired
    private PlaceholderService placeholderService;
    
    @Test
    public void testGetPosts() {
        RemotePost[] remotePosts = TestDataHelper.getRemotePosts();
        given(placeholderClient.getPosts()).willReturn(Arrays.asList(remotePosts));
        
        List<Post> posts = placeholderService.getPosts();
        
        assertThat(posts).isNotEmpty();
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts.get(0).getTitle()).isEqualTo(TestDataHelper.SAMPLE_TITLE_1);
    }
    
    @Test
    public void testGetPostsWhenClientThrowsException() {

        given(placeholderClient.getPosts()).willThrow(new RuntimeException("Error occurred"));
        
        assertThrows(RuntimeException.class, () -> {
            placeholderService.getPosts();
        });
    }
}
