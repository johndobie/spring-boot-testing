package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static com.johndobie.springboot.testing.cheatsheet.controller.PostController.POSTS_GET_ENDPOINT;
import static org.mockito.Mockito.when;


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
}