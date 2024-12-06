package com.socialnetwork.connecthub.backend.persistence.repository;

import com.socialnetwork.connecthub.backend.model.Block;

import java.util.List;
import java.util.Optional;

public interface BlockRepository {
    void save(Block block);
    void unblock(String blockingUserId, String blockedUserId);
    Optional<Block> findByIds(String blockingUserId, String blockedUserId);
    List<Block> findAllByBlockingUserId(String blockingUserId);
    List<Block> findAllByBlockedUserId(String blockedUserId);
}
