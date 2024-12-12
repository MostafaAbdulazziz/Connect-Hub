package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.Story;

import java.util.List;
import java.util.Optional;

public interface StoryRepository {
    Optional<Story> findById(String storyId);
    void save(Story story);
    void delete(String storyId);
    List<String> getAllStoryIds();
}
