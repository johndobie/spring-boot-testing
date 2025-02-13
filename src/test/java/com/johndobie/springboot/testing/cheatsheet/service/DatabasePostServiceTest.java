package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.exception.PostNotFoundException;
import com.johndobie.springboot.testing.cheatsheet.mapper.PostMapper;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.client.PostClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import com.johndobie.springboot.testing.cheatsheet.database.repository.PostRepository;
import com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.testPostOne;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabasePostServiceTest {
    
    @Mock
    private PostRepository postRepository;
    
    @Mock
    private PostClient postClient;
    
    @InjectMocks
    private DatabasePostService postService;
    
    @Test
    public void testGetPosts() {
        List<RemotePost> remotePosts = List.of(TestDataHelper.getRemotePosts());
        List<Post> posts = PostMapper.INSTANCE.remotePostsToPosts(remotePosts);
        
        when(postClient.getPosts()).thenReturn(remotePosts);
        when(postRepository.saveAll(any())).thenReturn(posts);
        
        List<Post> result = postService.getAllPosts();
        assertThat(result).hasSize(posts.size());
    }
    
    @Test
    public void testFindByTitle() {
        Post post = testPostOne;
        String title = post.getTitle();
        
        when(postRepository.findByTitle(title)).thenReturn(Optional.of(post));
        
        Post result = postService.findPostByTitle(title);
        assertThat(result.getTitle()).isEqualTo(title);
    }
    
    @Test
    public void testFindById() {
        Long id = 1L;
        Post post = testPostOne;
        
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        
        Post result = postService.findPostById(id);
        assertThat(result.getId()).isEqualTo(id);
    }
    
    @Test
    public void testFindByBodyContaining() {
        String keyword = "Test";
        List<Post> posts = List.of(
                Post.builder()
                    .build(),
                Post.builder()
                    .build());
        
        when(postRepository.findByBodyContaining(keyword)).thenReturn(posts);
        
        List<Post> result = postService.findPostByBodyContaining(keyword);
        assertThat(result).hasSize(2);
    }
    
    @Test
    public void testFindByTitleAndBodyContaining() {
        String title = "Test Title";
        String keyword = "Test";
        List<Post> posts = List.of(
                Post.builder()
                    .build(),
                Post.builder()
                    .build());
        
        when(postRepository.findByTitleAndBodyContaining(title, keyword)).thenReturn(posts);
        
        List<Post> result = postService.findPostByTitleAndBodyContaining(title, keyword);
        assertThat(result).hasSize(2);
    }
    
    @Test
    public void testSavePost() {
        Post post = Post.builder()
                        .title("Test Title")
                        .body("Test Body")
                        .build();
        
        when(postRepository.save(any(Post.class))).thenReturn(post);
        
        Post result = postService.savePost(post);
        assertThat(result.getTitle()).isEqualTo("Test Title");
        assertThat(result.getBody()).isEqualTo("Test Body");
    }
    
    @Test
    public void testDeletePost() {
        Long id = 1L;
        doNothing().when(postRepository)
                   .deleteById(id);
        
        postService.deletePost(id);
        verify(postRepository, times(1)).deleteById(id);
    }
    @Test
    public void testFindByTitle_NotFound() {
        String title = "Nonexistent Title";
        
        when(postRepository.findByTitle(title)).thenReturn(Optional.empty());
        
        assertThrows(
                PostNotFoundException.class, () -> {
            postService.findPostByTitle(title);
        });
    }
    
    @Test
    public void testFindById_NotFound() {
        Long id = 999L;
        
        when(postRepository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(PostNotFoundException.class, () -> {
            postService.findPostById(id);
        });
    }
    
    @Test
    public void testFindByBodyContaining_NoMatches() {
        String keyword = "Nonexistent Keyword";
        
        when(postRepository.findByBodyContaining(keyword)).thenReturn(Collections.emptyList());
        
        List<Post> result = postService.findPostByBodyContaining(keyword);
        assertThat(result).isEmpty();
    }
    
    @Test
    public void testFindByTitleAndBodyContaining_NoMatches() {
        String title = "Nonexistent Title";
        String keyword = "Nonexistent Keyword";
        
        when(postRepository.findByTitleAndBodyContaining(title, keyword)).thenReturn(Collections.emptyList());
        
        List<Post> result = postService.findPostByTitleAndBodyContaining(title, keyword);
        assertThat(result).isEmpty();
    }
    
    @Test
    public void testSavePost_NullPost() {
        Post post = null;
        
        when(postRepository.save(any())).thenThrow(new IllegalArgumentException("Post cannot be null"));
        
        try {
            postService.savePost(post);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Post cannot be null");
        }
    }
    
    @Test
    public void testDeletePost_NotFound() {
        Long id = 999L;
        
        doThrow(new IllegalArgumentException("Post not found")).when(postRepository).deleteById(id);
        
        try {
            postService.deletePost(id);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Post not found");
        }
    }
}
