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
    public List<FriendRequest> getFriendRequests(String userId) {
        return List.of();
    }

    @Override
    public List<UserDTO> getFriends(String userId) {
        return List.of();
    }

    @Override
    public List<UserDTO> getBlockedUsers(String userId) {
        return List.of();
    }

    @Override
    public void removeFriend(String userId, String friendId) {

    }


}
