package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.GroupService;
import com.socialnetwork.connecthub.backend.model.Group;
import com.socialnetwork.connecthub.backend.model.Post;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonGroupRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;
import com.socialnetwork.connecthub.shared.exceptions.GroupCreationException;
import com.socialnetwork.connecthub.util.idgenerator.IdGeneratorFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaGroupService implements GroupService {
    private static JavaGroupService instance;

    private JavaGroupService() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaGroupService getInstance() {
        if (instance == null) {
            instance = new JavaGroupService();
        }
        return instance;
    }

    // Primary Admin Role
    @Override
    public void createGroup(UserDTO creator, GroupDTO groupDTO) throws GroupCreationException {
        if (creator == null || groupDTO == null) {
            throw new GroupCreationException("Creator or group details cannot be null.");
        }

        Group group = new Group();
        group.setGroupId(IdGeneratorFactory.getIdGenerator("uuid").generateId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group.setIconPhotoPath(groupDTO.getIconPhotoPath());
        group.setPrimaryAdmin(creator.getUserId());
        group.setAdmins(new ArrayList<>());
        group.setMembers(new ArrayList<>());
        group.setPosts(new ArrayList<>());
        group.setRequests(new ArrayList<>());

        group.getAdmins().add(creator.getUserId()); // The creator is also an admin
        group.getMembers().add(creator.getUserId());

        JsonGroupRepository.getInstance().save(group);

        User user = JsonUserRepository.getInstance().findById(creator.getUserId()).orElseThrow();
        user.getGroups().add(group.getGroupId());
        JsonUserRepository.getInstance().save(user);

    }

    @Override
    public void deleteGroup(String groupId) {
        JsonGroupRepository.getInstance().delete(groupId);
    }

    @Override
    public void promoteToAdmin(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (!group.getAdmins().contains(userId)) {
                group.getAdmins().add(userId);
                JsonGroupRepository.getInstance().save(group);
            }
        }
    }

    @Override
    public void demoteAdmin(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getAdmins().remove(userId);
            JsonGroupRepository.getInstance().save(group);
        }
    }

    // Admins Role
    @Override
    public List<String> getJoinRequests(String groupId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            return new ArrayList<>(groupOpt.get().getRequests());
        }
        return new ArrayList<>();
    }

    @Override
    public void approveMember(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.getRequests().contains(userId)) {
                group.getRequests().remove(userId);
                group.getMembers().add(userId);
                JsonGroupRepository.getInstance().save(group);

                User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
                user.getGroups().add(group.getGroupId());
                JsonUserRepository.getInstance().save(user);
            }
        }
    }

    @Override
    public void declineMember(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getRequests().remove(userId);
            JsonGroupRepository.getInstance().save(group);
        }
    }

    @Override
    public void removeMember(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getMembers().remove(userId);
            group.getAdmins().remove(userId); // Also remove from admins if present
            JsonGroupRepository.getInstance().save(group);

            User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
            user.getGroups().remove(group.getGroupId());
            JsonUserRepository.getInstance().save(user);
        }
    }

    @Override
    public void approvePost(String groupId, String userId, ContentDTO post) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getPosts().add(post.getContentId());
            JsonGroupRepository.getInstance().save(group);
        }
    }

    @Override
    public void deletePost(String groupId, String userId, ContentDTO post) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            group.getPosts().remove(post.getContentId());
            JsonGroupRepository.getInstance().save(group);
        }
    }

    @Override
    public void editPost(String groupId, String userId, ContentDTO content) {
        Post post = new Post();
        post.setContentId(content.getContentId());
        post.setAuthorId(userId);
        post.setContent(content.getContent());
        post.setImagePath(content.getImagePath());
        JsonPostRepository.getInstance().save(post);
    }

    // Normal Member Role
    @Override
    public void requestToJoin(String groupId, String userId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (!group.getRequests().contains(userId) && !group.getMembers().contains(userId)) {
                group.getRequests().add(userId);
                JsonGroupRepository.getInstance().save(group);
            }
        }
    }

    @Override
    public void submitPost(String groupId, String userId, ContentDTO content) throws ContentCreationException {
        if (content.getContent().isEmpty() && content.getImagePath().isEmpty()) {
            throw new ContentCreationException("Content cannot be empty");
        }

        Post post = new Post();
        post.setAuthorId(userId);
        post.setContent(content.getContent());
        post.setImagePath(content.getImagePath());
        JsonPostRepository.getInstance().save(post);

        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            if (group.getMembers().contains(userId)) {
                group.getPosts().add(post.getContentId());
                JsonGroupRepository.getInstance().save(group);
            }
        }
    }

    // General Groups Methods
    @Override
    public GroupDTO getGroupById(String groupId) {
        Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(groupId);
        if (groupOpt.isPresent()) {
            Group group = groupOpt.get();
            return new GroupDTO(group.getName(), group.getDescription(), group.getIconPhotoPath(), group.getPrimaryAdmin());
        }
        return null;
    }

    @Override
    public List<GroupDTO> getGroupsByUserId(String userId) {
        List<String> userGroups = JsonUserRepository.getInstance().findById(userId).orElseThrow().getGroups();
        List<GroupDTO> groups = new ArrayList<>();
        for (String userGroup : userGroups) {
            Optional<Group> groupOpt = JsonGroupRepository.getInstance().findById(userGroup);
            if (groupOpt.isPresent()) {
                Group group = groupOpt.get();
                groups.add(new GroupDTO(group.getName(), group.getDescription(), group.getIconPhotoPath(), group.getPrimaryAdmin()));
            }
        }
        return groups;
    }

    @Override
    public List<GroupDTO> searchGroupsByName(String name) {
        List<Group> groups = JsonGroupRepository.getInstance().findByName(name);
        List<GroupDTO> matchingGroups = new ArrayList<>();

        for (Group group : groups) {
            matchingGroups.add(new GroupDTO(group.getName(), group.getDescription(), group.getIconPhotoPath(), group.getPrimaryAdmin()));
        }

        return matchingGroups;
    }

    @Override
    public UserDTO getGroupPrimaryAdmin(String groupId) {
        Group group = JsonGroupRepository.getInstance().findById(groupId).orElseThrow();
        return JavaUserAccountService.getInstance().getUserById(group.getPrimaryAdmin());
    }

    @Override
    public List<UserDTO> getGroupAdmins(String groupId) {
        Group group = JsonGroupRepository.getInstance().findById(groupId).orElseThrow();
        List<UserDTO> admins = new ArrayList<>();
        List<String> adminIds = group.getAdmins();
        for (String adminId : adminIds) {
            admins.add(JavaUserAccountService.getInstance().getUserById(adminId));
        }
        return admins;
    }

    @Override
    public List<UserDTO> getGroupMembers(String groupId) {
        Group group = JsonGroupRepository.getInstance().findById(groupId).orElseThrow();
        List<UserDTO> members = new ArrayList<>();
        List<String> memberIds = group.getMembers();
        for (String memberId : memberIds) {
            members.add(JavaUserAccountService.getInstance().getUserById(memberId));
        }
        return members;
    }

    @Override
    public List<ContentDTO> getGroupPosts(String groupId) {
        Group group = JsonGroupRepository.getInstance().findById(groupId).orElseThrow();
        List<ContentDTO> posts = new ArrayList<>();
        List<String> postIds = group.getPosts();
        for (String post : postIds) {
            posts.add(new ContentDTO(JsonPostRepository.getInstance().findById(post).orElseThrow()));
        }

        return posts;
    }

    @Override
    public List<ContentDTO> getUserGroupsPosts(String userId) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<Group> userGroups = user.getGroups().stream().map(groupId -> JsonGroupRepository.getInstance().findById(groupId).orElseThrow()).toList();
        List<ContentDTO> posts = new ArrayList<>();
        for (Group group : userGroups) {
            for (String postId : group.getPosts()) {
                posts.add(new ContentDTO
                        (JsonPostRepository.getInstance().findById(postId).orElseThrow())
                );
            }
        }

        posts.sort(Comparator.comparing(ContentDTO::getTimestamp).reversed()); // Sort by timestamp (newest first)
        return posts;
    }

    @Override
    public List<GroupDTO> getGroupsSuggestions(String userId) {
        List<Group> groups = JsonGroupRepository.getInstance().findAll();
        List<GroupDTO> suggestions = new ArrayList<>();
        for (Group group : groups) {
            if (!group.getMembers().contains(userId)) {
                suggestions.add(new GroupDTO(group));
            }
        }

        return suggestions.subList(0, Math.min(suggestions.size(), 20));
    }

    public static enum MembershipType {
        PRIMARY_ADMIN,
        ADMIN,
        MEMBER,
        NOT_MEMBER;
    }
    @Override
    public MembershipType getMembershipType(String memberId, String groupId) {
        Group group = JsonGroupRepository.getInstance().findById(groupId).orElseThrow();
        if (group.getPrimaryAdmin().equals(memberId)) {
            return MembershipType.PRIMARY_ADMIN;
        } else if (group.getAdmins().contains(memberId)) {
            return MembershipType.ADMIN;
        } else if (group.getMembers().contains(memberId)) {
            return MembershipType.MEMBER;
        }
        return MembershipType.NOT_MEMBER;
    }
}
