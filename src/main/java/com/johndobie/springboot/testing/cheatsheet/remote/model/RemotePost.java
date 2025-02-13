package com.johndobie.springboot.testing.cheatsheet.remote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemotePost {
    private Long id;
    private String title;
    private String body;
    private Long userId;
}