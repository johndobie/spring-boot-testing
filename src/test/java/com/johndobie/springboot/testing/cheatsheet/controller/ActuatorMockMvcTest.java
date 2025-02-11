package com.johndobie.springboot.testing.cheatsheet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActuatorMockMvcTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/actuator/health").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("UP"));
    }
    
    @Test
    public void testInfoEndpoint() throws Exception {
        mockMvc.perform(get("/actuator/info").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.git.branch").exists());
    }
}
