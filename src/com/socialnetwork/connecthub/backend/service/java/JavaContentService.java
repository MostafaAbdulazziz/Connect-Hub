package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.model.Post;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;

import java.util.List;

public class JavaContentService implements ContentService {
    @Override
    public void createPost(String userId, ContentDTO contentDTO) throws ContentCreationException {
        if(contentDTO.getContent().isEmpty() && contentDTO.getImagePath().isEmpty()){
            throw new ContentCreationException("Content is empty");
        }

        Post post = new Post();
        post.setAuthorId(userId);
        post.setContent(contentDTO.getContent());
        post.setImagePath(contentDTO.getImagePath());

        JsonPostRepository.getInstance().save(post);

        User user = JsonUserRepository.getInstance().findById(userId).get();
        user.getPosts().add(post.getContentId());
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void createStory(String userId, ContentDTO contentDTO) throws ContentCreationException {

    }

    @Override
    public void deleteExpiredStories() {

    }

    @Override
    public List<ContentDTO> getUserPosts(String userId) {
        return List.of();
    }

    @Override
    public List<ContentDTO> getFriendsPosts(String userId) {
        return List.of();
    }

    @Override
    public List<ContentDTO> getUserStories(String userId) {
        return List.of();
    }

    @Override
    public List<ContentDTO> getFriendsStories(String userId) {
        return List.of();
    }
}
