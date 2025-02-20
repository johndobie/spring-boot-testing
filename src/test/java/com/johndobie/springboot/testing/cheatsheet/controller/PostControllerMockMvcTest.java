package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.exception.PostNotFoundException;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static com.johndobie.springboot.testing.cheatsheet.controller.PostController.POSTS_GET_ENDPOINT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@AutoConfigureMockMvc(printOnlyOnFailure = false)
@WebMvcTest(PostController.class)
public class PostControllerMockMvcTest {
   
    @MockitoBean
    private PostService postService;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testGetPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(Collections.emptyList());
        
        mockMvc.perform(MockMvcRequestBuilders.get(POSTS_GET_ENDPOINT))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }
    
    @Test
    public void testGetPostById() throws Exception {
        Post post = new Post(1L, "Test Title", "Test Body");
        when(postService.findPostById(1L)).thenReturn(post);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Test Title"))
               .andExpect(jsonPath("$.body").value("Test Body"));
    }
    
    @Test
    public void testCreatePost() throws Exception {
        Post post = new Post(null, "New Post", "New Body");
        Post savedPost = new Post(1L, "New Post", "New Body");
        when(postService.savePost(post)).thenReturn(savedPost);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("{\"title\": \"New Post\", \"body\": \"New Body\"}"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("New Post"))
               .andExpect(jsonPath("$.body").value("New Body"));
    }
    
    @Test
    public void testUpdatePost() throws Exception {
        Post post = new Post(1L, "Updated Post", "Updated Body");
        when(postService.savePost(post)).thenReturn(post);
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("{\"title\": \"Updated Post\", \"body\": \"Updated Body\"}"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Updated Post"))
               .andExpect(jsonPath("$.body").value("Updated Body"));
    }
    
    @Test
    public void testDeletePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/1"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testGetPostsByContentContaining() throws Exception {
        Post post = new Post(1L, "Test Title", "Test Body");
        when(postService.findPostByBodyContaining("Test")).thenReturn(Collections.singletonList(post));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content")
                                              .param("keyword", "Test"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].id").value(1L))
               .andExpect(jsonPath("$[0].title").value("Test Title"))
               .andExpect(jsonPath("$[0].body").value("Test Body"));
    }
    
    @Test
    public void testGetPostsByTitle() throws Exception {
        Post post = new Post(1L, "Test Title", "Test Body");
        when(postService.findPostByTitle("Test Title")).thenReturn(post);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/title")
                                              .param("title", "Test Title"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Test Title"))
               .andExpect(jsonPath("$.body").value("Test Body"));
    }
    
    @Test
    public void testGetPostByIdNotFound() throws Exception {
        when(postService.findPostById(1L)).thenThrow(new PostNotFoundException("Post not found with id 1"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andExpect(jsonPath("$.errors[0].detail").value("Post not found with id 1"));
    }
}