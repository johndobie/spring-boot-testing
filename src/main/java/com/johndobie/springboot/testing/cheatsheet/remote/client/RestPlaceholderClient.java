package com.johndobie.springboot.testing.cheatsheet.remote.client;

import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class RestPlaceholderClient implements PlaceholderClient {
    
    private final RestTemplate restTemplate;
    
    public RestPlaceholderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public List<RemotePost> getPosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        RemotePost[] remotePosts = restTemplate.getForObject(url, RemotePost[].class);
        return Arrays.asList(Objects.requireNonNull(remotePosts));
    }
}
