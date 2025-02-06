package com.johndobie.springboot.testing.cheatsheet.repository;

import com.johndobie.springboot.testing.cheatsheet.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByContent(String content);
}