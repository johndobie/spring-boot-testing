package com.johndobie.springboot.testing.cheatsheet.remote.client;

import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;

import java.util.List;

public interface PlaceholderClient {
    List<RemotePost> getPosts();
}
