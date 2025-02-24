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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static com.johndobie.springboot.testing.cheatsheet.controller.PostController.POSTS_GET_ALL_ENDPOINT;
import static com.johndobie.springboot.testing.cheatsheet.controller.PostController.POSTS_GET_BY_TITLE_ENDPOINT;
import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.*;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        
        mockMvc.perform(get(POSTS_GET_ALL_ENDPOINT))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }
    
    @Test
    public void testGetPostById() throws Exception {
        when(postService.findPostById(1L)).thenReturn(testPostOne);
        
        MvcResult mvcResult = mockMvc.perform(get("/api/1"))
                                     .andExpect(MockMvcResultMatchers.status().isOk())git
                                     .andReturn();
        
        Post savedPost = readJsonAsObject(mvcResult.getResponse().getContentAsString(), Post.class);
        assertThat(savedPost).isEqualTo(testPostOne);
        
    }
    
    @Test
    public void testCreatePost() throws Exception {
        Post post = testPostOne;
        when(postService.savePost(any())).thenReturn(post);
        
        MvcResult mvcResult = mockMvc.perform(post("/api/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(getJsonObjectAsString(post)))
                                      .andReturn();
        
        Post savedPost = readJsonAsObject(mvcResult.getResponse().getContentAsString(), Post.class);
        assertThat(savedPost).isEqualTo(testPostOne);
    }
    
    @Test
    public void testUpdatePost() throws Exception {
        Post post = testPostOne;
        when(postService.savePost(post)).thenReturn(post);
        
        mockMvc.perform(put("/api")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(getJsonObjectAsString(post)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(post.getId()))
               .andExpect(jsonPath("$.title").value(post.getTitle()))
               .andExpect(jsonPath("$.body").value(post.getBody()));
    }
    
    @Test
    public void testDeletePost() throws Exception {
        mockMvc.perform(delete("/api/1"))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testGetPostsByContentContaining() throws Exception {
        Post post = testPostOne;
        when(postService.findPostByBodyContaining("Test")).thenReturn(Collections.singletonList(post));
        
        mockMvc.perform(get("/api/content")
                                              .param("keyword", "Test"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].id").value(testPostOne.getId()))
               .andExpect(jsonPath("$[0].title").value(testPostOne.getTitle()))
               .andExpect(jsonPath("$[0].body").value(testPostOne.getBody()));
    }
    
    @Test
    public void testGetPostsByTitle() throws Exception {
        Post post = testPostOne;
        when(postService.findPostByTitle("Test Title")).thenReturn(post);
        
        mockMvc.perform(get(POSTS_GET_BY_TITLE_ENDPOINT)
                                              .param("title", "Test Title"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(jsonPath("$.id").value(testPostOne.getId()))
               .andExpect(jsonPath("$.title").value(testPostOne.getTitle()))
               .andExpect(jsonPath("$.body").value(testPostOne.getBody()));
    }
    
    @Test
    public void testGetPostByIdNotFound() throws Exception {
        when(postService.findPostById(1L)).thenThrow(new PostNotFoundException("Post not found with id 1"));
        
        mockMvc.perform(get("/api/1"))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andExpect(jsonPath("$.errors[0].detail").value("Post not found with id 1"));
    }
}