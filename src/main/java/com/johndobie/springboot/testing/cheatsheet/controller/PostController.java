package com.johndobie.springboot.testing.cheatsheet.controller;


import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    
    public static final String POSTS_GET_BY_CONTENT_ENDPOINT = "/api/content";
    public static final String POSTS_GET_BY_TITLE_ENDPOINT = "/api/title";
    public static final String POSTS_GET_ALL_ENDPOINT = "/api";
    public static final String POSTS_GET_BY_ID_ENDPOINT = "/api/{id}";
    public static final String POSTS_CREATE_ENDPOINT = "/api/{id}";
    public static final String POSTS_UPDATE_ENDPOINT = "/api";
    public static final String POSTS_DELETE_ENDPOINT = "/api/{id}";

    @Autowired
    private PostService postService;
    
    @GetMapping(POSTS_GET_ALL_ENDPOINT)
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }
    
    @GetMapping(POSTS_GET_BY_ID_ENDPOINT)
    public Post getPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }
    
    @PostMapping(POSTS_CREATE_ENDPOINT)
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }
    
    @PutMapping(POSTS_UPDATE_ENDPOINT)
    public Post updatePost(@RequestBody Post post) {
        return postService.savePost(post);
    }
    
    @DeleteMapping(POSTS_DELETE_ENDPOINT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
    
    @GetMapping(POSTS_GET_BY_CONTENT_ENDPOINT)
    public List<Post> getPostsByContentContaining(@RequestParam String keyword) {
        return postService.findPostByBodyContaining(keyword);
    }
    
    @GetMapping(POSTS_GET_BY_TITLE_ENDPOINT)
    public Post getPostsByTitle(@RequestParam String title) {
        return postService.findPostByTitle(title);
    }
}