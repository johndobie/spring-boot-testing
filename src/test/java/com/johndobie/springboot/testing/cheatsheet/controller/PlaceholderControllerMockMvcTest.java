package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.service.PlaceholderService;
import com.johndobie.springboot.testing.cheatsheet.util.MockMvcBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static com.johndobie.springboot.testing.cheatsheet.controller.PlaceholderController.POSTS_GET_ENDPOINT;
import static org.mockito.Mockito.when;

@WebMvcTest(PlaceholderController.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class PlaceholderControllerMockMvcTest extends MockMvcBaseTest {
   
    @MockitoBean
    private PlaceholderService placeholderService;
    
    @Test
    public void testGetPosts() throws Exception {
        when(placeholderService.getPosts()).thenReturn(Collections.emptyList());
        
        mockMvc.perform(MockMvcRequestBuilders.get(POSTS_GET_ENDPOINT))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
               .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }
}