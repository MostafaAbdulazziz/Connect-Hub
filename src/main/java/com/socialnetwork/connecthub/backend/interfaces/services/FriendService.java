package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import java.util.List;

public interface FriendService {
    void sendFriendRequest(String senderId, String recipientId);
    void acceptFriendRequest(String userId, String requesterId);
    void declineFriendRequest(String userId, String requesterId);
    void removeFriend(String userId, String friendId);
    void blockUser(String userId, String blockedUserId);
    void unblockUser(String userId, String blockedUserId);
    List<FriendRequest> getFriendRequests(String userId);
    List<UserDTO> getFriends(String userId);
    List<UserDTO> getBlockedUsers(String userId);

}