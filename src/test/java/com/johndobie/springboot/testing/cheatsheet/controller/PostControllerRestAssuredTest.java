package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.util.RestAssuredBaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.johndobie.springboot.testing.cheatsheet.util.TestDataHelper.testPostOne;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class PostControllerRestAssuredTest extends RestAssuredBaseTest {
    
    @BeforeEach
    public void setupDatabase() {
        postRepository.deleteAll();
        postRepository.save(testPostOne);
    }
    
    @Test
    public void testGetPosts() {
        List<Post> posts = given(requestSpecification).when()
                                                      .get("/api/posts")
                                                      .then()
                                                      .statusCode(200)
                                                      .extract()
                                                      .body()
                                                      .jsonPath()
                                                      .getList(".", Post.class);
        
        assertThat(posts.size()).isEqualTo(100);
    }
    
    @Test
    public void testGetPostById() {
        
        Response response = given(requestSpecification).when()
                                                       .get("/api/1")
                                                       .then()
                                                       .statusCode(200)
                                                       .extract()
                                                       .response();
        
        Post retrievedPost = response.as(Post.class);
        assertThat(retrievedPost).isEqualTo(testPostOne);
    }
    
    @Test
    public void testCreatePost() {
        Post post = testPostOne;
        
        given(requestSpecification).contentType("application/json")
                                   .body(post)
                                   .when()
                                   .post("/api/1")
                                   .then()
                                   .statusCode(200)
                                   .body(
                                           "id",
                                           equalTo(testPostOne.getId()
                                                              .intValue()))
                                   .body("title", equalTo(testPostOne.getTitle()))
                                   .body("body", equalTo(testPostOne.getBody()));
    }
    
    @Test
    public void testUpdatePost() {
        Post post = new Post(1L, "Updated Post", "Updated Body");
        
        given(requestSpecification).contentType("application/json")
                                   .body(post)
                                   .when()
                                   .put("/api/1")
                                   .then()
                                   .statusCode(200)
                                   .body("id", equalTo(1))
                                   .body("title", equalTo("Updated Post"))
                                   .body("body", equalTo("Updated Body"));
    }
    
    @Test
    public void testDeletePost() {
        
        postRepository.save(testPostOne);
        assertThat(postRepository.findById(1L)).isNotEmpty();
        
        given(requestSpecification).when()
                                   .delete("/api/1")
                                   .then()
                                   .statusCode(200);
        
        assertThat(postRepository.findById(1L)).isEmpty();
    }
    
    @Test
    public void testGetPostsByContentContaining() {
        
        given(requestSpecification).param("keyword", "Test")
                                   .when()
                                   .get("/api/content")
                                   .then()
                                   .statusCode(200)
                                   .body("$", hasSize(1))
                                   .body(
                                           "[0].id",
                                           equalTo(testPostOne.getId()
                                                              .intValue()))
                                   .body("[0].title", equalTo(testPostOne.getTitle()))
                                   .body("[0].body", equalTo(testPostOne.getBody()));
    }
    
    @Test
    public void testGetPostsByTitle() {
        
        Response response = given(requestSpecification).param("title", testPostOne.getTitle())
                                                       .when()
                                                       .get("/api/title")
                                                       .then()
                                                       .statusCode(200)
                                                       .extract()
                                                       .response();
        
        Post retrievedPost = response.as(Post.class);
        assertThat(retrievedPost).usingRecursiveComparison()
                                 .isEqualTo(testPostOne);
    }
}