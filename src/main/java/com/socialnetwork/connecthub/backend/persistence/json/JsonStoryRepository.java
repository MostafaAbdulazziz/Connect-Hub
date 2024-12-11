package com.socialnetwork.connecthub.backend.persistence.json;

import com.socialnetwork.connecthub.backend.model.Story;
import com.socialnetwork.connecthub.backend.persistence.repository.StoryRepository;
import com.socialnetwork.connecthub.util.idgenerator.IdGenerator;
import com.socialnetwork.connecthub.util.idgenerator.IdGeneratorFactory;
import com.socialnetwork.connecthub.util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonStoryRepository implements StoryRepository {
    private static final String FILE_PATH = "src/main/java/com/socialnetwork/connecthub/resources/data/stories.json";
    private static JsonStoryRepository instance;
    private final JsonFileUtil<Story> jsonFileUtil = new JsonFileUtil<>(Story[].class);
    private List<Story> stories;
    private final IdGenerator idGenerator;

    private JsonStoryRepository() {
        stories = new ArrayList<>(jsonFileUtil.loadFromFile(FILE_PATH));
        this.idGenerator = IdGeneratorFactory.getIdGenerator("uuid");
    }

    public static synchronized JsonStoryRepository getInstance() {
        if (instance == null) {
            instance = new JsonStoryRepository();
        }
        return instance;
    }

    @Override
    public Optional<Story> findById(String storyId) {
        for (Story story : stories) {
            if (story.getContentId().equals(storyId)) {
                return Optional.of(story);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Story story) {
        story.setContentId(idGenerator.generateId());
        if (story.getImagePath() == null || story.getImagePath().isEmpty()) {
            story.setImagePath("src/main/java/com/socialnetwork/connecthub/resources/pics/black-background.jpg");
        }
        stories.add(story);
        jsonFileUtil.saveToFile(FILE_PATH, stories);
    }

    @Override
    public void delete(String storyId) {
        for (int i = 0; i < stories.size(); i++) {
            if (stories.get(i).getContentId().equals(storyId)) {
                stories.remove(i);
                break;
            }
        }
        jsonFileUtil.saveToFile(FILE_PATH, stories);
    }

    @Override
    public List<String> getAllStoryIds() {
        List<String> storyIds = new ArrayList<>();
        for (Story story : stories) {
            storyIds.add(story.getContentId());
        }
        return storyIds;
    }
}
