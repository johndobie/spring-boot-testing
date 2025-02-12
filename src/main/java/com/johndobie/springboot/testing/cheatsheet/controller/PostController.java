package com.johndobie.springboot.testing.cheatsheet.controller;


import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    
    @Autowired
    private PostService postService;
    
    public static final String POSTS_GET_ENDPOINT = "/api/posts";
    
    @GetMapping(POSTS_GET_ENDPOINT)
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }
    
    @GetMapping("/api/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }
    
    @PostMapping("/api/{id}")
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }
    
    @PutMapping("/api/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        post.setId(id);
        return postService.savePost(post);
    }
    
    @DeleteMapping("/api/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
    
    @GetMapping("/api/content")
    public List<Post> getPostsByContentContaining(@RequestParam String keyword) {
        return postService.findPostByBodyContaining(keyword);
    }
    
    @GetMapping("/api/title")
    public Post getPostsByTitle(@RequestParam String title) {
        return postService.findPostByTitle(title);
    }
}