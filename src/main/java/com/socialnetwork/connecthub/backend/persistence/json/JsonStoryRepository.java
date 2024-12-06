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
    private static final String FILE_PATH = "src/com/socialnetwork/connecthub/resources/data/stories.json";
    private static JsonStoryRepository instance;
    private final JsonFileUtil<Story> jsonFileUtil = new JsonFileUtil<>(Story[].class);
    private List<Story> stories;
    private final IdGenerator idGenerator;

    private JsonStoryRepository() {
        stories = new ArrayList<>(jsonFileUtil.loadFromFile(FILE_PATH));
        this.idGenerator = IdGeneratorFactory.getIdGenerator("incremental");
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
        boolean storyExists = false;
        for (int i = 0; i < stories.size(); i++) {
            if (stories.get(i).getContentId().equals(story.getContentId())) {
                stories.set(i, story);
                storyExists = true;
                break;
            }
        }
        if (!storyExists) {
            story.setContentId(idGenerator.generateId());
            stories.add(story);
        }
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
