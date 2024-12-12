package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.NewsFeedService;
import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonBlockRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonFriendRequestRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaNewsFeedService implements NewsFeedService {
    private static JavaNewsFeedService instance;

    private JavaNewsFeedService() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaNewsFeedService getInstance() {
        if (instance == null) {
            instance = new JavaNewsFeedService();
        }
        return instance;
    }

    @Override
    public List<UserDTO> getOnlineFriends(String userId) {
        List<User> friends = JsonUserRepository.getInstance().findAllFriends(userId);
        List<UserDTO> onlineFriends = new ArrayList<>();
        for (User friend : friends) {
            if (friend.isOnlineStatus()) {
                onlineFriends.add(new UserDTO(friend));
            }
        }
        return onlineFriends;
    }

    @Override
    public List<UserDTO> getFriendSuggestions(String userId) {
        // Fetch friends of the user
        List<User> friends = JsonUserRepository.getInstance().findAllFriends(userId);
        List<User> usersFromDB = JsonUserRepository.getInstance().findAll();
        usersFromDB = usersFromDB.subList(0, Math.min(usersFromDB.size(), 20));

        // Gather friend suggestions from friends of friends
        HashSet<User> suggestions = new HashSet<>();
        for (User friend : friends) {
            List<User> friendsOfFriend = JsonUserRepository.getInstance().findAllFriends(friend.getUserId());
            for (User secondFriend : friendsOfFriend) {
                if (!secondFriend.getUserId().equals(userId)) {
                    suggestions.add(secondFriend);
                }
            }
        }
        for (User user : usersFromDB) {
            if (!user.getUserId().equals(userId)) {
                suggestions.add(user);
            }
        }

        // Convert suggestions to a list for filtering
        List<UserDTO> finalList = new ArrayList<>();
        for (User suggestion : suggestions) {
            finalList.add(new UserDTO(suggestion));
        }

        // Collect blocked users and sent/received friend requests
        List<FriendRequest> sentRequests = JsonFriendRequestRepository.getInstance().findRequestsBySender(userId);
        List<FriendRequest> receivedRequests = JsonFriendRequestRepository.getInstance().findRequestsByReceiver(userId);
        List<UserDTO> toRemove = new ArrayList<>();

        for (UserDTO friend : finalList) {
            // Remove blocked users
            if (JsonBlockRepository.getInstance().findByIds(userId, friend.getUserId()).isPresent()) {
                toRemove.add(friend);
                continue;
            }

            // Remove common friends from suggestions
            for (UserDTO suggestion : finalList) {
                if (friends.contains(JsonUserRepository.getInstance().findById(suggestion.getUserId()).orElseThrow())) {
                    toRemove.add(suggestion);
                }
            }

            // Remove users who have already received friend requests
            for (FriendRequest friendRequest : sentRequests) {
                if (friendRequest.getReceiverId().equals(friend.getUserId())) {
                    toRemove.add(friend);
                    break;
                }
            }

            // Remove users who have already sent friend requests
            for (FriendRequest friendRequest : receivedRequests) {
                if (friendRequest.getSenderId().equals(friend.getUserId())) {
                    toRemove.add(friend);
                    break;
                }
            }
        }

        // Remove all marked items in one go
        finalList.removeAll(toRemove);

        // This prevents ConcurrentModificationException

        return finalList;
    }


}
