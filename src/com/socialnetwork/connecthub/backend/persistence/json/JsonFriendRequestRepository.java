package com.socialnetwork.connecthub.backend.persistence.json;

import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.backend.persistence.repository.FriendRequestRepository;
import com.socialnetwork.connecthub.util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonFriendRequestRepository implements FriendRequestRepository {
    private static final String FILE_PATH = "src/com/socialnetwork/connecthub/resources/data/friend_requests.json";
    private static JsonFriendRequestRepository instance;
    private final JsonFileUtil<FriendRequest> jsonFileUtil = new JsonFileUtil<>(FriendRequest[].class);
    private List<FriendRequest> friendRequests;

    private JsonFriendRequestRepository() {
        friendRequests = new ArrayList<>(jsonFileUtil.loadFromFile(FILE_PATH));
    }

    public static synchronized JsonFriendRequestRepository getInstance() {
        if (instance == null) {
            instance = new JsonFriendRequestRepository();
        }
        return instance;
    }

    @Override
    public void sendRequest(FriendRequest friendRequest) {
        friendRequests.add(friendRequest);
        jsonFileUtil.saveToFile(FILE_PATH, friendRequests);
    }

    @Override
    public void deleteRequest(String senderId, String receiverId) {
        for (int i = 0; i < friendRequests.size(); i++) {
            FriendRequest request = friendRequests.get(i);
            if (request.getSenderId().equals(senderId) && request.getReceiverId().equals(receiverId)) {
                friendRequests.remove(i);
                break;
            }
        }
        jsonFileUtil.saveToFile(FILE_PATH, friendRequests);
    }

    @Override
    public List<FriendRequest> findRequestsByReceiver(String receiverId) {
        List<FriendRequest> requestsToReceiver = new ArrayList<>();
        for (FriendRequest request : friendRequests) {
            if (request.getReceiverId().equals(receiverId)) {
                requestsToReceiver.add(request);
            }
        }
        return requestsToReceiver;
    }

    @Override
    public List<FriendRequest> findRequestsBySender(String senderId) {
        List<FriendRequest> requestsFromSender = new ArrayList<>();
        for (FriendRequest request : friendRequests) {
            if (request.getSenderId().equals(senderId)) {
                requestsFromSender.add(request);
            }
        }
        return requestsFromSender;
    }

    @Override
    public List<FriendRequest> getAllRequests() {
        return new ArrayList<>(friendRequests);
    }
}
