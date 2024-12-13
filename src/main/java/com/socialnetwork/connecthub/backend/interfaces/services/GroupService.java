package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;
import com.socialnetwork.connecthub.shared.exceptions.GroupCreationException;

import java.util.List;

public interface GroupService {
    // Primary Admin Role
    void createGroup(UserDTO creatorId, GroupDTO group) throws GroupCreationException;
    void deleteGroup(String groupId);
    void promoteToAdmin(String groupId, String userId);
    void demoteAdmin(String groupId, String userId);

    // Admins Role
    List<String> getJoinRequests(String groupId);
    void approveMember(String groupId, String userId);
    void declineMember(String groupId, String userId);
    void removeMember(String groupId, String userId);
    void approvePost(String groupId, String userId, ContentDTO post);
    void deletePost(String groupId, String userId, ContentDTO post);
    void editPost(String groupId, String userId, ContentDTO post);

    // Normal Member Role
    void requestToJoin(String groupId, String userId);
    void leaveGroup(String groupId, String userId);
    void cancelJoinRequest(String groupId, String userId);
    void submitPost(String groupId, String userId, ContentDTO content) throws ContentCreationException;

    // General groups methods
    GroupDTO getGroupById(String groupId);
    List<GroupDTO> getGroupsByUserId(String userId);
    List<GroupDTO> searchGroupsByName(String name);
    String getGroupPrimaryAdmin(String groupId);
    List<String> getGroupAdmins(String groupId);
    List<String> getGroupMembers(String groupId);
    List<String> getGroupPosts(String groupId);

    List<ContentDTO> getUserGroupsPosts(String userId); //for timeline in MyGroupsView
    // must return mix of posts from groups user is member of
    List<GroupDTO> getGroupsSuggestions(String userId);
}
