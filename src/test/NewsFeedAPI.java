package test;

import com.socialnetwork.connecthub.backend.interfaces.services.NewsFeedService;
import com.socialnetwork.connecthub.backend.model.Post;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsFeedAPI implements NewsFeedService {
    @Override
    public List<ContentDTO> getNewsFeed(String userId) {

        List<ContentDTO> newsFeed = new java.util.ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ContentDTO content = new ContentDTO();
            content.setAuthorId("User"+String.valueOf(i));
            content.setContent("a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - ");
                    content.setTimestamp(new Date());
                    content.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
                    newsFeed.add(content);
        }
        for (int i = 0; i < 5; i++) {
            ContentDTO content = new ContentDTO();
            content.setAuthorId("User"+String.valueOf(i));
            content.setContent("a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                    "a7a neeeeeeeeek - a7a neeeeeeeeek - ");
                    content.setTimestamp(new Date());
                    content.setImagePath("src/test/Screenshot 2024-12-03 011157.png");
                    newsFeed.add(content);
        }
        ContentDTO content = new ContentDTO();
        content.setAuthorId("User"+String.valueOf(5));
        content.setContent("a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                "a7a neeeeeeeeek - a7a neeeeeeeeek - "+"a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - a7a neeeeeeeeek - " +
                "a7a neeeeeeeeek - a7a neeeeeeeeek - ");
        content.setTimestamp(new Date());
        newsFeed.add(content);


        return newsFeed;
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
    public List<UserDTO> getFriendSuggestions(String userId) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId("user" + i);
            userDTO.setUsername("User" + i);
            userDTO.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
            userDTO.setCoverPhotoPath("coverPhotoPath");
            userDTO.setBio("bio");
            userDTO.setOnlineStatus(true);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
