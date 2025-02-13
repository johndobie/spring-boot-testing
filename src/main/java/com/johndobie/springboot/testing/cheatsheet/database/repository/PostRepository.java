package com.johndobie.springboot.testing.cheatsheet.database.repository;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    List<Post> findByBodyContaining(String keyword);
    List<Post> findByTitleAndBodyContaining(String title, String keyword);
}