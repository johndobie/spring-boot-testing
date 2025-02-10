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
    
    @Test
    public void testRemotePostToPost() {
        RemotePost remotePost = new RemotePost(1, "Remote Title", "Remote Body", 1);
        Post post = postMapper.remotePostToPost(remotePost);
        
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(remotePost.getId());
        assertThat(post.getTitle()).isEqualTo(remotePost.getTitle());
        assertThat(post.getBody()).isEqualTo(remotePost.getBody());
    }
    
    @Test
    public void testRemotePostsToPosts() {
        RemotePost remotePost1 = new RemotePost(1, "Remote Title 1", "Remote Body 1", 1);
        RemotePost remotePost2 = new RemotePost(2, "Remote Title 2", "Remote Body 2", 2);
        List<RemotePost> remotePosts = Arrays.asList(remotePost1, remotePost2);
        
        List<Post> posts = postMapper.remotePostsToPosts(remotePosts);
        
        assertThat(posts).isNotNull();
        assertThat(posts).hasSize(2);
        
        assertThat(posts.get(0).getId()).isEqualTo(remotePost1.getId());
        assertThat(posts.get(0).getTitle()).isEqualTo(remotePost1.getTitle());
        assertThat(posts.get(0).getBody()).isEqualTo(remotePost1.getBody());
        
        assertThat(posts.get(1).getId()).isEqualTo(remotePost2.getId());
        assertThat(posts.get(1).getTitle()).isEqualTo(remotePost2.getTitle());
        assertThat(posts.get(1).getBody()).isEqualTo(remotePost2.getBody());
    }
}
