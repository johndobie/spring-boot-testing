package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.mapper.PostMapper;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.client.PlaceholderClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonPlaceholderService implements PlaceholderService {
    
    private final PlaceholderClient placeholderClient;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    
    public JsonPlaceholderService(PlaceholderClient placeholderClient) {
        this.placeholderClient = placeholderClient;
    }
    
    @Override
    public List<Post> getPosts() {
        List<RemotePost> remotePosts = placeholderClient.getPosts();
        return postMapper.remotePostsToPosts(remotePosts);
    }
}