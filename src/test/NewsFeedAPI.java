package test;

import com.socialnetwork.connecthub.backend.interfaces.services.NewsFeedService;
import com.socialnetwork.connecthub.backend.model.Post;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.List;

public class NewsFeedAPI implements NewsFeedService {
    @Override
    public List<Post> getNewsFeed(String userId) {
        return null;
    }

    @Override
    public java.util.List<UserDTO> getOnlineFriends(String userId) {
        List<UserDTO> friends = new java.util.ArrayList<>();
        for (int i = 0; i < 30; i++) {
            UserDTO user = new UserDTO();
            user.setUserId(String.valueOf(i));
            user.setUsername("User " + i);
            user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
            friends.add(user);
        }
        return friends;


    }

    @Override
    public java.util.List<UserDTO> getFriendSuggestions(String userId) {
        return null;
    }
}
