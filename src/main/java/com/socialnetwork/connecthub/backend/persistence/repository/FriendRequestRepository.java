package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.FriendRequest;

import java.util.List;

public interface FriendRequestRepository {
    void sendRequest(FriendRequest friendRequest);
    void deleteRequest(String senderId, String receiverId);
    List<FriendRequest> findRequestsByReceiver(String receiverId);
    List<FriendRequest> findRequestsBySender(String senderId);
    List<FriendRequest> getAllRequests();
}
