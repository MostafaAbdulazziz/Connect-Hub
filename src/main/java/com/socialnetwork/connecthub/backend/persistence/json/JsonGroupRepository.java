package com.socialnetwork.connecthub.backend.persistence.json;

import com.socialnetwork.connecthub.backend.model.Group;
import com.socialnetwork.connecthub.backend.persistence.repository.GroupRepository;
import com.socialnetwork.connecthub.util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonGroupRepository implements GroupRepository {
    private static final String FILE_PATH = "src/main/java/com/socialnetwork/connecthub/resources/data/groups.json";
    private final JsonFileUtil<Group> jsonFileUtil = new JsonFileUtil<>(Group[].class);
    private List<Group> groups;

    private static JsonGroupRepository instance;

    // Private constructor for Singleton
    private JsonGroupRepository() {
        this.groups = new ArrayList<>(jsonFileUtil.loadFromFile(FILE_PATH));
    }

    // Singleton pattern to get the repository instance
    public static synchronized JsonGroupRepository getInstance() {
        if (instance == null) {
            instance = new JsonGroupRepository();
        }
        return instance;
    }

    @Override
    public void save(Group group) {
        // Check if the group already exists
        groups.removeIf(existingGroup -> existingGroup.getGroupId().equals(group.getGroupId()));
        groups.add(group);
        jsonFileUtil.saveToFile(FILE_PATH, groups);
    }

    @Override
    public Optional<Group> findById(String groupId) {
        for (Group group : groups) {
            if (group.getGroupId().equals(groupId)) {
                return Optional.of(group);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Group> findAll() {
        return new ArrayList<>(groups); // Return a copy to prevent external modification
    }

    @Override
    public List<Group> findByName(String groupName) {
        List<Group> matchingGroups = new ArrayList<>();
        for (Group group : groups) {
            if (group.getName() != null && group.getName().toLowerCase().contains(groupName.toLowerCase())) {
                matchingGroups.add(group);
            }
        }
        return matchingGroups;
    }

    @Override
    public void delete(String groupId) {
        groups.removeIf(group -> group.getGroupId().equals(groupId));
        jsonFileUtil.saveToFile(FILE_PATH, groups);
    }
}
