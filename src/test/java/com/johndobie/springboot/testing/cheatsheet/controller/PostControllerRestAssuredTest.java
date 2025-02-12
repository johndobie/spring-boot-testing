package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.util.RestAssuredBaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class PostControllerRestAssuredTest extends RestAssuredBaseTest {
    
    @Test
    public void testGetPosts() {
        given(requestSpecification)
                .when()
                .get("/api/posts")
                .then()
                .statusCode(200)
                .body("$", hasSize(100));
    }
}