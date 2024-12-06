package com.socialnetwork.connecthub.util.idgenerator;

import java.util.UUID;

public class UuidIdGenerator implements IdGenerator {
    @Override
    public String generateId() {
        return UUID.randomUUID().toString(); // Generate a random UUID
    }
}