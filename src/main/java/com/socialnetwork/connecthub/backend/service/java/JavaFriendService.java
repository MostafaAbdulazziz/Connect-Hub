package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.FriendService;
import com.socialnetwork.connecthub.backend.model.*;
import com.socialnetwork.connecthub.backend.persistence.json.JsonBlockRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonFriendRequestRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
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

        // send notification for user
        User recipient = JsonUserRepository.getInstance().findById(recipientId).orElseThrow();
        recipient.getNotifications().add(new FriendRequestNotification(senderId));
        JsonUserRepository.getInstance().save(recipient);
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


        // send notification
        sender.getNotifications().add(new FriendRequestNotification(senderId)
                .setSenderId(userId)
                .setMessage("User " + user.getUsername() + " accepted friend request")
        );
        JsonUserRepository.getInstance().save(sender);
    }

    @Override
    public void declineFriendRequest(String senderId, String receiverId) {
        JsonFriendRequestRepository.getInstance().deleteRequest(senderId, receiverId);
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

        // Remove user from each other's friend list
        removeFriend(userId, blockedUserId);

        // Remove friend request if one sent to other
        JsonFriendRequestRepository.getInstance().deleteRequest(userId, blockedUserId);
        JsonFriendRequestRepository.getInstance().deleteRequest(blockedUserId, userId);
    }

    @Override
    public void unblockUser(String userId, String blockedUserId) {
        JsonBlockRepository.getInstance().unblock(userId, blockedUserId);
    }

    @Override
    public List<FriendRequest> getFriendRequests(String userId) {
        return JsonFriendRequestRepository.getInstance().findRequestsByReceiver(userId);
    }

    @Override
    public List<UserDTO> getFriends(String userId) {
        List<User> friends = getFriendsHelper(userId);
        List<UserDTO> friendsDTOs = new ArrayList<>();
        for (User friend : friends) {
            friendsDTOs.add(
                new UserDTO(
                        friend.getUserId(),
                        friend.getUsername(),
                        friend.getProfilePhotoPath(),
                        friend.getCoverPhotoPath(),
                        friend.getBio(),
                        friend.isOnlineStatus()
                )
            );
        }

        return friendsDTOs;
    }

    @Override
    public List<UserDTO> getBlockedUsers(String userId) {
        List<Block> blocks = JsonBlockRepository.getInstance().findAllByBlockingUserId(userId);
        List<UserDTO> blockedUsersDTOs = new ArrayList<>();
        for (Block block : blocks) {
            User blockedUser = JsonUserRepository.getInstance().findById(block.getBlockedUserId()).orElseThrow();
            blockedUsersDTOs.add(new UserDTO(blockedUser));
        }

        return blockedUsersDTOs;
    }

    private List<User> getFriendsHelper(String userId) {
        // Get the user's friends
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<User> friends = new ArrayList<>();

        // Retrieve friends using a for loop
        for (String friendId : user.getFriends()) {
            User friend = JsonUserRepository.getInstance().findById(friendId).orElseThrow();
            // Don't process blocked users
            if(JsonBlockRepository.getInstance().findByIds(userId, friendId).isEmpty())
                friends.add(friend);
        }

        return friends;
    }

    public enum FriendStatus {
        FRIEND_REQUEST_SENT,
        FRIEND_REQUEST_RECEIVED,
        NOT_FRIENDS,
        FRIENDS;
    }

    public FriendStatus getFriendStatus(String userId, String friendId) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();

        if (user.getFriends().contains(friendId)) {
            return FriendStatus.FRIENDS; // Can remove from friends
        }

        List<FriendRequest> friendReceivedFriendRequests = getFriendRequests(friendId);
        for (FriendRequest friendRequest : friendReceivedFriendRequests) {
            if (friendRequest.getSenderId().equals(userId)) {
                return FriendStatus.FRIEND_REQUEST_SENT; // Can cancel request
            }
        }

        List<FriendRequest> userReceivedFriendRequests = getFriendRequests(userId);
        for (FriendRequest friendRequest : userReceivedFriendRequests) {
            if (friendRequest.getSenderId().equals(friendId)) {
                return FriendStatus.FRIEND_REQUEST_RECEIVED; // Can Approve or decline
            }
        }

        return FriendStatus.NOT_FRIENDS; // Can send Request
    }

}
