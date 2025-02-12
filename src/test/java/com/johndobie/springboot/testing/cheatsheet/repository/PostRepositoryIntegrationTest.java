package com.johndobie.springboot.testing.cheatsheet.repository;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
public class PostRepositoryIntegrationTest {
    
    @Autowired
    private PostRepository postRepository;
    
    @BeforeAll
    public static void deleteAllPosts(@Autowired PostRepository postRepository) {
        postRepository.deleteAll();
    }
    
    @Test
    public void testCreatePost() {
        Post savedPost = savePost();
        
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isEqualTo(POST_ID_1);
    }
    
    @Test
    public void testFindPostById() {
        Post savedPost = savePost();
        Long postId = savedPost.getId();
        
        Optional<Post> post = postRepository.findById(postId);
        
        assertThat(post).isPresent();
        assertThat(post.get().getId()).isEqualTo(postId);
    }
    
    @Test
    public void testFindAllPosts() {
        savePost();
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isOne();
    }
    
    @Test
    public void testUpdatePost() {

        Post savedPost = savePost();
        savedPost.setTitle(UPDATED_POST_TITLE);
        
        postRepository.save(savedPost);
            
        Post updatedPost = postRepository.findByTitle(UPDATED_POST_TITLE).orElseThrow();
        assertThat(updatedPost.getId()).isEqualTo(savedPost.getId());
        assertThat(updatedPost.getTitle()).isEqualTo(UPDATED_POST_TITLE);
    }
    
    @Test
    public void testDeletePost() {
        
        Post savedPost = savePost();

        postRepository.deleteById(savedPost.getId());
        
        Optional<Post> deletedPost = postRepository.findById(savedPost.getId());
        assertThat(deletedPost.isPresent()).isFalse();
    }
    
    private Post savePost() {
        Post post = new Post(POST_ID_1, POST_TEST_TITLE_1, POST_TEST_BODY_1);
        return postRepository.save(post);
    }
}