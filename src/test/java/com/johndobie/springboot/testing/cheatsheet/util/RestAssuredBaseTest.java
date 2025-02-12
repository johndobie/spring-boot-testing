package com.johndobie.springboot.testing.cheatsheet.util;

import com.johndobie.springboot.testing.cheatsheet.repository.PostRepository;
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
import org.springframework.test.context.ActiveProfiles;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.testPostOne;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class RestAssuredBaseTest {
    
    private static final String BASE_PATH = "http://localhost";
    
    @Autowired
    protected PostRepository postRepository;
    
    @Autowired
    protected RestTemplateBuilder restTemplateBuilder;
    
    protected RequestSpecification requestSpecification;
    
    @LocalServerPort
    private int localServerPort;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_PATH + ":" + localServerPort;
        requestSpecification = new RequestSpecBuilder().setBaseUri(getBaseUrl())
                                                       .setPort(localServerPort)
                                                       .addFilter(new ResponseLoggingFilter())
                                                       .log(LogDetail.ALL)
                                                       .setContentType(MediaType.APPLICATION_JSON_VALUE)
                                                       .build();
        
        postRepository.deleteAll();
        postRepository.save(testPostOne);
        
    }
    
    protected String getBaseUrl() {
        return BASE_PATH + ":" + localServerPort + contextPath;
    }
}
