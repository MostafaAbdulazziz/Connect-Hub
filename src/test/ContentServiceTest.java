package test;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.model.Story;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;

import java.util.List;

public class ContentServiceTest implements ContentService {


    @Override
    public void createPost(String userId, ContentDTO contentDTO) {

    }

    @Override
    public void createStory(String userId, ContentDTO contentDTO) {

    }

    @Override
    public void deleteExpiredStories() {
    }

    @Override
    public List<ContentDTO> getUserPosts(String userId) {
        return null;
    }

    @Override
    public List<ContentDTO> getFriendsPosts(String userId) {
        List<ContentDTO> posts = new java.util.ArrayList<>();
        for (int i =0; i<20; i++){
            ContentDTO post = new ContentDTO();
            post.setContent(String.valueOf(i)+"stererees");
            post.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
            posts.add(post);
        }
        return posts;

    }

    @Override
    public List<ContentDTO> getUserStories(String userId) {
        List<ContentDTO> stories = new java.util.ArrayList<>();
       for (int i =0; i<20; i++){
           ContentDTO story = new ContentDTO();
           story.setContent(String.valueOf(i)+"stererees");
           story.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
           stories.add(story);
       }

        return stories;
    }

    @Override
    public List<ContentDTO> getFriendsStories(String userId) {
        List<ContentDTO> stories = new java.util.ArrayList<>();
        for (int i =0; i<20; i++){
            ContentDTO story = new ContentDTO();
            story.setContent(String.valueOf(i)+"stererees");
            story.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
            stories.add(story);
        }

        return stories;
    }
}
