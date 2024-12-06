package test;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContentServiceTest implements ContentService {

    @Override
    public void createPost(String userId, ContentDTO contentDTO) {
        // Simulate post creation (can be extended for testing purposes)
    }

    @Override
    public void createStory(String userId, ContentDTO contentDTO) {
        // Simulate story creation (can be extended for testing purposes)
    }

    @Override
    public void deleteExpiredStories() {
        // Simulate deletion of expired stories
    }

    @Override
    public List<ContentDTO> getUserPosts(String userId) {
        List<ContentDTO> posts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ContentDTO post = new ContentDTO();
            post.setContent("Sample post content Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content #Sample post content ##" + i);
            post.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
            post.setAuthorId(userId);
            post.setTimestamp(new Date());
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<ContentDTO> getFriendsPosts(String userId) {
        return getUserPosts(userId); // Simulated for simplicity
    }

    @Override
    public List<ContentDTO> getUserStories(String userId) {
        return getUserPosts(userId); // Simulated for simplicity
    }

    @Override
    public List<ContentDTO> getFriendsStories(String userId) {
        return getUserPosts(userId); // Simulated for simplicity
    }
}
