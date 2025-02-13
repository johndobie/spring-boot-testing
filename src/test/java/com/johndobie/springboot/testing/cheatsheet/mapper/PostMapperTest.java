package com.johndobie.springboot.testing.cheatsheet.mapper;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    
    private final RemotePost remotePost1 = new RemotePost(1L, "Remote Title 1", "Remote Body 1", 1L);
    private final RemotePost remotePost2 = new RemotePost(2L, "Remote Title 2", "Remote Body 2", 2L);
    
    @Test
    public void testRemotePostToPost() {
        Post post = postMapper.remotePostToPost(remotePost1);
        
        assertThat(post).usingRecursiveComparison().isEqualTo(remotePost1);
    }
    
    @Test
    public void testRemotePostsToPosts() {
        List<RemotePost> remotePosts = Arrays.asList(remotePost1, remotePost2);
        
        List<Post> posts = postMapper.remotePostsToPosts(remotePosts);
        
        assertThat(posts).isNotNull();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0)).usingRecursiveComparison().isEqualTo(remotePost1);
        assertThat(posts.get(1)).usingRecursiveComparison().isEqualTo(remotePost2);
    }
    
    @Test
    public void testRemotePostToPostReturnsNull() {
        Post post = postMapper.remotePostToPost(null);
        assertThat(post).isNull();
    }
    
    @Test
    public void testRemotePostsToPostsReturnsNull() {
        List<Post> posts = postMapper.remotePostsToPosts(null);
        assertThat(posts).isNull();
    }
}
