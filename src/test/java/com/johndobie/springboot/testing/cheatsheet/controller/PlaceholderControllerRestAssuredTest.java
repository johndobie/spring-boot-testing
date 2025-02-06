package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.util.IntegrationBaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class PlaceholderControllerRestAssuredTest extends IntegrationBaseTest {
    
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