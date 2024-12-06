package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(String postId);
    void save(Post post);
    void delete(String postId);
    List<String> getAllPostIds();
}
