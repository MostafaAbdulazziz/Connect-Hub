package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(String userId);
    Optional<User> findByEmail(String email);
    void save(User user);
    void delete(String userId);
    List<User> findAllFriends(String userId);
    List<User> findAllBlockedUsers(String userId);
    List<String> getReceivedFriendRequests(String userId);
    List<String> getPostsByUserId(String userId);
    List<String> getStoriesByUserId(String userId);
}