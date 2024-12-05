package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.backend.model.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;

import java.util.List;

public interface ContentService {
    void createPost(String userId, ContentDTO contentDTO);
    void createStory(String userId, ContentDTO contentDTO);
    void deleteExpiredStories();
    List<Post> getUserPosts(String userId);
    List<Post> getFriendsPosts(String userId);
    List<Story> getUserStories(String userId);
    List<Story> getFriendsStories(String userId);
}