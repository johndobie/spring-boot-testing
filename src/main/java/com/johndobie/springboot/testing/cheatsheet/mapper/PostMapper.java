package com.johndobie.springboot.testing.cheatsheet.mapper;

import com.johndobie.springboot.testing.cheatsheet.model.Post;
import com.johndobie.springboot.testing.cheatsheet.remote.model.RemotePost;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PostMapper {
    
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    
    Post remotePostToPost(RemotePost remotePost);
    
    List<Post> remotePostsToPosts(List<RemotePost> remotePosts);
}
