package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<Post> getAllPosts();
    
    Post findPostByTitle(String title);
    
    Post findPostById(Long id);
    
    List<Post> findPostByBodyContaining(String keyword);
    
    List<Post> findPostByTitleAndBodyContaining(String title, String keyword);
    
    Post savePost(Post post);
    
    void deletePost(Long id);
}
