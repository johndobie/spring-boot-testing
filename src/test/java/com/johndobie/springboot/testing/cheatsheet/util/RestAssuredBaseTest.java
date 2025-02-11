package com.johndobie.springboot.testing.cheatsheet.util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestAssuredBaseTest {
    
    private static final String BASE_PATH = "http://localhost";
    
    @LocalServerPort
    private int localServerPort;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    protected String getBaseUrl() {
        return BASE_PATH + ":" + localServerPort + contextPath;
    }
    
    
    @Autowired
    protected RestTemplateBuilder restTemplateBuilder;
    
    protected RequestSpecification requestSpecification;
    
   
    
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_PATH + ":" + localServerPort;
        requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseUrl())
                                                       .setPort(localServerPort)
                                                       .addFilter(new ResponseLoggingFilter())
                                                       .log(LogDetail.ALL)
                                                       .setContentType(MediaType.APPLICATION_JSON_VALUE)
                                                       .build();
    }
}
