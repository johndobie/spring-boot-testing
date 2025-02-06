package com.johndobie.springboot.testing.cheatsheet.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class MockMvcBaseTest {
    
    @Autowired
    protected MockMvc mockMvc;

}
