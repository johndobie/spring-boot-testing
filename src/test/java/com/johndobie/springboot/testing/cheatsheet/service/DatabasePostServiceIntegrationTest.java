package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.exception.PostNotFoundException;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.database.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.testPostOne;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class DatabasePostServiceIntegrationTest {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private PostRepository postRepository;
    
    @Test
    public void testGetAllPosts() {
        List<Post> posts = postService.getAllPosts();
        assertThat(posts).isNotEmpty();
    }
    
    @Test
    public void testFindPostById() {
        Post post = testPostOne;
        
        post = postRepository.save(post);
        
        Post foundPost = postService.findPostById(post.getId());
        assertThat(foundPost.getId()).isEqualTo(post.getId());
        assertThat(foundPost.getBody()).isEqualTo(post.getBody());
        assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
    }
    
    @Test
    public void testSavePost() {
        Post post = testPostOne;
        
        Post savedPost = postService.savePost(post);
        
        assertThat(savedPost.getId()).isEqualTo(post.getId());
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getBody()).isEqualTo(post.getBody());
    }
    
    @Test
    public void testDeletePost() {
        Post post = testPostOne;
        
        Post savedPost = postRepository.save(post);
        postService.deletePost(savedPost.getId());
        
        assertThrows(
                PostNotFoundException.class, () -> {
                    postService.findPostById(savedPost.getId());
                });
    }
    
    @Test
    public void testFindPostByUnknownIdThrowsException() {
        Long nonExistentId = 999L;
        assertThrows(
                PostNotFoundException.class, () -> {
                    postService.findPostById(nonExistentId);
                });
    }
    
    @Test
    public void testSaveNullPostThrowsException() {
        Post nullPost = null;
        assertThrows(
                InvalidDataAccessApiUsageException.class, () -> {
                    postService.savePost(nullPost);
                });
    }
    
    @Test
    public void testDeleteUnknownPostDoesNothing() {
        Long nonExistentId = 99456767459L;
        postService.deletePost(nonExistentId);
    }
}