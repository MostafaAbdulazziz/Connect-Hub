package test;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.model.Story;

import java.util.List;

public class ContentServiceTest implements ContentService {
    @Override
    public void createPost(String userId, String content) {
    }

    @Override
    public void createStory(String userId, String content) {
    }

    @Override
    public void deleteExpiredStories() {
    }

    @Override
    public java.util.List<com.socialnetwork.connecthub.backend.model.Post> getUserPosts(String userId) {
        return null;
    }

    @Override
    public java.util.List<com.socialnetwork.connecthub.backend.model.Post> getFriendsPosts(String userId) {
        return null;
    }

    @Override
    public java.util.List<com.socialnetwork.connecthub.backend.model.Story> getUserStories(String userId) {
        List<Story> stories = new java.util.ArrayList<>();
       for (int i =0; i<20; i++){
           Story story = new Story();
           story.setContent(String.valueOf(i)+"stererees");
           story.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
           stories.add(story);
       }

        return stories;
    }

    @Override
    public java.util.List<com.socialnetwork.connecthub.backend.model.Story> getFriendsStories(String userId) {
        List<Story> stories = new java.util.ArrayList<>();
        for (int i =0; i<20; i++){
            Story story = new Story();
            story.setContent(String.valueOf(i)+"stererees");
            story.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
            stories.add(story);
        }

        return stories;
    }
}
