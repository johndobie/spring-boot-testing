package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceholderService {
    List<Post> getPosts();
}
