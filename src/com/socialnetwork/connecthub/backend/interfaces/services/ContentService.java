package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;

import java.util.List;

public interface ContentService {
    void createPost(String userId, ContentDTO contentDTO) throws ContentCreationException;
    void createStory(String userId, ContentDTO contentDTO) throws ContentCreationException;
    void deleteExpiredStories();
    List<ContentDTO> getUserPosts(String userId);
    List<ContentDTO> getFriendsPosts(String userId);
    List<ContentDTO> getUserStories(String userId);
    List<ContentDTO> getFriendsStories(String userId);
}