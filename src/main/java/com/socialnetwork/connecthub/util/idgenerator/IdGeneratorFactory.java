package com.socialnetwork.connecthub.util.idgenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to provide the appropriate ID generator.
 * The factory returns an instance of the required ID generator strategy.
 */
public class IdGeneratorFactory {
    private static final Map<String, IdGenerator> generators = new HashMap<>();

    // Provide basic generators
    static {
        registerIdGenerator("incremental", new IncrementalIdGenerator());
        registerIdGenerator("uuid", new UuidIdGenerator());
    }

    /**
     * Factory method to return an IdGenerator based on the requested generator type.
     *
     * @param generatorType The type of the ID generator (e.g., "incremental").
     * @return An instance of IdGenerator.
     */
    public static IdGenerator getIdGenerator(String generatorType) {
        IdGenerator generator = generators.get(generatorType.toLowerCase());
        if (generator == null) {
            throw new IllegalArgumentException("Unknown generator type: " + generatorType);
        }
        return generator;
    }

    /**
     * Register a new ID generator strategy at runtime.
     */
    public static void registerIdGenerator(String generatorType, IdGenerator generator) {
        generators.put(generatorType.toLowerCase(), generator);
    }
}
