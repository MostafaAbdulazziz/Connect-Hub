package com.socialnetwork.connecthub.backend.persistence.json;

import com.socialnetwork.connecthub.backend.model.Block;
import com.socialnetwork.connecthub.backend.persistence.repository.BlockRepository;
import com.socialnetwork.connecthub.util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JsonBlockRepository implements BlockRepository {

    private static final String FILE_PATH = "src/com/socialnetwork/connecthub/resources/data/blocks.json";
    private static JsonBlockRepository instance;
    private final JsonFileUtil<Block> jsonFileUtil = new JsonFileUtil<>(Block[].class);
    private List<Block> blocks;

    private JsonBlockRepository() {
        // Load the block records from the JSON file
        blocks = jsonFileUtil.loadFromFile(FILE_PATH);
    }

    public static synchronized JsonBlockRepository getInstance() {
        if (instance == null) {
            instance = new JsonBlockRepository();
        }
        return instance;
    }

    @Override
    public void save(Block block) {
        blocks.add(block);
        jsonFileUtil.saveToFile(FILE_PATH, blocks);
    }

    @Override
    public void unblock(String blockingUserId, String blockedUserId) {
        // Find the block record and remove it
        blocks.removeIf(block -> block.getBlockingUserId().equals(blockingUserId)
                && block.getBlockedUserId().equals(blockedUserId));
        // Save the updated list to the JSON file
        jsonFileUtil.saveToFile(FILE_PATH, blocks);
    }

    @Override
    public Optional<Block> findByIds(String blockingUserId, String blockedUserId) {
        for (Block block : blocks) {
            if (
                    (block.getBlockingUserId().equals(blockingUserId)
                    && block.getBlockedUserId().equals(blockedUserId))
                    || (block.getBlockingUserId().equals(blockedUserId)
                    && block.getBlockedUserId().equals(blockingUserId))
            ) {
                return Optional.of(block);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findAllByBlockingUserId(String blockingUserId) {
        List<Block> result = new ArrayList<>();
        for (Block block : blocks) {
            if (block.getBlockingUserId().equals(blockingUserId)) {
                result.add(block);
            }
        }
        return result;
    }

    @Override
    public List<Block> findAllByBlockedUserId(String blockedUserId) {
        List<Block> result = new ArrayList<>();
        for (Block block : blocks) {
            if (block.getBlockedUserId().equals(blockedUserId)) {
                result.add(block);
            }
        }
        return result;
    }
}
