package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.model.*;
import com.socialnetwork.connecthub.backend.persistence.json.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class JavaContentService implements ContentService {
    private static JavaContentService instance;

    private JavaContentService() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaContentService getInstance() {
        if (instance == null) {
            instance = new JavaContentService();
        }
        return instance;
    }

    @Override
    public void createPost(String userId, ContentDTO contentDTO) throws ContentCreationException {
        if (contentDTO.getContent().isEmpty() && contentDTO.getImagePath().isEmpty()) {
            throw new ContentCreationException("Content is empty");
        }

        // Create new Post object
        Post post = new Post();
        post.setAuthorId(userId);
        if (contentDTO.getContent() != null)
            post.setContent(contentDTO.getContent());
        if (contentDTO.getImagePath() != null)
            post.setImagePath(contentDTO.getImagePath());
        post.setTimestamp(new Date());

        // Save the post to the repository
        JsonPostRepository.getInstance().save(post);

        // Update the user's posts list
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.getPosts().add(post.getContentId());
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void createStory(String userId, ContentDTO contentDTO) throws ContentCreationException {
        if (contentDTO.getContent().isEmpty() && contentDTO.getImagePath().isEmpty()) {
            throw new ContentCreationException("Content is empty");
        }

        // Create new Story object
        Story story = new Story();
        story.setAuthorId(userId);
        if (contentDTO.getContent() != null)
            story.setContent(contentDTO.getContent());
        if (contentDTO.getImagePath() != null)
            story.setImagePath(contentDTO.getImagePath());
        story.setTimestamp(new Date());
        // Set an expiry date for the story (for example, 24 hours)
        story.setExpiryDate(new Date(System.currentTimeMillis() + 86400000L));  // 1 day in milliseconds

        // Save the story to the repository
        JsonStoryRepository.getInstance().save(story);

        // Update the user's stories list
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.getStories().add(story.getContentId());
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void deleteExpiredStories() {
        List<String> allStoriesIds = JsonStoryRepository.getInstance().getAllStoryIds(); // Get all stories
        Date currentDate = new Date();

        // Find expired stories and delete them
        for (String storyId : allStoriesIds) {
            Story story = JsonStoryRepository.getInstance().findById(storyId).orElseThrow();
            if (story.getExpiryDate().before(currentDate)) {
                JsonStoryRepository.getInstance().delete(story.getContentId());
            }
        }
    }

    @Override
    public List<ContentDTO> getUserPosts(String userId) {
        deleteExpiredStories();
        // Get all posts for a user
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<String> postIds = user.getPosts();

        // Create a list to store the ContentDTOs and sort them
        List<ContentDTO> contentDTOs = getContentDTOS(postIds);
        contentDTOs.sort(Comparator.comparing(ContentDTO::getTimestamp).reversed()); // Sort by timestamp (newest first)
        return contentDTOs;
    }

    @Override
    public List<ContentDTO> getFriendsPosts(String userId) {
        List<User> friends = getFriends(userId);

        // Create a list to store all post IDs from friends
        List<String> allPostIds = new ArrayList<>();

        // Collect all posts from friends
        for (User friend : friends) {
            List<String> friendPostIds = friend.getPosts();
            allPostIds.addAll(friendPostIds);
        }

        // Create a list to store the ContentDTOs and sort them
        List<ContentDTO> contentDTOs = getContentDTOS(allPostIds);
        contentDTOs.sort(Comparator.comparing(ContentDTO::getTimestamp).reversed()); // Sort by timestamp (newest first)
        return contentDTOs;
    }

    @Override
    public List<ContentDTO> getUserStories(String userId) {
        // Get the user's stories
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<String> storyIds = user.getStories();

        // Create a list to store the ContentDTOs and sort them
        List<ContentDTO> contentDTOs = getContentDTOS(storyIds);
        contentDTOs.sort(Comparator.comparing(ContentDTO::getTimestamp).reversed()); // Sort by timestamp (newest first)
        return contentDTOs;
    }

    @Override
    public List<ContentDTO> getFriendsStories(String userId) {
        deleteExpiredStories();
        List<User> friends = getFriends(userId);

        // Create a list to store all story IDs from friends
        List<String> allStoryIds = new ArrayList<>();

        // Collect all stories from friends
        for (User friend : friends) {
            List<String> friendStoryIds = friend.getStories();
            allStoryIds.addAll(friendStoryIds);
        }

        // Create a list to store the ContentDTOs and sort them
        List<ContentDTO> contentDTOs = getContentDTOS(allStoryIds);
        contentDTOs.sort(Comparator.comparing(ContentDTO::getTimestamp).reversed()); // Sort by timestamp (newest first)
        return contentDTOs;
    }


    private List<User> getFriends(String userId) {
        // Get the user's friends
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<User> friends = new ArrayList<>();

        // Retrieve friends using a for loop
        for (String friendId : user.getFriends()) {
            User friend = JsonUserRepository.getInstance().findById(friendId).orElseThrow();
            // Don't get content from blocked users in the content service
            if(JsonBlockRepository.getInstance().findByIds(userId, friendId).isEmpty())
                friends.add(friend);
        }

        return friends;
    }

    private List<ContentDTO> getContentDTOS(List<String> ContentIds) {
        List<ContentDTO> contentDTOList = new ArrayList<>();// Retrieve posts by their IDs and convert to ContentDTO
        for (String postId : ContentIds) {
            Content post = JsonPostRepository.getInstance().findById(postId).orElseThrow();
            ContentDTO contentDTO = new ContentDTO(post.getAuthorId(), post.getContent(), post.getImagePath(), post.getTimestamp());
            contentDTOList.add(contentDTO);
        }

        return contentDTOList;
    }

}