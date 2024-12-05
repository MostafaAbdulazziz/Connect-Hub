package test;

import com.socialnetwork.connecthub.backend.interfaces.services.FriendService;
import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class FriendServiceTest implements FriendService {
    @Override
    public void sendFriendRequest(String senderId, String receiverId) {

    }

    @Override
    public void acceptFriendRequest(String senderId, String receiverId) {

    }

    @Override
    public void declineFriendRequest(String senderId, String receiverId) {

    }

    @Override
    public void blockUser(String userId, String blockedUserId) {

    }

    @Override
    public void unblockUser(String userId, String blockedUserId) {

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

    @Override
    public List<FriendRequest> getFriendRequests(String userId) {
        return List.of();
    }

    @Override
    public void removeFriend(String userId, String friendId) {

    }


}
