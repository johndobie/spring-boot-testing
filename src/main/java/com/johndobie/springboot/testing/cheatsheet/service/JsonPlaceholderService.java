package com.johndobie.springboot.testing.cheatsheet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class JsonPlaceholderService implements PlaceholderService {
    
    private final RestTemplate restTemplate;
    
    public JsonPlaceholderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public List<Object> getPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        Object[] posts = restTemplate.getForObject(url, Object[].class);
        return Arrays.asList(Objects.requireNonNull(posts));
    }
}