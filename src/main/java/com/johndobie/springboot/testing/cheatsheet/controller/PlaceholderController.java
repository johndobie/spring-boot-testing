package com.johndobie.springboot.testing.cheatsheet.controller;


import com.johndobie.springboot.testing.cheatsheet.service.PlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaceholderController {
    
    @Autowired
    private PlaceholderService placeholderService;
    
    public static final String POSTS_GET_ENDPOINT = "/api/posts";
    
    @GetMapping(POSTS_GET_ENDPOINT)
    public List<Object> getPosts() {
        return placeholderService.getPosts();
    }
}