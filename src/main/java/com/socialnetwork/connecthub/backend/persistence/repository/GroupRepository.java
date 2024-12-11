package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    void save(Group group);
    Optional<Group> findById(String groupId);
    List<Group> findAll();
    List<Group> findByName(String groupName); // For searching groups
    void delete(String groupId);
}
