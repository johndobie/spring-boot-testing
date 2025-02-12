package com.johndobie.springboot.testing.cheatsheet.service;

import com.johndobie.springboot.testing.cheatsheet.exception.PostNotFoundException;
import com.johndobie.springboot.testing.cheatsheet.mapper.PostMapper;
import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.client.PostClient;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import com.johndobie.springboot.testing.cheatsheet.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabasePostService implements PostService {
    
    private final PostRepository postRepository;
    private final PostClient postClient;
    private final PostMapper postMapper = PostMapper.INSTANCE;
    
    @Autowired
    public DatabasePostService(final PostRepository postRepository, final PostClient postClient) {
        this.postRepository = postRepository;
        this.postClient = postClient;
    }
    
    /**
     * Get posts from the remote service and save them to the database.
     *
     * @return List of saved posts
     */
    @Override
    public List<Post> getAllPosts() {
        List<RemotePost> posts = postClient.getPosts();
        List<Post> mappedPosts = postMapper.remotePostsToPosts(posts);
        List<Post> savedPosts = postRepository.saveAll(mappedPosts);
        return savedPosts;
    }
    
    @Override
    public Post findPostByTitle(String title) {
        return postRepository.findByTitle(title)
                             .orElseThrow(() -> new PostNotFoundException("Could Not Find The Post With Title: " + title));
    }
    
    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id)
                             .orElseThrow(() -> new PostNotFoundException("Could Not Find The Post With Title: " + id));
    }
    
    @Override
    public List<Post> findPostByBodyContaining(String keyword) {
        return postRepository.findByBodyContaining(keyword);
    }
    
    @Override
    public List<Post> findPostByTitleAndBodyContaining(String title, String keyword) {
        return postRepository.findByTitleAndBodyContaining(title, keyword);
    }
    
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    
}