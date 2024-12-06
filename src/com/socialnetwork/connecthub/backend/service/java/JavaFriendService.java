package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.FriendService;
import com.socialnetwork.connecthub.backend.model.Block;
import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonBlockRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonFriendRequestRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;

import java.util.List;

public class JavaFriendService implements FriendService {
    private static JavaFriendService instance;

    private JavaFriendService() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaFriendService getInstance() {
        if (instance == null) {
            instance = new JavaFriendService();
        }
        return instance;
    }

    @Override
    public void sendFriendRequest(String senderId, String recipientId) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(senderId);
        friendRequest.setReceiverId(recipientId);
        JsonFriendRequestRepository.getInstance().sendRequest(friendRequest);
    }

    @Override
    public void acceptFriendRequest(String userId, String senderId) {
        JsonFriendRequestRepository.getInstance().deleteRequest(senderId, userId);

        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        User sender = JsonUserRepository.getInstance().findById(senderId).orElseThrow();

        user.getFriends().add(senderId);
        sender.getFriends().add(userId);

        JsonUserRepository.getInstance().save(user);
        JsonUserRepository.getInstance().save(sender);
    }

    @Override
    public void declineFriendRequest(String userId, String senderId) {
        JsonFriendRequestRepository.getInstance().deleteRequest(senderId, userId);
    }

    @Override
    public void removeFriend(String userId, String friendId) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        User friend = JsonUserRepository.getInstance().findById(friendId).orElseThrow();

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        JsonUserRepository.getInstance().save(user);
        JsonUserRepository.getInstance().save(friend);
    }

    @Override
    public void blockUser(String userId, String blockedUserId) {
        Block block = new Block();
        block.setBlockingUserId(userId);
        block.setBlockedUserId(blockedUserId);
        JsonBlockRepository.getInstance().save(block);
    }

    @Override
    public void unblockUser(String userId, String blockedUserId) {
        JsonBlockRepository.getInstance().unblock(userId, blockedUserId);
    }

    @Override
    public List<FriendRequest> getFriendRequests(String userId) {
        return JsonFriendRequestRepository.getInstance().findRequestsByReceiver(userId);
    }
}
