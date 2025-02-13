package com.johndobie.springboot.testing.cheatsheet.util;

import com.johndobie.springboot.testing.cheatsheet.database.repository.PostRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class RestAssuredBaseTest {
    
    protected static final String BASE_PATH = "http://localhost";
    
    @Autowired
    protected PostRepository postRepository;
    
    protected RequestSpecification requestSpecification;
    
    @LocalServerPort
    protected int localServerPort;
    
    @Value("${server.servlet.context-path}")
    protected String contextPath;
    
    protected String getBaseUrl() {
        return BASE_PATH + ":" + localServerPort + contextPath;
    }
    
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
