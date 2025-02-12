package com.johndobie.springboot.testing.cheatsheet.remote.client;

import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;

import java.util.List;

public interface PostClient {
    List<RemotePost> getPosts();
}
