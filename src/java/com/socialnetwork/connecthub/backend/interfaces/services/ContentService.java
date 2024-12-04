package java.com.socialnetwork.connecthub.backend.interfaces.services;

import java.com.socialnetwork.connecthub.backend.model.*;
import java.util.List;

public interface ContentService {
    void createPost(String userId, String content);
    void createStory(String userId, String content);
    void deleteExpiredStories();
    List<Post> getUserPosts(String userId);
    List<Post> getFriendsPosts(String userId);
    List<Story> getUserStories(String userId);
    List<Story> getFriendsStories(String userId);
}