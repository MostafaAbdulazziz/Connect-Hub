package java.com.socialnetwork.connecthub.backend.interfaces.services;

import java.com.socialnetwork.connecthub.backend.model.FriendRequest;
import java.com.socialnetwork.connecthub.shared.dto.UserDTO;
import java.util.List;

public interface FriendService {
    void sendFriendRequest(String senderId, String recipientId);
    void acceptFriendRequest(String userId, String requesterId);
    void declineFriendRequest(String userId, String requesterId);
    void removeFriend(String userId, String friendId);
    void blockUser(String userId, String blockedUserId);
    void unblockUser(String userId, String blockedUserId);
    List<UserDTO> getFriendSuggestions(String userId);
    List<FriendRequest> getFriendRequests(String userId);
}