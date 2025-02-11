package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.exception.ErrorModel;
import com.johndobie.springboot.testing.cheatsheet.exception.ErrorResponseModel;
import com.johndobie.springboot.testing.cheatsheet.exception.ErrorType;
import com.johndobie.springboot.testing.cheatsheet.model.Message;
import com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.johndobie.springboot.testing.cheatsheet.controller.EchoController.ECHO_POST_ENDPOINT;
import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EchoController.class)
@AutoConfigureMockMvc
public class EchoControllerMockMvcTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testEcho() throws Exception {
        Message message = getMessage(HELLO_WORLD);
        
        String jsonMessage = TestDataHelper.getJsonObjectAsString(message);
        
        mockMvc.perform(post(ECHO_POST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                                               .content(jsonMessage))
               .andExpect(status().isOk())
               .andExpect(content().json(jsonMessage));
    }
    
    @Test
    public void testEchoEmptyContent() throws Exception {
        Message message = Message.builder()
                                 .content(EMPTY_STRING)
                                 .build();
        
        String jsonMessage = TestDataHelper.getJsonObjectAsString(message);
        
        mockMvc.perform(post(ECHO_POST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                                               .content(jsonMessage))
               .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testEchoNullContent() throws Exception {
        Message message = Message.builder()
                                 .content(null)
                                 .build();
        
        String jsonMessage = TestDataHelper.getJsonObjectAsString(message);
        
        mockMvc.perform(post(ECHO_POST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                                               .content(jsonMessage))
               .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testEchoSpecialCharacters() throws Exception {
        Message message = Message.builder()
                                 .content("!@#$%^&*()_+")
                                 .build();
        
        String jsonMessage = TestDataHelper.getJsonObjectAsString(message);
        
        mockMvc.perform(post(ECHO_POST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                                               .content(jsonMessage))
               .andExpect(status().isOk())
               .andExpect(content().json(jsonMessage));
    }
    
    @Test
    public void testEchoLongContentThrowsAnError() throws Exception {
        String longContent = "a".repeat(50);
        Message message = TestDataHelper.getMessage(longContent);
        
        String jsonMessage = TestDataHelper.getJsonObjectAsString(message);
        
        MvcResult mvcResult = mockMvc.perform(post(ECHO_POST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                                                                     .content(jsonMessage))
                                     .andExpect(status().is4xxClientError())
                                     .andReturn();
        
        String responseBody = mvcResult.getResponse()
                                       .getContentAsString();
        
        ErrorResponseModel errorResponseModel = TestDataHelper.readJsonAsObject(responseBody, ErrorResponseModel.class);
        assertThat(errorResponseModel.type).isEqualTo(ErrorType.VALIDATION.toString());
        
        List<ErrorModel> errorList = errorResponseModel.getErrors();
        assertThat(errorList.size()).isOne();
        
        ErrorModel errorModel = errorList.get(0);
        assertThat(errorModel.getCode()).isEqualTo("Length");
        assertThat(errorModel.getDetail()).isEqualTo("content length must be less than 30 characters");
        assertThat(errorModel.getSource()).isEqualTo("message/content");
    }
}