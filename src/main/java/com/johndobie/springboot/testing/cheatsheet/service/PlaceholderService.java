package com.johndobie.springboot.testing.cheatsheet.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceholderService {
    List<Object> getPosts();
}
